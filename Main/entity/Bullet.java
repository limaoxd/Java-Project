package entity;
import java.io.FileNotFoundException;
import java.util.List;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Entity{
    private static int t=0;
    private static double originX;
    private static double originY;
    public boolean isHit = false;
    public Bullet(double x,double y) throws FileNotFoundException{
        image = new Image(new FileInputStream("pic/test1.png"));
        flipimage = getFlip(image);
        sprite = new ImageView(image);
        hitbox = new Rectangle();
        hitbox.setFill(Color.BLACK);
        sprite.setSmooth(true);
        setSize(70,70);
        setPos(x,y);
        originX=x;
        originY=y;
    }
    @Override
    public void act(){
        setPos(getX()+(-8*frameRate),originY);
        t++;
        if(t>160/frameRate){
            setPos(originX,originY);
            isHit=false;
            t=0;
        }
        if(isHit){
            setPos(originX,originY);
        }
    }
}