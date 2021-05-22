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
import javafx.scene.transform.Scale;

public class Trigger extends Entity{
    private boolean turnround = false;
    private boolean encounter = false;
    

    public Trigger(double x,double y) throws FileNotFoundException {
        image = new Image(new FileInputStream("pic/test3.png"));
        sprite = new ImageView(image);
        flipimage = getFlip(image);
        sprite.setSmooth(true);
        sprite.setPreserveRatio(true);
        setSize(100,150);
        setPos(x,y);  
    }
    
    public void act(double getX){
        boolean flip = false;
        boolean passThrough  = false;

        
        //determine whether the passerby should stop or not
        if(!encounter){
            if(getX()-1750<250 && !turnround){
                    Motion[0]=2;
            }else if(getX()-1750==250 && !turnround){
                turnround = true;
                Motion[0]=-2;
                sprite.setImage(flipimage);
            }else if(getX()-1750>0 && turnround){
                Motion[0]=-2;
            }else if(getX()-1750==0 && turnround){
                turnround = false;
                Motion[0]=+2;
                sprite.setImage(image);
            }
            setPos(getX()+Motion[0],getY());
            if (Math.abs(getX()-getX)<150) encounter = true; //player get close enough to passerby 
        }else{
            if (Math.abs(getX()-getX)<150 && getX()-getX>0) {   
                flip = true;
                passThrough = false;
                encounter = true;
                if(sprite.getImage().equals(image) && Motion[0]>0){
                    flipimage(flip, passThrough);
                }
            }else if(Math.abs(getX()-getX)>150 && getX()-getX>0){
                // flip = true;
                // passThrough = false;
                // flipimage(flip, passThrough);
                if(Motion[0]>0){
                    sprite.setImage(image);
                }else if(Motion[0]<0){
                    sprite.setImage(flipimage);
                }
                encounter = false;
            }else if(Math.abs(getX()-getX)<150 && getX()-getX<0){
                flip = true;
                passThrough = true;
                encounter = true;
                if(sprite.getImage().equals(flipimage) && Motion[0]<0){
                    flipimage(flip, passThrough);
                }
            }else if(Math.abs(getX()-getX)>150 && getX()-getX<0){
                // flip = false;
                // passThrough = true;
                // flipimage(flip, passThrough);
                if(Motion[0]>0){
                    sprite.setImage(image);
                }else if(Motion[0]<0){
                    sprite.setImage(flipimage);
                }
                encounter = false;
            }
            setPos(getX(), getY());
        }
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
