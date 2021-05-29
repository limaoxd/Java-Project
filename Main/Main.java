import entity.*;
import map.*;
import loadSave.*;
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
import javafx.scene.text.*;

import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Main extends Application {
   List<Entity> entity = new ArrayList<>();
   List<Entity> obj = new ArrayList<>();
   List<Trigger> trigger = new ArrayList<>();
   public static Player p;
   public static Trigger t;
   public static Cannon c;
   public static Bullet b;
   public static Savepoint s;
   public static double frameRate;
   private final long[] frameTimes = new long[100];
   private int frameTimeIndex = 0 ;
   private double frameRatio = 0;
   private boolean arrayFilled = false ;

   public Main() throws FileNotFoundException{
      p = new Player(960,500);
      t = new Trigger(2500,500);
      int cannonX=9000,cannonY=600;
      c = new Cannon(cannonX,cannonY);
      b = new Bullet(cannonX+140,cannonY+100);
      s = new Savepoint(6000,900);
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
      /*obj.forEach(B -> root.getChildren().add(B.hitbox));

      entity.forEach(E-> {
            root.getChildren().add(E.hitbox);
            root.getChildren().add(E.sprite);
      });
      trigger.forEach((T -> root.getChildren().add(T.sprite)));*/


      //Creating a scene object
      Scene scene = new Scene(root, 1920, 1080);

      //for openning
      Openning openning = new Openning();
      openning.openningScreen(root,stage,scene);

      scene.setOnKeyPressed(ke -> {
         if (ke.getCode() == KeyCode.ENTER && openning.isStart == false){
            openning.start(root,stage,scene);
            /*entity.add(p);
            entity.add(b);
            obj.add(c);
            trigger.add(t);

            obj.forEach(B -> root.getChildren().add(B.hitbox));

            entity.forEach(E-> {
               root.getChildren().add(E.hitbox);
               root.getChildren().add(E.sprite);
            });
            trigger.forEach((T -> root.getChildren().add(T.sprite)));

            root.getChildren().addAll(p.bloodbarBase,p.redBlood,p.bloodbar);*/
         }
         if (ke.getCode() == KeyCode.S) LoadSave.save(p,s);
         if (ke.getCode() == KeyCode.L) LoadSave.load();
         if (ke.getCode() == KeyCode.LEFT) p.Leftpress = true;
         else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = true;
         else if (ke.getCode() == KeyCode.SPACE) p.Jump = true;
         else if (ke.getCode() == KeyCode.SHIFT) p.Shift = true;
         else if (ke.getCode() == KeyCode.R){
            p.setPos(960,500);
            p.setMy(0);
            p.Cam[0]=0;
            p.Cam[1]=0;
            p.World[0]=0;
            p.World[1]=0;
            p.newBornInGame=true;
            p.damaged=false;
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
      openning.setImage(stage);

      //It can refresh screen
      AnimationTimer mainloop = new AnimationTimer() {
         @Override
         public void handle(long t) {
            //calculate the framerate
            long oldFrameTime = frameTimes[frameTimeIndex] ;
            frameTimes[frameTimeIndex] = t ;
            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;

            if (frameTimeIndex == 0) {
               arrayFilled = true ;
            }

            //lag the loading
            if (openning.isStart == true) {
               if(openning.step <= 2) openning.time = (openning.time + 1) % frameTimes.length ;
               if(openning.time == 0 && openning.step == 0) {
                  addE();
                  openning.step++;
                  openning.loadingIn(root,stage);
               }
               else if(openning.time >= 20 && openning.step == 1) forEach(root,openning,stage);
               else if(openning.time >= 40 && openning.step == 2) {
                  root.getChildren().addAll(p.bloodbarBase,p.redBlood,p.bloodbar);
                  openning.step++;
                  openning.loadingOut(root);
                  LoadSave.save(p,s);
               }
            }

            if (arrayFilled) {
               long elapsedNanos = t - oldFrameTime ;
               long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
               frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
               frameRate = Math.round(frameRate);
               if(frameRate <=60) frameRatio = 1;

               else frameRatio = 60/frameRate;
            }

            //Dealing entity and obj collide(falling)
            for(Entity E : entity){
               E.collideh=0;
               E.collidev=0;
               //Divide falling speed
               for(double i=0;i>=E.getMy()&&E.getMy()<=0;i-=0.3*frameRatio){
                  E.setPos(E.getX(),E.getY()-0.3*frameRatio);
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
                        if(E.getY()<B.getY()+B.getY()-20){
                           if(E.getX()<B.getX()) {
                              E.collideh=1;
                           }
                           else if(E.getX()>B.getX()) {
                              E.collideh=2;
                           }
                           if(E.getY()+E.getH()>B.getY()&&E.getY()+E.getH()<B.getY()+10&& E.Isinrange(B)){
                              E.collidev=2;
                              E.setPos(E.getX(),B.getY()-E.getH()-1/(stage.getHeight()/1080));
                           }
                        }
                     }
                  }
               }
            }
            if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
               b.isHit=true;
               //player damaged code
                  p.hitByBullet = true;
                  p.Inject();
            }
            if(p.redBlood.getWidth()<=0){
               LoadSave.load();
               p.setPos(LoadSave.temp[0],LoadSave.temp[1]);
            }
            //savepoint
            if(p.hitbox.intersects(s.hitbox.getBoundsInLocal())){
               LoadSave.phase++;
               LoadSave.save(p,s);
            }

            //Acting everthing
            Entity.frameRate = frameRatio;

            Entity.setScreenSize(stage.getWidth(),stage.getHeight());
            entity.forEach(E -> E.act());
            obj.forEach(B -> B.act());
            trigger.forEach(T -> T.act(p.getX()));
         }
      };
      mainloop.start();
   }

   public void addE(){
      entity.add(p);
      entity.add(b);
      entity.add(s);
      obj.add(c);
      trigger.add(t);
   }

   public void forEach(Group root, Openning openning, Stage stage){
      obj.forEach(B -> root.getChildren().add(B.hitbox));

      entity.forEach(E-> {
         root.getChildren().add(E.hitbox);
         root.getChildren().add(E.sprite);
      });
      trigger.forEach((T -> root.getChildren().add(T.sprite)));

      openning.step++;
      openning.loadingIn(root,stage);
   }
}