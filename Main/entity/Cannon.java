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

public class Cannon extends Entity{
    public Cannon(double x, double y) throws FileNotFoundException{
        image = new Image(new FileInputStream("pic/cannon.png"));
        flipimage = getFlip(image);
        sprite = new ImageView(image);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        sprite.setSmooth(true);
        setSize(400,174);
        setPos(x,y);
    }

    @Override
    public void act(){
        setPos(getX(),getY());
    }
}
