import entity.*;
import map.*;
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
   List<Trigger> trigger = new ArrayList<>();
   public static Player p;
   public static Trigger t;
   public static double frameRate;
   private final long[] frameTimes = new long[100];
   private int frameTimeIndex = 0 ;
   private boolean arrayFilled = false ;

   public Main() throws FileNotFoundException{
      p = new Player(960,200);
      t = new Trigger(1750,250);
      entity.add(p);
      trigger.add(t);
      int i = 0;
      //Read map and build
      Block.createBlock(obj);
   }

   public static void main(String args[]) throws FileNotFoundException{ 
      launch(args); 
   }

   @Override 
   public void start(Stage stage) throws FileNotFoundException {   

      //Creating a Group object  
      Group root = new Group();  
      
      obj.forEach(B -> root.getChildren().add(B.hitbox));

      entity.forEach(E-> {
            root.getChildren().add(E.hitbox);
            root.getChildren().add(E.sprite);
      });
      trigger.forEach((T -> root.getChildren().add(T.sprite)));


      //Creating a scene object
      Scene scene = new Scene(root, 1920, 1080);  

      scene.setOnKeyPressed(ke -> {
         if (ke.getCode() == KeyCode.LEFT) p.Leftpress = true;
         else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = true;
         else if (ke.getCode() == KeyCode.SPACE) p.Jump = true;
         else if (ke.getCode() == KeyCode.SHIFT) p.Shift = true;
         else if (ke.getCode() == KeyCode.R){
            p.setPos(960,200);
            p.setMy(0);
            p.Cam[0]=0;
            p.Cam[1]=0;
            p.World[0]=0;
            p.World[1]=0;
         }
      });

      scene.setOnKeyReleased(ke -> {
         if (ke.getCode() == KeyCode.LEFT) p.Leftpress = false;
         else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = false;
         else if (ke.getCode() == KeyCode.SPACE) p.Jump = false;
         else if (ke.getCode() == KeyCode.SHIFT) p.Shift = false;
      });

      stage.setFullScreen(true);
      stage.setTitle("test");
      stage.setScene(scene);
      stage.show();

      //It can refresh screen
      AnimationTimer mainloop = new AnimationTimer() {
         @Override
         public void handle(long t) {
            //calculate the framerate
            long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = now ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                }


            //Dealing entity and obj collide(falling)
            for(Entity E : entity){
               E.collideh=0;
               E.collidev=0;
               //Divide falling speed
               for(double i=0;i>=E.getMy()&&E.getMy()<=0;i-=0.3){
                  E.setPos(E.getX(),E.getY()-0.3);
                  //Detect if collide obj
                  for(Entity B : obj){
                     if(E.hitbox.intersects(B.hitbox.getBoundsInLocal())){
                        if(E.getY()<(B.getH()+B.getY()-10)){
                           //Collide right
                           if(E.getX()<B.getX()) {
                              E.collideh=1;
                              E.setPos(B.getX()-B.getW()/2-E.getW()/2,E.getY());
                           }
                           //Collide left
                           else if(E.getX()>B.getX()) {
                              E.collideh=2;
                              E.setPos(B.getX()+B.getW()/2+E.getW()/2,E.getY());
                           }
                        }
                        //Collide down
                        else if(E.getMy()<=0 && E.Isinrange(B)){
                           E.collidev=1;
                           E.setPos(E.getX(),B.getH()+B.getY());
                           i--;
                        } 
                     }
                  }
               }
               //Dealing entity and obj collide(jump)
               if(E.getMy()>0){
                  for(Entity B : obj){
                     if(E.hitbox.intersects(B.hitbox.getBoundsInLocal())){
                        if(E.getY()<B.getY()+B.getY()-10){
                           if(E.getX()<B.getX()) E.collideh=1;
                           else if(E.getX()>B.getX()) E.collideh=2;
                           if(E.getY()+E.getH()>B.getY()&&E.getY()+E.getH()<B.getY()+10&& E.Isinrange(B)){
                              E.collidev=2;
                              E.setPos(E.getX(),B.getY()-E.getH()-1);
                           }
                        }
                     }
                  }
               }
            }
            //Acting everthing
            entity.forEach(E -> E.act(stage.getWidth(),stage.getHeight()));
            obj.forEach(B -> B.act(stage.getWidth(),stage.getHeight()));
            trigger.forEach(T -> T.act(stage.getWidth(),stage.getHeight(), p.getX()));

         }
      };
      mainloop.start();
   }

}