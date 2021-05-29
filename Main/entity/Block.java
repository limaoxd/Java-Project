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
    private int LRtimer=0;
    private int gateTimer=0;
    private double ReferenceX;//reference for type 3 & 4
    private double GateReference;
    public static boolean isSwitchOpened=false;
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

                //Type 3(block moveing left)
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

                //Type 4(block moving right)
                if(col.charAt(j)=='4'){
                    length_4++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_4*200,150,200*(j+1-length_4/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                        movingBlock.add(block);
                        block.setType(4);
                    }
                }
                else if(length_4>0){
                    Block block = new Block((int)length_4*200,150,200*(j-length_4/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    movingBlock.add(block);
                    block.setType(4);
                    length_4 = 0;
                    block.ReferenceX=block.getX();
                }
                //Type 4

                //Type 5(gate)
                if(col.charAt(j)=='5'){
                    length_5++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Block block = new Block((int)length_5*200,150,200*(j+1-length_5/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                        obj.add(block);
                        block.setType(5);
                    }
                }
                else if(length_5>0){
                    Block block = new Block((int)length_5*200,150,200*(j-length_5/2)+50,150*(MAP.map1.length-1-i),Color.RED);
                    obj.add(block);
                    block.setType(5);
                    length_5 = 0;
                    block.GateReference=block.getY();
                }
                //Type 5
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
        LRtimer++;
        if(LRtimer>360/frameRate) {
            LRtimer=0;
        }

        setPos(getX(),getY());
        switch(blockType){
            case 3 :case 4:
                //stop 60
                if     (LRtimer<60/frameRate){
                    if(blockType==3)        setPos(ReferenceX+600, getY());
                    else if(blockType==4)   setPos(ReferenceX+600, getY());
                    Motion[0]=0;
                }
                //left 120
                else if(LRtimer> 60/frameRate && LRtimer<180/frameRate){
                    Motion[0]=-5;
                    setPos(getX()+(Motion[0]*frameRate),getY());
                }
                //stop 60
                else if(LRtimer>180/frameRate && LRtimer<240/frameRate){
                    if(blockType==3)        setPos(ReferenceX, getY());
                    else if(blockType==4)   setPos(ReferenceX, getY());
                    Motion[0]=0;
                }
                //right 120
                else if(LRtimer>240/frameRate && LRtimer<360/frameRate){
                    Motion[0]=5;
                    setPos(getX()+(Motion[0]*frameRate),getY());
                }
                //LRtimer exceed 360/frameRate
                break;
            case 5 :
                if(isSwitchOpened){
                    gateTimer++;
                    //3 seconds
                    if(gateTimer<190/frameRate)
                        Motion[1]=1;
                    //3 second opening
                    else if(gateTimer>190/frameRate && gateTimer<400/frameRate){
                        Motion[1]=0;
                        setPos(getX(), GateReference+450);
                    }
                    //close
                    else{
                        Motion[1]=-10;
                        if(getY()<=GateReference){
                            setPos(getX(), GateReference);
                            gateTimer=0;
                            Motion[1]=0;
                            isSwitchOpened=false;
                        }
                    }
                    setPos(getX(), getY()+Motion[1]);
                }
            default :
                break;
        }
    }

    private void setType(int t){
        this.blockType=t;
    }
}