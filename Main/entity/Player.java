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
      hitbox = new Rectangle();
      hitbox.setFill(Color.TRANSPARENT);
      hitbox.setStroke(Color.LIGHTGREEN);
      hitbox.setStrokeWidth(2);
      sprite.setSmooth(true);
      sprite.setPreserveRatio(true);
      setSize(100,140);
      setPos(x,y); 
   }

   public void Camera(double sp,double sp1){
      World[0]+=sp;
      World[1]+=sp1;

      if(World[0]-Cam[0]>80*ratio[0]){
         Cam[0]=World[0]-80*ratio[0];
      }
      else if(World[0]-Cam[0]<-80*ratio[0]){
         Cam[0]=World[0]+80*ratio[0];
      }

      Cam[1]=World[1];
   }

   @Override
   public void act(){
       
      if(Jump == true && landing == true){
         if(Jumped == false){
            Motion[1] = 11;
            Jumped = true;
         }
      }else if(landing == true) Jumped = false;
 
      if(Rightpress == true){
         if(Shift&&landing) Motion[0] = 5;
         else if(Motion[0]<=3) Motion[0] = 3;
         sprite.setImage(image);
      }
      else if(Leftpress == true){
         if(Shift&&landing) Motion[0] = -5;
         else if(Motion[0]>=-3)Motion[0] = -3;
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
         Motion[1]-=0.3;
      }
      Camera(Motion[0],Motion[1]);

      setPos(getX()+Motion[0],getY()+cancel(Motion[1]));
    }
 }