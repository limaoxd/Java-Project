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
    public boolean isSwitchOpened=false;
    public Switch(double x, double y) throws FileNotFoundException{
        image = new Image(new FileInputStream("pic/switch.png"));
        flipimage = getFlip(image);
        sprite = new ImageView(image);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        sprite.setSmooth(true);
        setSize(100,175);
        setPos(x,y);
    }

    @Override
    public void act(){
        setPos(getX(),getY());
    }
}
