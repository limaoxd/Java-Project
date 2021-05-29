package entity;

import java.util.*;
import static java.lang.System.out;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import loadSave.LoadSave;

public class Player extends Entity{
   public Rectangle redBlood;
   public Rectangle bloodbarBase;
   public ImageView bloodbar;
   public boolean hitByBullet = false;
   public boolean damaged =false;
   public boolean newBornInGame = true;
   public double health_value;

   private static double[] Blood_pos = {10,30};
   private Image blood;
   private List<Image> idle,run,jump,idleFlip,runFlip,jumpFlip;
   private boolean flip = false,fliped = false;
   private int anim_timer=0,anim_type=0,preAct=0,preIndex=0;
   private int timer=0;//timer for damaged animation

   public Player(double x,double y) throws FileNotFoundException{

      idle = splitSprite(new Image(new FileInputStream("pic/playerIdle.png")),1,12);
      run = splitSprite(new Image(new FileInputStream("pic/playerRun.png")),1,8);
      jump = splitSprite(new Image(new FileInputStream("pic/playerJump.png")),1,4);
      idleFlip=flipSprite(idle);
      runFlip=flipSprite(run);
      jumpFlip=flipSprite(jump);

      image = idle.get(0);
      flipimage = getFlip(image);
      sprite = new ImageView(image);
      redBlood = new Rectangle();
      redBlood.setFill(Color.RED);
      bloodbarBase = new Rectangle();
      bloodbarBase.setFill(Color.GRAY);
      blood = new Image(new FileInputStream("pic/health_bar.png"));
      bloodbar = new ImageView(blood);
      hitbox = new Rectangle();
      hitbox.setFill(Color.TRANSPARENT);
      hitbox.setStroke(Color.LIGHTGREEN);
      hitbox.setStrokeWidth(2);
      sprite.setSmooth(true);
      bloodbar.setSmooth(true);
      //sprite.setPreserveRatio(true);
      setSize(200,280);
      setPos(x,y);
   }

   @Override
   public void setPos(double x,double y){
      Pos[0] = x;
      Pos[1] = y;

      hitbox.setX((Pos[0]-Width/2-Cam[0])*ratio[0]);
      hitbox.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
      hitbox.setWidth(Width*ratio[0]); 
      hitbox.setHeight(Height*ratio[1]);

      sprite.setFitWidth(Width*ratio[0]);
      sprite.setFitHeight(Height*ratio[1]);
      sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
      sprite.setY((1090-Pos[1]-Height+Cam[1])*ratio[1]);

      redBlood.setX((Blood_pos[0]+100)*ratio[0]);
      redBlood.setY((Blood_pos[1]+70)*ratio[1]);
      redBlood.setWidth(Inject());
      redBlood.setHeight(20);

      bloodbarBase.setX((Blood_pos[0]+10)*ratio[0]);
      bloodbarBase.setY((Blood_pos[1]+70)*ratio[1]);
      bloodbarBase.setWidth(400);
      bloodbarBase.setHeight(20);

      bloodbar.setX(Blood_pos[0]*ratio[0]);
      bloodbar.setY(Blood_pos[1]*ratio[1]);
      bloodbar.setFitWidth(450);
      bloodbar.setFitHeight(150);
   }


   public void Camera(){
      World[0]=Pos[0]-960;
      World[1]=Pos[1]-100;

      if(World[0]-Cam[0]>80*ratio[0]){
         Cam[0]=World[0]-80*ratio[0];
      }
      else if(World[0]-Cam[0]<-80*ratio[0]){
         Cam[0]=World[0]+80*ratio[0];
      }
      if(World[1]-Cam[1]>300*ratio[1]){
         Cam[1]=World[1]-300*ratio[1];
      }
      else if(World[1]-Cam[1]<0){
         Cam[1]=World[1];
      }
   }

   public double Inject(){
      if(newBornInGame){
         health_value = 325;
         newBornInGame = false;
      }else{
         if(hitByBullet){
            health_value = redBlood.getWidth()-50;
            redBlood.setWidth(health_value);
            hitByBullet = false;
            damaged = true;
            if(redBlood.getWidth()<=0){
               setMy(0);
               Cam[0]=0;
               Cam[1]=0;
               World[0]=0;
               World[1]=0;
               newBornInGame = true;
               damaged = false;
            }
         }
      }

      return health_value;
   }

   public List<Image> splitSprite(Image img,int row,int col){
      int width = 768,height = 1024;
      int count=0,col_count=0;
      List<Image> anim = new ArrayList<>();
      PixelReader pixelReader = img.getPixelReader();

      for(int r=0; r<row; r++){
         for(int c=0; c<col; c++){
            WritableImage c_img = new WritableImage(width,height);
            PixelWriter writer = c_img.getPixelWriter();
            for(int i=r*height; i<(r+1)*height; i++){
               for(int j=c*width; j<(c+1)*width; j++){
                  Color color = pixelReader.getColor(j,i);
                  writer.setColor(j%width,i%height,color);
               }
            }
            anim.add((Image)c_img);
         }
      }
      return anim;
   }
   public List<Image> flipSprite(List<Image> arr){
      List<Image> fliparr = new ArrayList<>();
      for(int i=0;i<arr.size();i++){
         fliparr.add(getFlip(arr.get(i)));
      }
      return fliparr;
   }
   public void runAnim(){
      if(preAct!=anim_type){
         anim_timer=0;
         fliped = false;
         preAct=anim_type;
      }
      double index_DOUBLE=((double)anim_timer)/7.5*frameRate;
      int index = (int)index_DOUBLE;
      if(preIndex!=index){
         preIndex=index;
      }
      if(anim_type==0){
         if(index==idle.size()){
            anim_timer=0;
            image=idle.get(0);
            flipimage=(WritableImage)idleFlip.get(0);
         }
         else{
            image=idle.get(index);
            flipimage=(WritableImage)idleFlip.get(index);
         }
      }
      else if(anim_type==1){
         if(index==run.size()) {
            anim_timer=0;
            image=run.get(0);
            flipimage=(WritableImage)runFlip.get(0);
         }
         else {
            image=run.get(index);
            flipimage=(WritableImage)runFlip.get(index);
         }
      }
      else if(anim_type==2){
         if(index>=jump.size()-1) index=jump.size()-1;
         image=jump.get(index);
         flipimage=(WritableImage)jumpFlip.get(index);
      }

      if(flip){
         fliped = true;
         sprite.setImage(flipimage);
      }else{
         fliped = false;
         sprite.setImage(image);
      }

      anim_timer++;
   }

   @Override
   public void act(){

      if(Motion[0]<=1&&Motion[0]>=-1&&landing&&Motion[1]<=0&&Motion[1]>-0.3){
         anim_type=0;
      }
      if(!damaged){
         if(Rightpress == true){
            if(Shift&&landing) Motion[0] = 10;
            else if(Motion[0]<=3) Motion[0] = 6;
            if(landing&&Motion[1]<=0&&Motion[1]>-0.3)
               anim_type=1;
            flip=false;
         }

         else if(Leftpress == true){
            if(Shift&&landing) Motion[0] = -10;
            else if(Motion[0]>=-3)Motion[0] = -6;
            if(landing&&Motion[1]<=0&&Motion[1]>-0.3)
               anim_type=1;
            flip=true;
         }
      }
      else {
         Motion[0]=-10;
         timer++;
         if(timer>20/frameRate||!damaged){//let damaged be false when respwan
            timer=0;
            damaged=false;
         }
      }

      if(Jump == true && landing == true){
         if(Jumped == false){    //haven't Jumped
            Motion[1] = 22.4;//10
            Jumped = true;
            anim_type=2;
         }
      }else if(landing == true) Jumped = false;

      if(Motion[0] != 0){        //slow down when movement key released
         if(Motion[0]>0.1){
            if(collideh == 1){
               Motion[0]=0;
            }else if(landing){
               Motion[0]-=0.2;
            }
         }
         else if(Motion[0]<-0.1){
            if(collideh == 2){
               Motion[0]=0;
            }else if(landing){
               Motion[0]+=0.2;
            }
         }
         else Motion[0]=0;
      }

      if(collidev == 2){      //hit upside block
         if(Motion[1]>0) Motion[1]=0;
      }

      if(collidev == 1){      //hit the ground
         landing = true;
         if(Motion[1]<0) Motion[1]=0;
      }else{                  //falling
         landing = false;
         Motion[1]-=0.6*frameRate;
      }
      runAnim();
      Camera();
      //System.out.println(frameRate);
      setPos(getX()+(Motion[0]*frameRate),getY()+cancel(Motion[1]*frameRate));
   }
 }