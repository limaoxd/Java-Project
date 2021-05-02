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
   List<Entity> entity = new ArrayList<>();
   List<Entity> obj = new ArrayList<>();
   public static Player p;
   /*public static Enemy01 e;
   public static Enemy02 e2;
   public static Enemy03 e3;
   public static Enemy04 e4;*/
   public static Block b,b1,b2,b3; 

   public Main() throws FileNotFoundException{
      p = new Player(1000,600);
      /*e = new Enemy01(800,300);
      e2 = new Enemy02(640,400);
      e3 = new Enemy03(1120,350);
      e4 = new Enemy04(1280,500);*/
      b = new Block(100,200,1100,0);
      b1 = new Block(100,400,1200,0);
      b2 = new Block(200,600,1350,0);
      b3 = new Block(300,100,1600,700);
      entity.add(p);
      /*entity.add(e);
      entity.add(e2);
      entity.add(e3);
      entity.add(e4);*/
      obj.add(b);
      obj.add(b1);
      obj.add(b2);
      obj.add(b3);
   }

   public static void main(String args[]) throws FileNotFoundException{ 
      launch(args); 
   }

   @Override 
   public void start(Stage stage) throws FileNotFoundException {   

      //Creating a Group object  
      Group root = new Group();  
      
      for(Entity B : obj){
         root.getChildren().add(B.hitbox);
      }

      for(Entity E : entity){
         root.getChildren().add(E.hitbox);
         root.getChildren().add(E.sprite);
      }

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
            for(Entity E : entity){
               E.collideh=0;
               E.collidep=0;
               for(Entity B : obj){
                  if(E.hitbox.intersects(B.hitbox.getBoundsInLocal())){
                     if(E.Gety()<(B.Geth()+B.Gety()-50)){
                        if(E.Getx()<B.Getx()){
                           E.collideh=1;
                        }else if(E.Getx()>B.Getx()){
                           E.collideh=2;
                        }
                        if(E.Gety()+E.Geth()<B.Gety()+15) E.collidep=2;
                     }
                     else{
                        E.collidep=1;
                        //E.Setpos(E.Getx(),B.Geth()+B.Gety()+1);
                     } 
                  }
               }
            }
            for(Entity B : obj){
               B.SetScreenSize(stage.getWidth(),stage.getHeight());
               B.act();
            }
            for(Entity E : entity){
               E.SetScreenSize(stage.getWidth(),stage.getHeight());
               E.act();
            }
         }
      };

      mainloop.start();
   }

}