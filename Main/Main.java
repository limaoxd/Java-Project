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
   List<Entity> movingBlock = new ArrayList<>();
   List<Trigger> trigger = new ArrayList<>();
   public static Player p;
   public static Trigger t;
   public static Cannon c;
   public static Bullet b;
   public static Savepoint s;
   public static Switch sw;
   public static Gate g;
   public static double frameRate;
   private final long[] frameTimes = new long[100];
   private int frameTimeIndex = 0 ;
   private double frameRatio = 0;
   private boolean arrayFilled = false ;

   public Main() throws FileNotFoundException{
      p = new Player(960,500);//960
      t = new Trigger(2500,250);
      int cannonX=8900,cannonY=450;
      c = new Cannon(cannonX,cannonY);
      b = new Bullet(cannonX,cannonY+70);
      s = new Savepoint(6000,700);
      sw = new Switch(3500,1050);
      g = new Gate(4000,450);
      //Read map and build
      Block.createBlock(obj,movingBlock);
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
         }
         if (ke.getCode() == KeyCode.ENTER && openning.isDead == true && openning.isReborn == false){
            openning.reborn(root);
            p.setPos(LoadSave.temp[0],LoadSave.temp[1]);
         }
         if (ke.getCode() == KeyCode.S && openning.isDead == false) LoadSave.save(p,s);
         if (ke.getCode() == KeyCode.L && openning.isDead == false) LoadSave.load();
         if (ke.getCode() == KeyCode.LEFT && openning.isDead == false) p.Leftpress = true;
         else if (ke.getCode() == KeyCode.RIGHT && openning.isDead == false) p.Rightpress = true;
         else if (ke.getCode() == KeyCode.SPACE && openning.isDead == false) p.Jump = true;
         else if (ke.getCode() == KeyCode.SHIFT && openning.isDead == false) p.Shift = true;
         else if (ke.getCode() == KeyCode.E && openning.isDead == false) {
            if(p.getX()-t.getX()<200) t.infoWindow = true;
         }
         else if (ke.getCode() == KeyCode.R && openning.isDead == false){
            p.setPos(9000,700);
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

            
            if (openning.isStart == true) {
               openning.time = (openning.time + 1) % frameTimes.length ;

               //lag the loading
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

               //for dead screen
               if(openning.isDead == true){
                  if(openning.time >= 3 && openning.lightDegree <= 0.8 && openning.isReborn == false){
                     openning.deadSdarker();
                  }
                  if(openning.time >= 1 && openning.isReborn == true){
                     openning.rebornLoading(root);
                  }
               }
            }

            if (arrayFilled) {
               long elapsedNanos = t - oldFrameTime ;
               long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
               frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
               frameRate = Math.round(frameRate);
               if(frameRate <=60) frameRatio = 1;
               else if(frameRate>=130) frameRatio = 0.416667;
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
                           if(E.getX()<B.getX()) {//set
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
               for(Entity B : movingBlock)
                  if(E.hitbox.intersects(B.hitbox.getBoundsInLocal()))
                     E.setMx(B.getMx());
            }
            if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
               b.isHit=true;
               //player damaged code
               if(openning.isDead == false){
                  p.hitByBullet = true;
                  p.Inject();
               }
            }
            if(p.redBlood.getWidth()<=0){
               LoadSave.load();
               openning.deadScreen(root);
               p.Leftpress = false;
               p.Rightpress = false;
               p.Jump = false;
               p.Shift = false;
            }
            //savepoint
            if(p.hitbox.intersects(s.hitbox.getBoundsInLocal())){
               LoadSave.phase++;
               LoadSave.save(p,s);
            }
            if(p.hitbox.intersects(sw.hitbox.getBoundsInLocal())){//open switch
               g.isSwitchOpened=true;
               sw.isSwitchOpened=true;
            }
            //Acting everthing
            Entity.frameRate = frameRatio;

            Entity.setScreenSize(stage.getWidth(),stage.getHeight());
            entity.forEach(E -> E.act());
            obj.forEach(B -> B.act());
            trigger.forEach(T -> T.act(p.getX(),p.getY()));
         }
      };
      mainloop.start();
   }

   public void addE(){
      entity.add(p);
      entity.add(b);
      entity.add(s);
      obj.add(c);
      obj.add(sw);
      obj.add(g);
      trigger.add(t);
   }

   public void forEach(Group root, Openning openning, Stage stage){
      obj.forEach(B -> {
         root.getChildren().add(B.hitbox);
         if(B instanceof Block)
            if(((Block)B).getType()==1||((Block)B).getType()==4) root.getChildren().add(B.sprite);
         
      });
      trigger.forEach((T -> root.getChildren().addAll(T.sprite,T.exclamationMark)));
      entity.forEach(E-> {
         root.getChildren().add(E.hitbox);
         root.getChildren().add(E.sprite);
      });
      //subtitle should above the player
      trigger.forEach((T -> root.getChildren().addAll(T.messageBase,T.information)));
      

      openning.step++;
      openning.loadingIn(root,stage);
   }
}