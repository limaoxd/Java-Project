package entity;

import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color; 

public class Player extends Entity{  
 
   public Player(int x,int y) throws FileNotFoundException{
      image = new Image(new FileInputStream("pic/test1.png"));
      flipimage = GetFlip(image);
      sprite = new ImageView(image);
      sprite.setSmooth(true);
      sprite.setPreserveRatio(true);
      Setsize(191,263);
      Setpos(x,y); 
   }

   public void Camera(double sp){
      World[0]+=sp;

      if(World[0]-Cam[0]>80*ratio[0]){
         Cam[0]=World[0]-80*ratio[0];
      }
      else if(World[0]-Cam[0]<-80*ratio[0]){
         Cam[0]=World[0]+80*ratio[0];
      }
   }

   @Override
   public void act(){
       
      if(Motion[0] != 0){
         if(Motion[0]>0.1) Motion[0]-=0.1;
         else if(Motion[0]<-0.1) Motion[0]+=0.1;
         else Motion[0]=0;
      }
       
      if(Pos[1]<=0){
         landing = true;
         if(Motion[1]<0) Motion[1]=0;
      }else{
         landing = false;
         Motion[1]-=0.3;
      }
       
      if(Jump == true && landing == true){
         if(Jumped == false){
            Motion[1] = 15;
            Jumped = true;
         }
      }else if(landing == true) Jumped = false;
 
      if(Rightpress == true){
         Motion[0] = 4;
         sprite.setImage(image);
      }
      else if(Leftpress == true){
         Motion[0] = -4;
         sprite.setImage(flipimage);
      }

      Camera(Motion[0]);

      Setpos(Getx()+Motion[0],Gety()+Motion[1]);
    }
 }