package entity;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.lang.Math;
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Trigger extends Entity{

    

    public Trigger(double x,double y) throws FileNotFoundException {
        image = new Image(new FileInputStream("pic/test3.png"));
        sprite = new ImageView(image);
        sprite.setSmooth(true);
        sprite.setPreserveRatio(true);
        setSize(100,150);
        setPos(x,y);  
    }
    
    
    public void show(){
        
        setSize(500,700);
    }
    
    public void act(double x,double y,double getX){
        setScreenSize(x, y);
        boolean flip = false;
        boolean passThrough  = false;
        if (Math.abs(getX()-getX)<150 && getX()-getX>0) {
            flip = true;
            passThrough = false;
            flipimage(flip,passThrough);
        }else if(Math.abs(getX()-getX)>150 && getX()-getX>0){
            flip = false;
            passThrough = false;
            flipimage(flip,passThrough);
        }else if(Math.abs(getX()-getX)<150 && getX()-getX<0){
            flip = true;
            passThrough = true;
            flipimage(flip,passThrough);
        }else if(Math.abs(getX()-getX)>150 && getX()-getX<0){
            flip = false;
            passThrough = true;
            flipimage(flip,passThrough);
        }
        setPos(getX(),getY());
    }

    public void flipimage(boolean flip,boolean passThrough){
        if(flip || passThrough){
            if(flip && passThrough) sprite.setScaleX(1);
            else sprite.setScaleX(-1);
        }else if(!(flip && passThrough)){
            sprite.setScaleX(1);
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

    public void setScreenSize(double x,double y){
        ratio[0]=x/1920;
        ratio[1]=y/1080;
    }
}
