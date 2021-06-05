package entity;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
    public int conversationCount;
    public boolean resetCount;
    public Image spriteLaughImage;
    public Image spriteLaughFlip;
    public ImageView spritelaugh;
    public Trigger(double x,double y) throws FileNotFoundException {
        image = new Image(new FileInputStream("pic/npc_idle.png"));
        spriteLaughImage = new Image(new FileInputStream("pic/npc_laugh.png"));
        sprite = new ImageView(image);
        flipimage = getFlip(image);
        spriteLaughFlip = getFlip(spriteLaughImage);
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

        information = new Text();
        infoWindow  = false;
        information.setFill(Color.WHITE);
        information.setVisible(false);
        information.setFont(Font.font(null,25));
        conversationCount = 0;
        resetCount = true;

  
        E_keyImage = new Image(new FileInputStream("pic/e-key.png"));
        E_key = new ImageView(E_keyImage);
        E_key.setVisible(false);
        setSize(250,330);
        setPos(x,y);

    }

    public void act(double getX,double getY) {
        
        if(getX-Pos[0]<0 && Math.abs(getX-Pos[0])<200){
            sprite.setImage(flipimage);
            if(firstMeet||finishGame){
                exclamationMark.setVisible(true);
                E_key.setVisible(true);
            }
        }else if(getX-Pos[0]>0 && Math.abs(getX-Pos[0])<200 ){
            sprite.setImage(image);
            if(firstMeet||finishGame){
                E_key.setVisible(true);
                exclamationMark.setVisible(true);
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
            messageBase.setVisible(true);
            information.setVisible(true);
            if(!finishGame){
                switch(conversationCount){
                    case 1:
                        information.setText("......哦,真是稀奇啊，如果是頭腦正常的人應該不會過來這裡.");
                        break;
                    case 2:
                        information.setText("要是你和之前的笨蛋們一樣，想要在這充滿危險的古城找到寶藏的話");
                        break;
                    case 3:
                        information.setText("那就去吧，然後淒慘的死去吧. 呵呵呵");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        break;
                    case 4:
                        information.setText("怎麼了？決定放棄了？");
                        break;
                    case 5:
                        information.setText("呵呵呵 OUO");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        break;
                    case 6:
                        information.setText("那裡面充滿各種危險的機關，大砲，陷阱，各種機關.");
                        break;
                    case 7:
                        information.setText("沒有進去是明智的選擇,哈哈哈.....");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        E_key.setVisible(false);
                        firstMeet = false;
                        
                        break;
                    default:
                        information.setText("沒有進去是明智的選擇,哈哈哈.....");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        
                        break;
                }
            }else{
                if(resetCount)  {conversationCount = 1; resetCount = false;}
               
                switch(conversationCount){
                    case 1:
                        information.setText(".......,你繞了大半圈又回到這裡了呢.");
                        break;
                    case 2:
                        information.setText("這裡根本不存在什麼寶藏，不過你看起來不會想要放棄呢...");
                        break;
                    case 3:
                        information.setText("我前一段時間搜索過了，你就放棄吧！呵呵呵 OUO");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        E_key.setVisible(false);
                        break;
                    default:
                        information.setText("呵呵呵 OUO");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        break;
                }
            }
            
            

            
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
        messageBase.setFitHeight(ratio[1]*200);
        messageBase.setX((ratio[0]*1920)/2-messageBase.getFitWidth()/2);
        messageBase.setY((ratio[1]*1080)-messageBase.getFitHeight());
        information.setTextAlignment(TextAlignment.CENTER);
        information.setWrappingWidth(450);
        information.setX((ratio[0]*1920)/2-information.getWrappingWidth()/2);
        information.setLayoutY((ratio[1]*1080-information.getFont().getSize()-50));
        E_key.setFitWidth(30*ratio[0]);
        E_key.setFitHeight(30*ratio[1]);
        E_key.setX((Pos[0]-Width/2-Cam[0]+100)*ratio[0]); 
        E_key.setY((1080-Pos[1]-Height+Cam[1]+350)*ratio[1]);
    }

}
