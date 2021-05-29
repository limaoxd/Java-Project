package entity;

import map.*;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.util.List;

import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Entity{
    public Block(int w,int h,double x,double y,Color color) throws FileNotFoundException{
        hitbox = new Rectangle();
        hitbox.setFill(color);
        setSize(w,h);
        setPos(x,y);
    }
    public static void createBlock(List<Entity> obj) throws FileNotFoundException{
        int i=0;
        for(String col : MAP.map1){
            double length_1=0;
            double length_2=0;
            for(int j = 0;j<col.length();j++){
               // count rectangle length
                if(col.charAt(j)=='1'){
                    length_1++;
                    //If col end but number is "1"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_1*200,200,200*(j+1-length_1/2)+50,200*(MAP.map1.length-1-i),Color.GRAY);
                        obj.add(block);
                    }
                }
                
               //If read "0" stop counting
                else if(length_1>0){
                    Block block = new Block((int)length_1*200,200,200*(j-length_1/2)+50,200*(MAP.map1.length-1-i),Color.GRAY);
                    obj.add(block);
                    length_1 = 0;
                }

                if(col.charAt(j)=='2'){
                    length_2++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_2*200,200,200*(j+1-length_2/2)+50,200*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                    }
                }
                else if(length_2>0){
                    Block block = new Block((int)length_2*200,200,200*(j-length_2/2)+50,200*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    length_2 = 0;
                }
            }
            i++;
         }
    }

    @Override
    public void setPos(double x,double y){
        Pos[0] = x;
        Pos[1] = y;

        hitbox.setX((Pos[0]-Width/2-Cam[0])*ratio[0]);
        hitbox.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
        hitbox.setWidth(Width*ratio[0]);
        hitbox.setHeight(Height*ratio[1]);
    }

    @Override
    public void act(){
        setPos(getX(),getY());
    }
}