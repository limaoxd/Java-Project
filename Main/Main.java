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
   public static Player p;
   public Main() throws FileNotFoundException{
      p = new Player(1000,150);
      entity.add(p);
      int i = 0;
      //Read map and build
      for(String col : MAP.map1){
         double length=0;
         for(int j = 0;j<col.length();j++){
            // count rectangle length
            if(col.charAt(j)=='1'){
               length++;
               //If col end but number is "1"
               if(j==col.length()-1){
                  Block block = new Block((int)length*100,100,100*(j+1-length/2)+50,100*(MAP.map1.length-1-i));
                  obj.add(block);
               }
            }
            //If read "0" stop counting
            else if(length>0){
               Block block = new Block((int)length*100,100,100*(j-length/2)+50,100*(MAP.map1.length-1-i));
               obj.add(block);
               length = 0;
            }
         }
         i++;
      }
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
         else if (ke.getCode() == KeyCode.SHIFT) p.Shift = true;
         else if (ke.getCode() == KeyCode.R){
            p.setPos(1000,150);
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
            //Dealing entity and obj collide(falling)
            for(Entity E : entity){
               E.collideh=0;
               E.collidep=0;
               //Divide falling speed
               for(double i=0;i>=E.getMy()&&E.getMy()<=0;i-=0.3){
                  E.setPos(E.getX(),E.getY()-0.3);
                  //Detect if collide obj
                  for(Entity B : obj){
                     if(E.hitbox.intersects(B.hitbox.getBoundsInLocal())){
                        if(E.getY()<(B.getH()+B.getY()-1)){
                           //Collide right
                           if(E.getX()<B.getX()) E.collideh=1;
                           //Collide left
                           else if(E.getX()>B.getX()) E.collideh=2;
                        }
                        //Collide down
                        else if(E.getMy()<=0){
                           E.collidep=1;
                           E.setPos(E.getX(),B.getH()+B.getY());
                           E.setMy(i);
                           i--;
                        } 
                     }
                  }
               }
               //Dealing entity and obj collide(jump)
               if(E.getMy()>0){
                  for(Entity B : obj){
                     if(E.hitbox.intersects(B.hitbox.getBoundsInLocal())){
                        if(E.getY()<B.getY()+B.getY()-1){
                           if(E.getX()<B.getX()) E.collideh=1;
                           else if(E.getX()>B.getX()) E.collideh=2;
                           if(E.getY()+E.getH()>B.getY()&&E.getY()+E.getH()<B.getY()+10){
                              E.collidep=2;
                              E.setPos(E.getX(),B.getY()-E.getH());
                           }
                        }
                     }
                  }
               }
            }
            //Acting everthing
            for(Entity E : entity){
               E.setScreenSize(stage.getWidth(),stage.getHeight());
               E.act();
            }

            for(Entity B : obj){
               B.setScreenSize(stage.getWidth(),stage.getHeight());
               B.act();
            }
         }
      };
      mainloop.start();
   }

}