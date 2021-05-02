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
      p = new Player(1000,500);
      entity.add(p);
      int i = 0;
      for(String col : MAP.map1){
         double length=0;
         for(int j = 0;j<col.length();j++){
            if(col.charAt(j)=='1'){
               length++;
               if(j==col.length()-1){
                  Block block = new Block((int)length*100,100,100*(j+1-length/2)+50,100*(10-i));
                  obj.add(block);
               }
            }else if(length>0){
               Block block = new Block((int)length*100,100,100*(j-length/2)+50,100*(10-i));
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
                        E.Setpos(E.Getx(),B.Geth()+B.Gety());
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