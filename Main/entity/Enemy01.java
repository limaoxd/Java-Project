package entity;

import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy01 extends Entity{  

   private int t=0,rand=(int)(Math.random()*3);
   private int jt=0,jrand=(int)(Math.random()*10);
   
   public Enemy01(int x,int y) throws FileNotFoundException{
      image = new Image(new FileInputStream("pic/test2.png"));
      flipimage = getFlip(image);
      sprite = new ImageView(image);
      hitbox = new Rectangle();
      hitbox.setFill(Color.TRANSPARENT);
      hitbox.setStroke(Color.LIGHTGREEN);
      hitbox.setStrokeWidth(2);
      sprite.setSmooth(true);
      sprite.setPreserveRatio(true);
      setSize(165,261);
      setPos(x,y); 
   }

   @Override
   public void act(){
      t++;
      t%=60;
      if(t==59){
         rand=(int)(Math.random()*3);
      }

      jt++;
      jt%=60;
      if(jt==29){
         jrand=(int)(Math.random()*6);
      }

      if(rand == 0){
         Rightpress = true;
         Leftpress = false;
      }else if (rand == 1){
         Rightpress = false;
         Leftpress = true;
      }else{
         Rightpress = false;
         Leftpress = false;
      }

      if(jrand == 5){
         Jump = true;
      }else{
         Jump = false;
      }

      if(Rightpress == true){
         Motion[0] = 3;
         sprite.setImage(image);
      }
      else if(Leftpress == true){
         Motion[0] = -3;
         sprite.setImage(flipimage);
      }

      if(Motion[0] != 0){
         if(Motion[0]>0.1){
            if(collidedic == 1){
               Motion[0]=0;
            }else{
               Motion[0]-=0.1;
            }
         } 
         else if(Motion[0]<-0.1){
            if(collidedic == 3){
               Motion[0]=0;
            }else{
               Motion[0]+=0.1;
            } 
         }
         else Motion[0]=0;
      }
      if(collidedic == 4){
         if(Motion[1]>0) Motion[1]=0;
      }
      if(Pos[1]<=0 || collidedic == 2){
         landing = true;
         if(Motion[1]<0) Motion[1]=0;
      }else{
         landing = false;
         Motion[1]-=0.3;
      }
      setPos(getX()+Motion[0],getY()+Motion[1]);
    }
 }