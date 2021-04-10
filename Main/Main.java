//import entity.player.Player;

import java.util.*;
import java.math.BigInteger;
import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 

import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color; 

import javafx.stage.Stage;  
import javafx.animation.AnimationTimer;

public class Main extends Application {  
   public static Player p;

   public Main() throws FileNotFoundException{
      p = new Player(960,200);
   }

   public static void main(String args[]) throws FileNotFoundException{ 
      launch(args); 
   }

   @Override 
   public void start(Stage stage) throws FileNotFoundException {   

      //Creating a Group object  
      Group root = new Group(p.player);  
      
      //Creating a scene object
      Scene scene = new Scene(root, 1920, 1080);  

      scene.setOnKeyPressed(ke -> {
         if (ke.getCode() == KeyCode.LEFT) p.Leftpress = true;
         else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = true;
         else if (ke.getCode() == KeyCode.SPACE) p.Jump = true;
      });

      scene.setOnKeyReleased(ke -> {
         if (ke.getCode() == KeyCode.LEFT) p.Leftpress = false;
         else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = false;
         else if (ke.getCode() == KeyCode.SPACE) p.Jump = false;
      });

      stage.setFullScreen(true);
      stage.setTitle("test");  
      stage.setScene(scene);
      stage.show(); 

      AnimationTimer mainloop = new AnimationTimer() {
         @Override
         public void handle(long t) {
            p.SetScreenSize(stage.getWidth(),stage.getHeight());
            p.act();
         }
      };

      mainloop.start();
   }

}

class Player {  
   private Image image;
   private WritableImage flipimage;
   private double[] Pos = {0,0};
   private double[] Motion = {0,0};
   private int Width = 0,Height = 0;
   private double[] ratio={1,1};
   
   public boolean Rightpress = false ,Leftpress = false ,Jump = false,Jumped = false;
   public ImageView player;

   public Player(int x,int y) throws FileNotFoundException{
      image = new Image(new FileInputStream("pic/test1.png"));
      int w=(int)image.getWidth(),h=(int)image.getHeight();
      flipimage = new WritableImage(w,h);

      PixelReader pixelReader = image.getPixelReader(); 
      PixelWriter writer = flipimage.getPixelWriter(); 

      for(int col=0;col<h;col++){
         for(int row=0;row<w;row++){
            Color color = pixelReader.getColor(row,col); 
            writer.setColor(w-row-1, col, color);
         }
      }
      player = new ImageView(image);
      player.setSmooth(true);
      player.setPreserveRatio(true);
      Setsize(336,231);
      Setpos(x,y); 
   }

   public void Setpos(double x,double y){
      Pos[0] = x;
      Pos[1] = y;
      player.setFitWidth(Width*ratio[0]); 
      player.setFitHeight(Height*ratio[1]);
      player.setX((Pos[0]-Width/2)*ratio[0]); 
      player.setY((1080-Pos[1]-Height)*ratio[1]); 
   }

   public void Setsize(int w,int h){
      Width = w;
      Height = h;
   }

   public double Getx(){
      return Pos[0];
   }

   public double Gety(){
      return Pos[1];
   }

   public void SetScreenSize(double x,double y){
      ratio[0]=x/1920;
      ratio[1]=y/1080;
   }

   public void act(){
      
      if(Motion[0] != 0){
         if(Motion[0]>0.1) Motion[0]-=0.1;
         else if(Motion[0]<-0.1) Motion[0]+=0.1;
         else Motion[0]=0;
      }
      
      if(Pos[1]<=0){
         if(Motion[1]<0) Motion[1]=0;
      }else{
         Motion[1]-=0.3;
      }
      
      if(Jump == true){
         if(Jumped == false){
            Motion[1] = 10;
            Jumped = true;
         }
      }else Jumped = false;

      if(Rightpress == true){
         Motion[0] = 3;
         player.setImage(image);
      }
      else if(Leftpress == true){
         Motion[0] = -3;
         player.setImage(flipimage);
      }
      Setpos(Getx()+Motion[0],Gety()+Motion[1]);
   }
}