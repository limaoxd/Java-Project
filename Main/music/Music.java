package music;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;

public class Music {
    public static Media m = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/" + "music/StartingMusic.mp3");
    public static MediaPlayer mp = new MediaPlayer(m);

    public static void play(){
        mp.setVolume(1);
        mp.setAutoPlay(true);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();
    }

    public static void change(String name){
        mp.setVolume(0);
        m = new Media("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/" + "music/" + name);
        mp = new MediaPlayer(m);
        play();
    }
}
