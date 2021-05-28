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

public class Savepoint extends Entity{
    public double posx;
    public double posy;
    public Savepoint(double x, double y) throws FileNotFoundException{
        image = new Image(new FileInputStream("pic/save_point.png"));
        flipimage = getFlip(image);
        sprite = new ImageView(image);
        hitbox = new Rectangle();
        hitbox.setFill(Color.TRANSPARENT);
        sprite.setSmooth(true);
        setSize(10,10);
        setPos(x,y);
        posx = x;
        posy = y;
        landing = true;
    }

    @Override
    public void act(){
        Motion[0] = 0 ;
        Motion[1] = 0 ;
        landing = true;
        setPos(posx,posy);
    }
}
