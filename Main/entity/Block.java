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
    private int blockType=0;
    private int timer=0;
    private double ReferenceX;
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
            double length_3=0;
            double length_4=0;
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

                //Type 3(block moveing left)
                if(col.charAt(j)=='3'){
                    length_3++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_3*200,150,200*(j+1-length_3/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                        block.setType(3);
                    }
                }
                else if(length_3>0){
                    Block block = new Block((int)length_3*200,150,200*(j-length_3/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    block.setType(3);
                    length_3 = 0;
                    block.ReferenceX=block.getX();
                }
                //Type3

                //Type 4(block moving right)
                if(col.charAt(j)=='4'){
                    length_4++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_4*200,150,200*(j+1-length_4/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                        block.setType(4);
                    }
                }
                else if(length_4>0){
                    Block block = new Block((int)length_4*200,150,200*(j-length_4/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    block.setType(4);
                    length_4 = 0;
                    block.ReferenceX=block.getX();
                }
                //Type 4
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
        timer++;
        if(timer>360/frameRate) {
            timer=0;
        }

        setPos(getX(),getY());
        switch(blockType){
            case 3 :case 4:
                //stop 60
                if     (timer<60/frameRate){
                    if(blockType==3)        setPos(ReferenceX+600, getY());
                    else if(blockType==4)   setPos(ReferenceX+600, getY());
                    break;
                }
                //left 120
                else if(timer> 60/frameRate && timer<180/frameRate)
                    setPos(getX()-(5*frameRate),getY());
                //stop 60
                else if(timer>180/frameRate && timer<240/frameRate){
                    if(blockType==3)        setPos(ReferenceX, getY());
                    else if(blockType==4)   setPos(ReferenceX, getY());
                }
                //right 120
                else if(timer>240/frameRate && timer<360/frameRate)
                    setPos(getX()+(5*frameRate),getY());
                //timer exceed 360/frameRate
                break;
            default :
                break;
        }
    }

    private void setType(int t){
        this.blockType=t;
    }
}