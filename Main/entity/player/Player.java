import static java.lang.System.out;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 

public class Player {
    Image image = new Image(new FileInputStream("pic/test1.png"));  
    ImageView imageView = new ImageView(image); 
      
    imageView.setX(960); 
    imageView.setY(540); 
      
    imageView.setFitHeight(231); 
    imageView.setFitWidth(336); 
      
    imageView.setPreserveRatio(true);
}
