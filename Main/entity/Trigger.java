package entity;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.lang.Math;

import javax.swing.LayoutFocusTraversalPolicy;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
public class Trigger extends Entity{
    public boolean infoWindow ;
    public Text information;
    public ImageView exclamationMark;
    public Image exclamationMarkImage;
    public Image messageBaseImage;
    public ImageView messageBase;
    public boolean firstMeet; 
    public boolean finishGame;
    public Image E_keyImage;
    public ImageView E_key;
    public Trigger(double x,double y) throws FileNotFoundException {
        image = new Image(new FileInputStream("pic/npc_idle.png"));
        sprite = new ImageView(image);
        flipimage = getFlip(image);
        sprite.setSmooth(true);
        //sprite.setPreserveRatio(true);

        exclamationMarkImage = new Image(new FileInputStream("pic/exclamationMark.png"));
        exclamationMark = new ImageView(exclamationMarkImage);
        exclamationMark.setVisible(false);
        firstMeet = true;
        finishGame = false;

        messageBaseImage = new Image(new FileInputStream("pic/messageBase.png"));
        messageBase = new ImageView(messageBaseImage);
        messageBase.setVisible(false);
        messageBase.setOpacity(0.4);

        information = new Text("Welcome to the whatever");
        infoWindow  = false;
        information.setFill(Color.RED);
        information.setVisible(false);
        information.setFont(Font.font(null,FontWeight.NORMAL,25));
  
        E_keyImage = new Image(new FileInputStream("pic/e-key.png"));
        E_key = new ImageView(E_keyImage);
        E_key.setVisible(false);
        setSize(250,330);
        setPos(x,y);
    }

    public void act(double getX,double getY){
        /*System.out.println(Pos[0]);
        System.out.println(Pos[1]);*/
        if(getX-Pos[0]<0 && Math.abs(getX-Pos[0])<200){
            sprite.setImage(flipimage);
            if(firstMeet){
                exclamationMark.setVisible(true);
                E_key.setVisible(true);
            }
            if(finishGame){
                exclamationMark.setVisible(true);
                E_key.setVisible(true);
                information.setText("Congratulation!!");
            }
        }else if(getX-Pos[0]>0 && Math.abs(getX-Pos[0])<200 ){
            sprite.setImage(image);
            if(firstMeet){
                E_key.setVisible(true);
                exclamationMark.setVisible(true);
            }
            if(finishGame){
                E_key.setVisible(true);
                exclamationMark.setVisible(true);
                information.setText("Congratulation!!");
            }
        }else if(getX-Pos[0]<0 && Math.abs(getX-Pos[0])>200){
            sprite.setImage(flipimage);
            exclamationMark.setVisible(false);
            E_key.setVisible(false);
        }else if(getX-Pos[0]>0 && Math.abs(getX-Pos[0])>200){
            sprite.setImage(image);
            exclamationMark.setVisible(false);
            E_key.setVisible(false);
        }
        setPos(Pos[0], Pos[1]);
        if(Math.abs(getX-Pos[0])<200 && infoWindow){
            information.setVisible(true);
            messageBase.setVisible(true);
            E_key.setVisible(false);
            firstMeet = false;
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
        messageBase.setFitWidth(ratio[0]*840);
        messageBase.setFitHeight(ratio[1]*100);
        messageBase.setX((ratio[0]*1920)/2-messageBase.getFitWidth()/2);
        messageBase.setY((ratio[1]*1080)-messageBase.getFitHeight()-100);
        information.setTextAlignment(TextAlignment.CENTER);
        information.setWrappingWidth(400);
        information.setX((ratio[0]*1920)/2-information.getWrappingWidth()/2);
        information.setLayoutY((ratio[1]*1080-information.getFont().getSize())-100);
        E_key.setFitWidth(30*ratio[0]);
        E_key.setFitHeight(30*ratio[1]);
        E_key.setX((Pos[0]-Width/2-Cam[0]+100)*ratio[0]); 
        E_key.setY((1080-Pos[1]-Height+Cam[1]+350)*ratio[1]);
    }

}
