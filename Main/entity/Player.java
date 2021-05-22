package entity;

import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity{  
   public Player(int x,int y) throws FileNotFoundException{
      image = new Image(new FileInputStream("pic/test1.png"));
      flipimage = getFlip(image);
      sprite = new ImageView(image);
      blood = new Image(new FileInputStream("pic/health_bar.png"));
      bloodbar = new ImageView(blood);
      hitbox = new Rectangle();
      hitbox.setFill(Color.TRANSPARENT);
      //hitbox.setStroke(Color.LIGHTGREEN);
      //hitbox.setStrokeWidth(2);
      sprite.setSmooth(true);
      bloodbar.setSmooth(true);
      //sprite.setPreserveRatio(true);
      setSize(100,140);
      setPos(x,y); 
   }

   public void Blood(){}

   public void Camera(){
      World[0]=Pos[0]-960;
      World[1]=Pos[1]-100;

      if(World[0]-Cam[0]>80*ratio[0]){
         Cam[0]=World[0]-80*ratio[0];
      }
      else if(World[0]-Cam[0]<-80*ratio[0]){
         Cam[0]=World[0]+80*ratio[0];
      }
      if(World[1]-Cam[1]>300*ratio[1]){
         Cam[1]=World[1]-300*ratio[1];
      }
      else if(World[1]-Cam[1]<0){
         Cam[1]=World[1];
      }
   }

   @Override
   public void act(double x,double y){
      setScreenSize(x,y);

      if(Jump == true && landing == true){
         if(Jumped == false){
            Motion[1] = 12;
            Jumped = true;
         }
      }else if(landing == true) Jumped = false;
 
      if(Rightpress == true){
         if(Shift&&landing) Motion[0] = 8;
         else if(Motion[0]<=3) Motion[0] = 4;
         sprite.setImage(image);
      }
      else if(Leftpress == true){
         if(Shift&&landing) Motion[0] = -8;
         else if(Motion[0]>=-3)Motion[0] = -4;
         sprite.setImage(flipimage);
      }
      
      if(Motion[0] != 0){
         if(Motion[0]>0.1){
            if(collideh == 1){
               Motion[0]=0;
            }else if(landing){
               Motion[0]-=0.2;
            }
         } 
         else if(Motion[0]<-0.1){
            if(collideh == 2){
               Motion[0]=0;
            }else if(landing){
               Motion[0]+=0.2;
            } 
         }
         else Motion[0]=0;
      }

      if(collidev == 2){
         if(Motion[1]>0) Motion[1]=0;
      }

      if(collidev == 1){
         landing = true;
         if(Motion[1]<0) Motion[1]=0;
      }else{
         landing = false;
         Motion[1]-=0.3*frameRate;
      }
      Camera();
      Blood();
      //System.out.println(frameRate);
      setPos(getX()+(Motion[0]*frameRate),getY()+cancel(Motion[1]*frameRate));
   }
 }