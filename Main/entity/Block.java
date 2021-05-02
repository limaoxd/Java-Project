package entity;

import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Entity{
    public Block(int w,int h,double x,double y) throws FileNotFoundException{
        hitbox = new Rectangle();
        hitbox.setFill(Color.RED);
        Setsize(w,h);
        Setpos(x,y); 
    }

    @Override
    public void Setpos(double x,double y){
        Pos[0] = x;
        Pos[1] = y;
        
        hitbox.setX((Pos[0]-Width/2-Cam[0])*ratio[0]);
        hitbox.setY((1080-Pos[1]-Height)*ratio[1]);
        hitbox.setWidth(Width*ratio[0]); 
        hitbox.setHeight(Height*ratio[1]);
     }

    public void act(){
        Setpos(Getx(),Gety());
    }
}