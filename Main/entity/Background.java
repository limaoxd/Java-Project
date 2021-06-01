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

public class Background extends Entity{
    private int blockType=0;

    public Background(int w,int h,double x,double y) throws FileNotFoundException{
        setSize(w,h);
        setPos(x,y);
    }

    public static void createBg(List<Background> obj) throws FileNotFoundException{
        int i=0;
        for(String col : MAP.background1){
            double length_1=0;
            for(int j = 0;j<col.length();j++){
               //Type 1(normal block)
                if(col.charAt(j)=='0'){// count rectangle length
                    length_1++;
                    //If col end but number is "1"
                    if(j==col.length()-1){
                        Background bg = new Background((int)length_1*200,150,200*(j+1-length_1/2)+50,150*(MAP.background1.length-1-i));
                        obj.add(bg);
                        bg.setType(0);
                    }
                }
               //If read "0" stop counting
                else if(length_1>0){
                    Background bg = new Background((int)length_1*200,150,200*(j-length_1/2)+50,150*(MAP.background1.length-1-i));
                    obj.add(bg);
                    bg.setType(0);
                    length_1 = 0;
                }
                //Type 1
                if(col.charAt(j)!='0'){
                    if(col.charAt(j)>'0'&&col.charAt(j)<='6'){
                        int num = (int)col.charAt(j)-'0';
                        Background bg = new Background(200,150,200*(j+0.5)+50,150*(MAP.background1.length-1-i));
                        obj.add(bg);
                        bg.setType(num);
                    }
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
        if(blockType>=0&&blockType<7&&sprite!=null){
            sprite.setFitWidth(Width*ratio[0]);
            sprite.setFitHeight(Height*ratio[1]);
            sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
            sprite.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
        }
    }

    @Override
    public void act(){
        setPos(getX(),getY());
    }

    private void setType(int t) throws FileNotFoundException{
        this.blockType=t;

        if(blockType==0) image=new Image(new FileInputStream("pic/bg0.png"));
        else if(blockType==1) image=new Image(new FileInputStream("pic/bg1.png"));
        else if(blockType==2) image=new Image(new FileInputStream("pic/bg2.png"));
        else if(blockType==3) image=new Image(new FileInputStream("pic/bg3.png"));
        else if(blockType==4) image=new Image(new FileInputStream("pic/bg4.png"));
        else if(blockType==5) image=new Image(new FileInputStream("pic/bg5.png"));
        else if(blockType==6) image=new Image(new FileInputStream("pic/bg6.png"));

        sprite = new ImageView(image);
    }

    public int getType(){
        return blockType;
    }
}