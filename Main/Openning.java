import entity.*;
import map.*;
import loadSave.*;
import java.util.*;
import java.math.BigInteger;
import static java.lang.System.out;
import javafx.application.Application; 
import java.io.FileNotFoundException;
import java.io.FileInputStream; 
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;  

public class Openning{

    public boolean isStart = false;
    public boolean isBefore = false;
    public boolean isDead = false;
    public boolean isReborn = false;
    public boolean isWin = false;
    public boolean isAfter = false;
    public double lightDegree = 0.1;
    public int time = 0;
    public int t;
    public int step =0;

    //the lower one
    private Image hint = new Image("pic/loginhint.png");
    private ImageView hintView = new ImageView(hint);

    //the upper one
    private Image title = new Image("pic/logintitle(.ver2).png");
    private ImageView titleView = new ImageView(title);

    //background
    private Image screen = new Image("pic/loginscreen.png");
    private ImageView screenView = new ImageView(screen);

    private Image deadMBase = new Image("pic/deadmessage.png");
    private ImageView deadMBaseView = new ImageView(deadMBase);

    public Rectangle bar = new Rectangle();
    public Rectangle stroke = new Rectangle();
    public Rectangle dScreen= new Rectangle();

    private Rectangle transparentOne = new Rectangle();
    private Rectangle blackOne = new Rectangle();

    public void openningScreen(Group root, Stage stage, Scene scene){

        hintView.setX(stage.getWidth()/2-hint.getWidth()/2);
        hintView.setY(stage.getHeight()*8/9-hint.getHeight()/2);

        titleView.setX(stage.getWidth()/2-title.getWidth()/2);
        titleView.setY(stage.getHeight()/3-title.getHeight()/2);

        root.getChildren().add(screenView);
        root.getChildren().add(hintView);
        root.getChildren().add(titleView);
        
    }

    public void setImage(Stage stage){
        hintView.setX(stage.getWidth()/2-hint.getWidth()/2);
        hintView.setY(stage.getHeight()*8/9-hint.getHeight()/2);

        titleView.setX(stage.getWidth()/2-title.getWidth()/2);
        titleView.setY(stage.getHeight()/3-title.getHeight()/2);
    }

    public void start(Group root, Stage stage, Scene scene){

        isStart = true;
        time = 0;
        step = 0;

        root.getChildren().remove(screenView);
        root.getChildren().remove(hintView);
        root.getChildren().remove(titleView);

        title = new Image("pic/loadingtitle.png");
        titleView = new ImageView(title);
        loadingIn(root,stage);
    }

    public void loadingIn(Group root,Stage stage){
        bar.setHeight(10);
        bar.setWidth(640*step);
        bar.setX(0);
        bar.setY(800);
        bar.setFill(Color.WHITE);
        bar.setStroke(Color.WHITE);

        stroke.setHeight(10);
        stroke.setWidth(1920);
        stroke.setX(0);
        stroke.setY(800);
        stroke.setFill(Color.rgb(255,255,255,0.3));
        stroke.setStroke(Color.WHITE);

        titleView.setX(stage.getWidth()/2-title.getWidth()/2);
        titleView.setY(stage.getHeight()/3-title.getHeight()/2);

        loadingOut(root);

        screen = new Image("pic/loadingscreen.png");
        screenView = new ImageView(screen);

        root.getChildren().add(screenView);
        root.getChildren().add(stroke);
        root.getChildren().add(bar);
        root.getChildren().add(titleView);
    }

    public void beforestory(Group root,Stage stage){

        root.getChildren().remove(bar);
        root.getChildren().remove(stroke);
        root.getChildren().remove(titleView);

        title = new Image("pic/beforetitle.png");
        titleView = new ImageView(title);

        titleView.setX(stage.getWidth()/2-title.getWidth()/2);
        titleView.setY(stage.getHeight()/2-title.getHeight()/2);
        root.getChildren().add(titleView);

        hintView.setY(stage.getHeight()*18/19-hint.getHeight()/2);
        root.getChildren().add(hintView);
    }

    public void loadingOut(Group root){
        root.getChildren().remove(screenView);
        root.getChildren().remove(bar);
        root.getChildren().remove(stroke);
        root.getChildren().remove(titleView);
        root.getChildren().remove(hintView);
    }

    public void deadScreen(Group root, Stage stage){
        dScreen.setFill(Color.rgb(105,0,0,lightDegree));
        dScreen.setStroke(Color.rgb(105,0,0,lightDegree));
        dScreen.setHeight(1080);
        dScreen.setWidth(1920);
        dScreen.setX(0);
        dScreen.setY(0);

        deadMBaseView.setX(stage.getWidth()/2-deadMBase.getWidth()/2);
        deadMBaseView.setY(stage.getHeight()/2-deadMBase.getHeight()/2);

        hintView.setX(stage.getWidth()/2-hint.getWidth()/2);
        hintView.setY(stage.getHeight()*8/9-hint.getHeight()/2);

        time = 0;
        isDead = true;

        root.getChildren().add(dScreen);
        root.getChildren().add(hintView);
        root.getChildren().add(deadMBaseView);
    }

    public void deadSdarker(){
        
        time = 0;
        lightDegree += 0.1;
        dScreen.setFill(Color.rgb(105,0,0,lightDegree));
        dScreen.setStroke(Color.rgb(105,0,0,lightDegree));
    }

    public void reborn(Group root){

        isReborn = true;
        t = 0;
        time = 0;
        
        blackOne.setFill(Color.rgb(0,0,0));
        blackOne.setStroke(Color.rgb(0,0,0));
        blackOne.setX(0);
        blackOne.setY(0);
        blackOne.setHeight(0);
        blackOne.setWidth(1920);

        transparentOne.setFill(Color.rgb(0,0,0,lightDegree));
        transparentOne.setStroke(Color.rgb(0,0,0,lightDegree));
        transparentOne.setX(0);
        transparentOne.setY(0);
        transparentOne.setHeight(13);
        transparentOne.setWidth(1920);

        root.getChildren().add(blackOne);
        root.getChildren().add(transparentOne);

        dScreen.setFill(Color.rgb(105,0,0));
        dScreen.setStroke(Color.rgb(105,0,0));

    }

    public void rebornLoading(Group root){

        time = 0;
        t += 13;

        if(t <= 1080){
            blackOne.setHeight(t);
            transparentOne.setHeight(13 + t);
        }
        else if(t <= 2160){
            root.getChildren().remove(dScreen);
            root.getChildren().remove(hintView);
            root.getChildren().remove(deadMBaseView);
            blackOne.setHeight(2160-t);
            transparentOne.setHeight(2173-t);
        }
        else{
            isDead = false;
            lightDegree = 0;
            isReborn = false;
            root.getChildren().remove(blackOne);
            root.getChildren().remove(transparentOne);
        }
    }

    public void win(Group root, Stage stage, List<Entity> entity, Player p){
        isWin = true;

        lightDegree = 1;
        
        dScreen.setFill(Color.rgb(255,255,255,lightDegree));
        dScreen.setStroke(Color.rgb(255,255,255,lightDegree));
        dScreen.setHeight(1080);
        dScreen.setWidth(1920);
        dScreen.setX(0);
        dScreen.setY(0);

        title = new Image("pic/wintitle.png");
        titleView = new ImageView(title);

        titleView.setX(stage.getWidth()/2-title.getWidth()/2);
        titleView.setY(stage.getHeight()/2-title.getHeight()/2);

        hintView.setX(stage.getWidth()/2-hint.getWidth()/2);
        hintView.setY(stage.getHeight()*8/9-hint.getHeight()/2);

        root.getChildren().add(dScreen);
        root.getChildren().add(titleView);
        root.getChildren().add(hintView);
    }

    public void winstart(Group root){
        time = 0;
        lightDegree -= 0.1;
        if(lightDegree <= 0){
            root.getChildren().remove(dScreen);
            isWin = false;
        }
        else{
            dScreen.setFill(Color.rgb(255,255,255,lightDegree));
            dScreen.setStroke(Color.rgb(255,255,255,lightDegree));
        }
    }

    public void afterstory(Group root,Player p){
        isAfter = true;

        root.getChildren().remove(titleView);
        root.getChildren().remove(hintView);
        p.setPos(1541,400);

        winstart(root);
    }

}
