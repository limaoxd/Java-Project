package entity;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.lang.Math;
import javax.swing.text.html.StyleSheet;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
public class Trigger extends Entity{
    public boolean infoWindow ;
    public Text information;
    public ImageView exclamationMark;
    public Image exclamationMarkImage;
    public Image messageBaseImage;
    public ImageView messageBase;
    public Trigger(double x,double y) throws FileNotFoundException {
        image = new Image(new FileInputStream("pic/test3.png"));
        sprite = new ImageView(image);
        flipimage = getFlip(image);
        sprite.setSmooth(true);
        sprite.setPreserveRatio(true);

        exclamationMarkImage = new Image(new FileInputStream("pic/exclamationMark.png"));
        exclamationMark = new ImageView(exclamationMarkImage);
        exclamationMark.setVisible(false);

        messageBaseImage = new Image(new FileInputStream("pic/messageBase.png"));
        messageBase = new ImageView(messageBaseImage);
        messageBase.setVisible(false);

        setSize(200,300);
        setPos(x,y);

        infoWindow  = false;
        information = new Text(650,860,"Welcome to the whatever");

        messageBase.setX(400);
        messageBase.setY(600);
        messageBase.setFitWidth(800);
        messageBase.setFitHeight(500);

        information.setFill(Color.RED);
        information.setVisible(false);
        information.setFont(Font.font(null,FontWeight.NORMAL,25));
  
    }

    public void act(double getX,double getY){
        System.out.println(Pos[0]);
        System.out.println(Pos[1]);
        if(getX-Pos[0]<0 && Math.abs(getX-Pos[0])<200){
            sprite.setImage(flipimage);
            exclamationMark.setVisible(true);
        }else if(getX-Pos[0]>0 && Math.abs(getX-Pos[0])<200 ){
            sprite.setImage(image);
            exclamationMark.setVisible(true);
        }else if(getX-Pos[0]<0 && Math.abs(getX-Pos[0])>200){
            sprite.setImage(flipimage);
            exclamationMark.setVisible(false);
        }else if(getX-Pos[0]>0 && Math.abs(getX-Pos[0])>200){
            sprite.setImage(image);
            exclamationMark.setVisible(false);
        }
        setPos(Pos[0], Pos[1]);
        if(Math.abs(getX-Pos[0])<200 && infoWindow){
            information.setVisible(true);
            messageBase.setVisible(true);
        }else  {
            infoWindow = false;
            information.setVisible(false);
            messageBase.setVisible(false);
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
        exclamationMark.setFitWidth(100*ratio[0]);
        exclamationMark.setFitHeight(100*ratio[1]);
        exclamationMark.setX(((Pos[0]+50)-100/2-Cam[0])*ratio[0]); 
        exclamationMark.setY((1080-(Pos[1]+300)-100+Cam[1])*ratio[1]);
    }

}
