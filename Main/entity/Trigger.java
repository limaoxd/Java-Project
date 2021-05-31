package entity;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.lang.Math;

import javax.swing.text.html.StyleSheet;

import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Trigger extends Entity{
    
    public Trigger(double x,double y) throws FileNotFoundException {
        image = new Image(new FileInputStream("pic/test3.png"));
        sprite = new ImageView(image);
        flipimage = getFlip(image);
        sprite.setSmooth(true);
        sprite.setPreserveRatio(true);
        setSize(200,300);
        setPos(x,y);  
    }

    public void act(double getX,double getY){
        if(getX-Pos[0]<0 && Math.abs(getX-Pos[0])<200 && getY-Pos[1]<=50){
            sprite.setImage(flipimage);
        }else if(getX-Pos[0]>0 && Math.abs(getX-Pos[0])<200 && Math.abs(getY-Pos[1])<=50){
            sprite.setImage(image);
        }
        setPos(Pos[0], Pos[1]);
        
    }

    public void flipimage(boolean flip,boolean passThrough){
        if(flip || passThrough){
            if(flip && passThrough) sprite.setImage(image);
            else sprite.setImage(flipimage);
        }else if(!(flip && passThrough)){
            sprite.setImage(image);
        }
    }

    @Override
    public void setPos(double x,double y){
        Pos[0] = x;
        Pos[1] = y;
        sprite.setFitWidth(Width*ratio[0]);
        sprite.setFitHeight(Height*ratio[1]);
        sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
        sprite.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
    }

}
