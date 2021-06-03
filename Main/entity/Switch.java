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

public class Switch extends Entity{
    public Image E_keyImage;
    public ImageView E_key;
    public boolean show_Ekey;
    public boolean isSwitchOpened=false;
    private int timer;
    public Switch(double x, double y) throws FileNotFoundException{
        image = new Image(new FileInputStream("pic/switch.png"));
        E_keyImage = new Image(new FileInputStream("pic/e-key.png"));
        E_key = new ImageView(E_keyImage);
        E_key.setVisible(false);
        show_Ekey = false;

        flipimage = getFlip(image);
        sprite = new ImageView(image);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        sprite.setSmooth(true);
        setSize(100,175);
        setPos(x,y);
    }

    @Override
    public void setPos(double x,double y){
        Pos[0] = x;
        Pos[1] = y;
  
        hitbox.setX((Pos[0]-Width/2-Cam[0])*ratio[0]);
        hitbox.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
        hitbox.setWidth(Width*ratio[0]); 
        hitbox.setHeight(Height*ratio[1]);
  
        sprite.setFitWidth(Width*ratio[0]);
        sprite.setFitHeight(Height*ratio[1]);
        sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
        sprite.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);

        E_key.setFitWidth(50*ratio[0]);
        E_key.setFitHeight(50*ratio[1]);
        E_key.setX((Pos[0]-Width/2-Cam[0]+50)*ratio[0]); 
        E_key.setY((1080-Pos[1]-Height+Cam[1]-200)*ratio[1]);

        // E_key.setFitWidth(Width*ratio[0]);
        // E_key.setFitHeight(Height*ratio[1]);
        // E_key.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
        // E_key.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
  
     }

    @Override
    public void act(){
        setPos(getX(),getY());
        if(isSwitchOpened){
            sprite.setImage(flipimage);
            if(!Gate.isSwitchOpened){
                sprite.setImage(image);
                isSwitchOpened=false;
            }
        }
        
        if(show_Ekey){
            E_key.setVisible(true);
        }else {
            E_key.setVisible(false);
            show_Ekey = false;
        }
    }
}
