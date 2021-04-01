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
import javafx.stage.Stage;  
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
            p.act();
         }
      };
      mainloop.start();
   }

}

class Player {  
   private Image image;
   private double[] Pos = {0,0};
   private double[] Motion = {0,0};
   private int Width = 0,Height = 0;
   
   public boolean Rightpress = false ,Leftpress = false ,Jump = false,Jumped = false;
   public ImageView player;

   public Player(int x,int y) throws FileNotFoundException{
      image = new Image(new FileInputStream("pic/test1.png"));
      player = new ImageView(image);
      Setsize(336,231);
      Setpos(x,y);
      player.setPreserveRatio(true); 
   }

   public void Setpos(double x,double y){
      Pos[0] = x;
      Pos[1] = y;
      player.setX(Pos[0]-Width/2); 
      player.setY(1080-Pos[1]-Height); 
   }

   public void Setsize(int w,int h){
      Width = w;
      Height = h;
      player.setFitWidth(Width); 
      player.setFitHeight(Height);
   }

   public double Getx(){
      return Pos[0];
   }

   public double Gety(){
      return Pos[1];
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
      }
      else if(Leftpress == true){
         Motion[0] = -3;
      }

      Setpos(Getx()+Motion[0],Gety()+Motion[1]);
   }
}