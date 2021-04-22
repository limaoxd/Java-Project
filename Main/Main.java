import entity.*;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;  
import javafx.animation.AnimationTimer;

public class Main extends Application {  
   public static Player p;
   public static Enemy01 e;
   public static Enemy02 e2;
   public static Enemy03 e3;
   public static Enemy04 e4;

   public Main() throws FileNotFoundException{
      p = new Player(960,200);
      e = new Enemy01(800,300);
      e2 = new Enemy02(640,400);
      e3 = new Enemy03(1120,350);
      e4 = new Enemy04(1280,500);
   }

   public static void main(String args[]) throws FileNotFoundException{ 
      launch(args); 
   }

   @Override 
   public void start(Stage stage) throws FileNotFoundException {   

      //Creating a Group object  
      Group root = new Group(p.sprite,e.sprite,e2.sprite,e3.sprite,e4.sprite);  
      
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
            e.SetScreenSize(stage.getWidth(),stage.getHeight());
            e.act();
            e2.SetScreenSize(stage.getWidth(),stage.getHeight());
            e2.act();
            e3.SetScreenSize(stage.getWidth(),stage.getHeight());
            e3.act();
            e4.SetScreenSize(stage.getWidth(),stage.getHeight());
            e4.act();
         }
      };

      mainloop.start();
   }

}