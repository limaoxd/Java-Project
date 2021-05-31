package entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Gate extends Entity{
    private double gateTimer;
    public boolean isSwitchOpened=false;
    private double ReferenceY;
    public Gate(double x, double y) throws FileNotFoundException{
        image = new Image(new FileInputStream("pic/test1.png"));
        flipimage = getFlip(image);
        sprite = new ImageView(image);
        hitbox = new Rectangle();
        hitbox.setFill(Color.BLACK);
        sprite.setSmooth(true);
        setSize(210,450);
        setPos(x,y);
        ReferenceY=y;
    }

@Override
    public void act(){
        setPos(getX(),getY());
        if(isSwitchOpened){
            //3 seconds
            if(getY()<ReferenceY+450 && gateTimer==0)
                Motion[1]=(450/180)*frameRate;
            //3 second opening
            else if(getY()>=ReferenceY+450 && gateTimer<240/frameRate){
                Motion[1]=0;
                setPos(getX(), ReferenceY+450);
                gateTimer++;
            }
            //close
            else if(gateTimer>240/frameRate){
                Motion[1]=-10*frameRate;
                if(getY()<ReferenceY){
                    setPos(getX(), ReferenceY);
                    gateTimer=0;
                    Motion[1]=0;
                    isSwitchOpened=false;
                }
            }
            setPos(getX(), getY()+Motion[1]);
        }

    }
}
