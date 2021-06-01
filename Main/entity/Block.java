package entity;

import map.*;
import static java.lang.System.out;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Entity{
    private int blockType=0;
    private double LRtimer=0;
    private static boolean isGoingRight=false;//is going right or not(going left)
    private double ReferenceX,ReferenceY;//reference for type 3 & 4

    public Block(int w,int h,double x,double y,Color color) throws FileNotFoundException{
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        setSize(w,h);
        setPos(x,y);
    }

    public static void createBlock(List<Entity> obj,List<Entity> movingBlock) throws FileNotFoundException{
        int i=0;
        for(String col : MAP.map1){
            double length_1=0;
            double length_2=0;
            double length_3=0;
            double length_4=0;
            double length_5=0;
            for(int j = 0;j<col.length();j++){
               //Type 1(normal block)
                if(col.charAt(j)=='1'){// count rectangle length
                    length_1++;
                    //If col end but number is "1"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_1*200,150,200*(j+1-length_1/2)+50,150*(MAP.map1.length-1-i),Color.GRAY);
                        obj.add(block);
                        block.setType(1);
                    }
                }
               //If read "0" stop counting
                else if(length_1>0){
                    Block block = new Block((int)length_1*200,150,200*(j-length_1/2)+50,150*(MAP.map1.length-1-i),Color.GRAY);
                    obj.add(block);
                    block.setType(1);
                    length_1 = 0;
                }
                //Type 1

                //Type 2
                if(col.charAt(j)=='2'){
                    length_2++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_2*200,150,200*(j+1-length_2/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                        block.setType(2);
                    }
                }
                else if(length_2>0){
                    Block block = new Block((int)length_2*200,150,200*(j-length_2/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    block.setType(2);
                    length_2 = 0;
                }
                //Type 2

                //Type 3(block moveing L and R)
                if(col.charAt(j)=='3'){
                    length_3++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_3*200,150,200*(j+1-length_3/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                        movingBlock.add(block);
                        block.setType(3);
                    }
                }
                else if(length_3>0){
                    Block block = new Block((int)length_3*200,150,200*(j-length_3/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    movingBlock.add(block);
                    block.setType(3);
                    length_3 = 0;
                    block.ReferenceX=block.getX();
                }
                //Type3
                if(col.charAt(j)=='w'){
                    length_4++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_4*200,150,200*(j+1-length_4/2)+50,150*(MAP.map1.length-1-i),Color.BLACK);
                        obj.add(block);
                        block.setType(4);
                    }
                }
                else if(length_4>0){
                    Block block = new Block((int)length_4*200,150,200*(j-length_4/2)+50,150*(MAP.map1.length-1-i),Color.BLACK);
                    obj.add(block);
                    block.setType(4);
                    length_4 = 0;
                }

                if(col.charAt(j)=='l'||col.charAt(j)=='r'){
                    Block block = new Block(200,150,200*(j+0.5)+50,150*(MAP.map1.length-1-i),Color.BLACK);
                    obj.add(block);
                    if(col.charAt(j)=='l')
                        block.setType(5);
                    else
                        block.setType(6);
                }
                if(col.charAt(j)=='L'||col.charAt(j)=='R'){
                    Block block = new Block(200,150,200*(j+0.5)+50,150*(MAP.map1.length-1-i),Color.BLACK);
                    obj.add(block);
                    if(col.charAt(j)=='L')
                        block.setType(7);
                    else
                        block.setType(8);
                }
            }
            i++;
         }
    }

    public Image extendSprite(Image img,int w){
        int width = 200,height = 150;
        int blocklength=w/width;

        PixelReader pixelReader = img.getPixelReader();

        WritableImage c_img = new WritableImage(w,height);
        PixelWriter writer = c_img.getPixelWriter();
        for(int i=0; i<height; i++){
            for(int j=0; j<w; j++){
                Color color = pixelReader.getColor(j%width,i);
                writer.setColor(j,i,color);
            }
        }
        
        return (Image)c_img;
    }

    @Override
    public void setPos(double x,double y){
        Pos[0] = x;
        Pos[1] = y;
        if(blockType>0&&blockType<9){
            sprite.setFitWidth(Width*ratio[0]);
            sprite.setFitHeight(Height*ratio[1]);
            sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
            sprite.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
        }
        hitbox.setX((Pos[0]-Width/2-Cam[0])*ratio[0]);
        hitbox.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
        hitbox.setWidth(Width*ratio[0]);
        hitbox.setHeight(Height*ratio[1]);
    }

    @Override
    public void act(){
        setPos(getX(),getY());
        LRtimer++;

        switch(blockType){
            case 3 :
                //stop 60
                //blocks were going right and have arrived
                if     (isGoingRight && getX()>=ReferenceX){
                    Motion[0]=0;
                    setPos(ReferenceX, getY());

                    if(LRtimer>60/frameRate)    isGoingRight=false;
                }

                //left
                //blocks were going left and haven't arrived
                else if(!isGoingRight && getX()>ReferenceX-600){
                    Motion[0]=-10*frameRate;
                    setPos(getX()+(Motion[0]*frameRate),getY());

                    LRtimer=0;
                }

                //stop 60
                //blocks were going left and have arrived
                else if(!isGoingRight && getX()<=ReferenceX-600){
                    Motion[0]=0;
                    setPos(ReferenceX-600, getY());

                    if(LRtimer>60/frameRate)    isGoingRight=true;
                }

                //right
                //blocks were going right and haven't arrived
                else if(isGoingRight && getX()<ReferenceX){
                    Motion[0]=10*frameRate;
                    setPos(getX()+(Motion[0]*frameRate),getY());

                    LRtimer=0;
                }
                break;

            default :
                break;
        }
    }

    private void setType(int t) throws FileNotFoundException{
        this.blockType=t;
        if(blockType==1) image=extendSprite(new Image(new FileInputStream("pic/gd1.png")),Width);
        else if(blockType==3||blockType==2) image=new Image(new FileInputStream("pic/plug1.png"));
        else if(blockType==4) image=new Image(new FileInputStream("pic/p1.png"));
        else if(blockType==5) image=new Image(new FileInputStream("pic/left_gd1.png"));
        else if(blockType==6) image=new Image(new FileInputStream("pic/right_gd1.png"));
        else if(blockType==7) image=new Image(new FileInputStream("pic/gd2.png"));
        else if(blockType==8) image=new Image(new FileInputStream("pic/gd3.png"));

        sprite = new ImageView(image);
    }

    public int getType(){
        return blockType;
    }
}