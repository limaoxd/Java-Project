package entity;

import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.lang.Math;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    public boolean resetCount1;
    public boolean resetCount2;
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
        resetCount1 = true;
        resetCount2 = true;

  
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
            if(!finishGame){
                if(firstMeet){
                    switch(conversationCount){
                        case 1:
                            information.setText("......???,?????????????????????????????????????????????????????????????????????.");
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            break;
                        case 2:
                            information.setText("??????????????????????????????????????????????????????????????????????????????????????????");
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            break;
                        case 3:
                            information.setText("???????????????????????????????????????. ?????????");
                            if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                            else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            break;
                        case 4:
                            messageBase.setVisible(false);
                            information.setVisible(false);
                            E_key.setVisible(false);
                            exclamationMark.setVisible(false);
                            break;
                        default:
                            messageBase.setVisible(false);
                            information.setVisible(false);
                            E_key.setVisible(false);
                            exclamationMark.setVisible(false);
                            firstMeet = false;
                            break;

                    }
                }else{
                    if(resetCount1)  {conversationCount = 1; resetCount1 = false;}
                    switch(conversationCount){
                        case 1:
                            information.setText("??????????????????????????????");
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            break;
                        case 2:
                            information.setText("????????? OUO");
                            if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                            else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            break;
                        case 3:
                            information.setText("?????????????????????????????????????????????????????????????????????.");
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            break;
                        case 4:
                            information.setText("??????????????????????????????,?????????.....");
                            if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                            else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                            messageBase.setVisible(true);
                            information.setVisible(true);
                            E_key.setVisible(false);
                            break;
                        default:
                            conversationCount = 0;
                            messageBase.setVisible(false);
                            information.setVisible(false);
                            E_key.setVisible(false);

                    }
                }
   
                
            }else{
                if(resetCount2)  {conversationCount = 1; resetCount2 = false;}
                messageBase.setVisible(true);
                information.setVisible(true);
                switch(conversationCount){
                    case 1:
                        information.setText(".......,???????????????????????????????????????.");
                        break;
                    case 2:
                        information.setText("???????????????????????????????????????????????????????????????????????????...");
                        break;
                    case 3:
                        information.setText("???????????????????????????????????????????????????????????? OUO");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        E_key.setVisible(false);
                        exclamationMark.setVisible(false);
                        break;
                    default:
                        information.setText("????????? OUO");
                        if(getX-Pos[0]>0) sprite.setImage(spriteLaughImage);
                        else if(getX-Pos[0]<0) sprite.setImage(spriteLaughFlip);
                        E_key.setVisible(false);
                        exclamationMark.setVisible(false);
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
