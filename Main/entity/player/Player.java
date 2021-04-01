import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  

class Player {  
    private Image image;
    private float Pos_x = 0,Pos_y=0;
    private float Width = 0,Height = 0;
 
    public ImageView player;
 
    public Player(int x,int y) throws FileNotFoundException{
        image = new Image(new FileInputStream("../../pic/test1.png"));
        player = new ImageView(image);
        Setsize(336,231);
        Setpos(x,y);
        player.setPreserveRatio(true); 
    }
 
    public void Setpos(float x,float y){
        Pos_x = x;
        Pos_y = y;
        player.setX(Pos_x-Width/2); 
        player.setY(1080-Pos_y-Height); 
    }
 
    public void Setsize(int w,float h){
        Width = w;
        Height = h;
        player.setFitWidth(Width); 
        player.setFitHeight(Height);
    }
 
    public float Getx(){
        return Pos_x;
    }
 
    public float Gety(){
        return Pos_y;
    }
 }