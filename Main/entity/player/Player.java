package entity.player;

import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color; 

public class Player {  
    private Image image ,imageS;
    private WritableImage flipimage ,flipimageS;
    private double[] Pos = {0,0};
    private double[] Motion = {0,0};
    private int Width = 0,Height = 0;
    private double[] ratio={1,1};
    
    public boolean Rightpress = false ,Leftpress = false ,Jump = false ,Jumped = false ,landing = false ,Spress = false ,isRight = true;
    public ImageView player;
 
    public Player(int x,int y) throws FileNotFoundException{
       image = new Image(new FileInputStream("pic/test1.png"));
       int w=(int)image.getWidth(),h=(int)image.getHeight();
       flipimage = new WritableImage(w,h);
       imageS = new Image(new FileInputStream("pic/test2.png"));
       int wS=(int)imageS.getWidth(),hS=(int)imageS.getHeight();
       flipimageS = new WritableImage(wS,hS);
 
       PixelReader pixelReader = image.getPixelReader(); 
       PixelWriter writer = flipimage.getPixelWriter(); 
 
       for(int col=0;col<h;col++){
          for(int row=0;row<w;row++){
             Color color = pixelReader.getColor(row,col); 
             writer.setColor(w-row-1, col, color);
          }
       }

       PixelReader pixelReaderS = imageS.getPixelReader(); 
       PixelWriter writerS = flipimageS.getPixelWriter(); 
 
       for(int col=0;col<hS;col++){
          for(int row=0;row<wS;row++){
             Color color = pixelReaderS.getColor(row,col); 
             writerS.setColor(wS-row-1, col, color);
          }
       }

       player = new ImageView(image);
       player.setSmooth(true);
       player.setPreserveRatio(true);
       Setsize(336,231);
       Setpos(x,y); 
    }
 
    public void Setpos(double x,double y){
       Pos[0] = x;
       Pos[1] = y;
       player.setFitWidth(Width*ratio[0]); 
       player.setFitHeight(Height*ratio[1]);
       player.setX((Pos[0]-Width/2)*ratio[0]); 
       player.setY((1080-Pos[1]-Height)*ratio[1]); 
    }
 
    public void Setsize(int w,int h){
       Width = w;
       Height = h;
    }
 
    public double Getx(){
       return Pos[0];
    }
 
    public double Gety(){
       return Pos[1];
    }
 
    public void SetScreenSize(double x,double y){
       ratio[0]=x/1920;
       ratio[1]=y/1080;
    }
 
    public void act(){
       
       if(Motion[0] != 0){
          if(Motion[0]>0.1) Motion[0]-=0.1;
          else if(Motion[0]<-0.1) Motion[0]+=0.1;
          else Motion[0]=0;
       }
       
       if(Pos[1]<=0){
          landing = true;
          if(Motion[1]<0) Motion[1]=0;
       }else{
          landing = false;
          Motion[1]-=0.3;
       }
       
       if(Jump == true && landing == true){
          if(Jumped == false){
             Motion[1] = 15;
             Jumped = true;
          }
       }else if(landing == true) Jumped = false;
 
       if(Rightpress == true){
          Motion[0] = 3;
          player.setImage(image);
          isRight = true;
       }
       else if(Leftpress == true){
          Motion[0] = -3;
          player.setImage(flipimage);
          isRight = false;
       }

       if(Spress == true){
         if(isRight == true){
            player.setImage(imageS);
          }
          else{
            player.setImage(flipimageS);
          }
       }
       else{
          if(isRight == true){
            player.setImage(image);
          }
          else{
            player.setImage(flipimage);
          }
       }
       Setpos(Getx()+Motion[0],Gety()+Motion[1]);
    }
 }