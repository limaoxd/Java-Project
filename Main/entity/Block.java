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
    private double ReferenceX,ReferenceY;//reference for type 3 & 4

    public Block(int w,int h,double x,double y,Color color) throws FileNotFoundException{
        hitbox = new Rectangle();
        hitbox.setFill(color);
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
        if(blockType==1||blockType==4){
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

        switch(blockType){
            case 3 :
                LRtimer++;
                //LRtimer exceed 360/frameRate
                if(LRtimer>360/frameRate)   LRtimer=0;

                //stop 60
                if     (LRtimer<60/frameRate && getX()>=ReferenceX){
                    setPos(ReferenceX, getY());
                    Motion[0]=0;
                }

                //left 120
                else if(LRtimer> 60/frameRate && LRtimer<180/frameRate && getX()>ReferenceX-600){
                    Motion[0]=-10*frameRate;
                    setPos(getX()+(Motion[0]*frameRate),getY());

                    //time has arrived but the block haven't arrived yet
                    if(LRtimer>170/frameRate && getX()>ReferenceX-600)  LRtimer=175/frameRate;
                }

                //stop 60
                else if(LRtimer>180/frameRate && LRtimer<240/frameRate && getX()<=ReferenceX-600){
                    setPos(ReferenceX-600, getY());
                    Motion[0]=0;
                }

                //right 120
                else if(LRtimer>240/frameRate && LRtimer<360/frameRate && getX()<ReferenceX){
                    Motion[0]=10*frameRate;
                    setPos(getX()+(Motion[0]*frameRate),getY());

                    //time has arrived but the block haven't arrived yet
                    if(LRtimer>350/frameRate && getX()<ReferenceX)  LRtimer=355/frameRate;
                }
                break;

            default :
                break;
        }
    }

    private void setType(int t) throws FileNotFoundException{
        this.blockType=t;
        if(blockType==1){
            image=extendSprite(new Image(new FileInputStream("pic/gd1.png")),Width);
            sprite = new ImageView(image);
        }
        else if(blockType==4){
            image=new Image(new FileInputStream("pic/w1.png"));
            sprite = new ImageView(image);
        }
    }

    public int getType(){
        return blockType;
    }
}