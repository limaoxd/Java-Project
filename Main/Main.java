import entity.*;
import map.*;
import loadSave.*;
import music.*;
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
   List<Block> movingBlock = new ArrayList<>();
   List<Block> Ablock = new ArrayList<>();
   List<Trigger> trigger = new ArrayList<>();
   List<Book> book = new ArrayList<>();
   List<Book> spikes= new ArrayList<>();
   List<Background> backGround = new ArrayList<>();
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
   private int Ablock_now=7,Ablock_pre=0;

   public Main() throws FileNotFoundException{
      p = new Player(1400,500);//960
      t = new Trigger(2400,285);
      int cannonX=8850,cannonY=450;
      c = new Cannon(cannonX,cannonY);
      b = new Bullet(cannonX,cannonY+110);
      s = new Savepoint(6000,700);
      sw = new Switch(3500,1050);
      g = new Gate(3950,450);
      //Read map and build
      Background.createBg(backGround);
      Book.createBook(book,spikes);
      Block.createBlock(obj,movingBlock,Ablock);
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

      Music.play();

      scene.setOnKeyPressed(ke -> {
         if (ke.getCode() == KeyCode.ENTER && openning.isStart == false){
            openning.start(root,stage,scene);
         }
         if (ke.getCode() == KeyCode.ENTER && openning.isStart == true && openning.isBefore == false && openning.time >40){
            openning.loadingOut(root);
            openning.isBefore = true;
         }
         if (ke.getCode() == KeyCode.ENTER && openning.isDead == true && openning.isReborn == false){
            openning.reborn(root);
            p.setPos(LoadSave.temp[0],LoadSave.temp[1]);
         }
         if (ke.getCode() == KeyCode.ENTER && openning.isWin == true && openning.isAfter == false){
            openning.afterstory(root,p);
         }
         if(openning.isDead == false && openning.isWin == false){
            //if (ke.getCode() == KeyCode.S) LoadSave.testSave(p);
            if (ke.getCode() == KeyCode.LEFT) p.Leftpress = true;
            else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = true;
            else if (ke.getCode() == KeyCode.SPACE) p.Jump = true;
            else if (ke.getCode() == KeyCode.SHIFT) p.Shift = true;
            else if (ke.getCode() == KeyCode.E) {
               if(p.getX()-t.getX()<200) {
                  t.conversationCount++;
                  t.infoWindow = true;}
               if(p.hitbox.intersects(sw.hitbox.getBoundsInLocal())){
                  g.isSwitchOpened=true;
                  sw.isSwitchOpened=true;
               }
            }
         }
      });

      scene.setOnKeyReleased(ke -> {
         if (ke.getCode() == KeyCode.LEFT) p.Leftpress = false;
         else if (ke.getCode() == KeyCode.RIGHT) p.Rightpress = false;
         else if (ke.getCode() == KeyCode.SPACE) p.Jump = false;
         else if (ke.getCode() == KeyCode.SHIFT) p.Shift = false;
      });

      stage.setFullScreen(true);
      stage.setTitle("Emerald_Pendant");
      stage.setScene(scene);
      stage.getIcons().add(new Image("pic/project_icon.png"));
      stage.show();
      openning.setImage(stage);

      //It can refresh screen
      AnimationTimer mainloop = new AnimationTimer() {
         @Override
         public void handle(long t) {
            Entity.setScreenSize(stage.getWidth(),stage.getHeight());
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
                  openning.step++;
                  openning.beforestory(root,stage);
               }
               else if(openning.isBefore == true && openning.step == 3){
                  openning.step++;
                  root.getChildren().addAll(p.bloodbarBase,p.redBlood,p.bloodbar);
                  root.getChildren().addAll(sw.E_key);
                  LoadSave.reset(s,p);
                  Music.change("ProjectMusic.mp3");
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

               if(openning.isWin == true && openning.isAfter == true){
                  if(openning.time >= 3 && openning.lightDegree >= 0){
                     openning.winstart(root);
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
               for(Block B : movingBlock){
                  if(E.hitbox.intersects(B.hitbox.getBoundsInLocal())){
                     E.landing=true;
                     if(B.getType()==3){
                        E.setMx(B.getMx());
                     }
                     else if(B.getType()==2){
                        for(Block aB:Ablock) aB.isVisible=false;
                        Ablock_now=7;
                        Ablock_pre=0;
                        E.setMy(B.getMy()+0.3);
                     }
                  }
               }
               Ablock.get(Ablock_now).isVisible=true;
               if(E.hitbox.intersects(Ablock.get(Ablock_now).hitbox.getBoundsInLocal())){
                  Ablock.get(Ablock_pre).isVisible=false;
                  Ablock_pre=Ablock_now;
                  Ablock_now=Ablock.get(Ablock_now).nextBlock;
               }
            }
            for(Book spike:spikes){
               if(p.getY()<spike.getY()+(spike.getH()/2)&&
                  p.getY()>spike.getY()-(spike.getH()/2)&&
                  p.getX()>spike.getX()-(spike.getW()/2)&&
                  p.getX()<spike.getX()+(spike.getW()/2)){
                  if(openning.isDead == false){
                     p.hitBySpike = true;
                     p.Inject();
                  }
               }
            }

            if(p.hitbox.intersects(b.hitbox.getBoundsInLocal())){
               b.isHit=true;
               //player damaged code
               if(openning.isDead == false){
                  p.hitByBullet = true;
                  p.Inject();
               }
            }
            if(p.hitbox.intersects(sw.hitbox.getBoundsInLocal())){
               sw.show_Ekey = true;
            }else sw.show_Ekey = false;
            if(p.redBlood.getWidth()<=0){
               LoadSave.load();
               openning.deadScreen(root,stage);
               p.Leftpress = false;
               p.Rightpress = false;
               p.Jump = false;
               p.Shift = false;
            }
            //savepoint
            if(p.hitbox.intersects(s.hitbox.getBoundsInLocal())){
               LoadSave.phase++;
               LoadSave.save(s);
               if(LoadSave.phase==4) {Main.t.finishGame = true;}

               //to win(change this  to 1 or 2 or 3 to open it)
               if(LoadSave.phase == 4){
                  openning.win(root,stage,entity,p);
                  Music.change("StartingMusic.mp3");
               }
            }

            // if(p.hitbox.intersects(sw.hitbox.getBoundsInLocal())){//open switch
            //    g.isSwitchOpened=true;
            //    sw.isSwitchOpened=true;
            // }
            //Acting everthing
            Entity.frameRate = frameRatio;
            entity.forEach(E -> E.act());
            obj.forEach(B -> B.act());
            backGround.forEach(G -> G.act());
            book.forEach(P -> P.act());
            trigger.forEach(T -> T.act(p.getX(),p.getY()));
         }
      };
      mainloop.start();
   }

   public void addE(){
      entity.add(p);
      entity.add(s);
      obj.add(b);
      obj.add(c);
      obj.add(sw);
      obj.add(g);
      trigger.add(t);
   }

   public void forEach(Group root, Openning openning, Stage stage){
      backGround.forEach((G -> root.getChildren().add(G.sprite)));
      obj.forEach(B -> {
         root.getChildren().add(B.hitbox);
         root.getChildren().add(B.sprite);
      });
      trigger.forEach((T -> root.getChildren().addAll(T.sprite,T.exclamationMark)));
      entity.forEach(E-> {
         root.getChildren().add(E.hitbox);
         root.getChildren().add(E.sprite);
      });
      book.forEach((P -> root.getChildren().add(P.sprite)));
      //subtitle should above the player
      trigger.forEach((T -> root.getChildren().addAll(T.E_key,T.messageBase,T.information)));

      openning.step++;
      openning.loadingIn(root,stage);
   }
}