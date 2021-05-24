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
import javafx.stage.Stage;  

public class Openning{

    public Boolean isStart = false;
    public int time = 0;
    public int step =0;

    //the lower one
    private Image hint = new Image("pic/loginhint.png");
    private ImageView hintView = new ImageView(hint);

    //the upper one
    private Image title = new Image("pic/logintitle.png");
    private ImageView titleView = new ImageView(title);

    //background
    private Image screen = new Image("pic/loginscreen.png");
    private ImageView screenView = new ImageView(screen);

    //loadingImage
    private Image loadingScreen = new Image("pic/loadingscreen.png");
    private ImageView loadingView = new ImageView(loadingScreen);

    public void openningScreen(Group root, Stage stage, Scene scene){

        hintView.setX(stage.getWidth()/2-hint.getWidth()/2);
        hintView.setY(stage.getHeight()*3/4-hint.getHeight()/2);

        titleView.setX(stage.getWidth()/2-title.getWidth()/2);
        titleView.setY(stage.getHeight()/3-title.getHeight()/2);

        root.getChildren().add(screenView);
        root.getChildren().add(hintView);
        root.getChildren().add(titleView);
        
    }

    public void setImage(Stage stage){
        hintView.setX(stage.getWidth()/2-hint.getWidth()/2);
        hintView.setY(stage.getHeight()*3/4-hint.getHeight()/2);

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

        LoadSave.reset();
        loadingIn(root);
    }

    public void loadingIn(Group root){
        loadingOut(root);
        root.getChildren().add(loadingView);
    }

    public void loadingOut(Group root){
        root.getChildren().remove(loadingView);
    }

}
