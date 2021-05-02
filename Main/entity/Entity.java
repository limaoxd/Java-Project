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
import javafx.scene.shape.Rectangle;


public class Entity {
   protected Image image;
   protected WritableImage flipimage;
   protected double[] Pos = {0,0};
   protected double[] Motion = {0,0};
   protected int Width = 0,Height = 0;
   protected  double[] ratio={1,1};
   public int collideh;
   public int collidep;
   public boolean Rightpress = false ,Leftpress = false ,Jump = false ,Jumped = false ,landing = false;
   public ImageView sprite;
   public Rectangle hitbox;
   public static double[] World = {0,0};
   public static double[] Cam = {0,0};

   public Entity() throws FileNotFoundException{}

   public void Setpos(double x,double y){
      Pos[0] = x;
      Pos[1] = y;
      
      hitbox.setX((Pos[0]-Width/2-Cam[0])*ratio[0]);
      hitbox.setY((1080-Pos[1]-Height)*ratio[1]);
      hitbox.setWidth(Width*ratio[0]); 
      hitbox.setHeight(Height*ratio[1]);

      sprite.setFitWidth(Width*ratio[0]);
      sprite.setFitHeight(Height*ratio[1]);
      sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
      sprite.setY((1080-Pos[1]-Height)*ratio[1]);
   }

   public void Setsize(int w,int h){
      Width = w;
      Height = h;
   }

   public void SetScreenSize(double x,double y){
      ratio[0]=x/1920;
      ratio[1]=y/1080;
   }

   public double Getx(){
      return Pos[0];
   }

   public double Gety(){
      return Pos[1];
   }

   public double Getw(){
      return Width;
   }
   
   public double Geth(){
      return Height;
   }

   public double Getmx(){
      return Motion[0];
   }

   public double Getmy(){
      return Motion[1];
   }

   public WritableImage GetFlip(Image img){
      int w=(int)image.getWidth(),h=(int)image.getHeight();
      WritableImage flipimg = new WritableImage(w,h); 
      PixelReader pixelReader = img.getPixelReader(); 
      PixelWriter writer = flipimg.getPixelWriter(); 
 
      for(int col=0;col<h;col++){
         for(int row=0;row<w;row++){
            Color color = pixelReader.getColor(row,col); 
            writer.setColor(w-row-1, col, color);
         }
      }
      return flipimg;
   }

   public void act(){}
}