package Main;
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
import javafx.stage.Stage;  

public class Main extends Application {  
   @Override 
   public void start(Stage stage) throws FileNotFoundException {         
      //Creating an image 
      Image image = new Image(new FileInputStream("pic/test1.png"));  
      
      //Setting the image view 
      ImageView imageView = new ImageView(image); 
      
      //Setting the position of the image 
      imageView.setX(960); 
      imageView.setY(540); 
      
      //setting the fit height and width of the image view 
      imageView.setFitHeight(231); 
      imageView.setFitWidth(336); 
      
      //Setting the preserve ratio of the image view 
      imageView.setPreserveRatio(true);  
      
      //Creating a Group object  
      Group root = new Group(imageView);  
      
      //Creating a scene object 
      Scene scene = new Scene(root, 1920, 1080);  
      
      //Setting title to the Stage 
      stage.setTitle("Loading an image");  
      
      //Adding scene to the stage 
      stage.setScene(scene);
      
      //Displaying the contents of the stage 
      stage.show(); 
   } 

   public static void main(String args[]) { 
      launch(args); 
   } 
}
