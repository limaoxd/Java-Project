package entity;

import map.*;
import static java.lang.System.out;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Book extends Entity{

    public Book(int w,int h,double x,double y) throws FileNotFoundException{
        image=new Image(new FileInputStream("pic/p1.png"));
        sprite = new ImageView(image);
        setSize(w,h);
        setPos(x,y);
    }

    public static void createBook(List<Book> book) throws FileNotFoundException{
        int i=0;
        for(String col : MAP.book1){
            for(int j = 0;j<col.length();j++){
                if(col.charAt(j)<='9'&&col.charAt(j)>'0'){
                    int height = (int)col.charAt(j)-'0';
                    Book pillier = new Book(200,150*height,200*(j+0.5)+50,150*(MAP.book1.length-1-i));
                    book.add(pillier);
                }
            }
            i++;
         }
    }

    @Override
    public void setPos(double x,double y){
        Pos[0] = x;
        Pos[1] = y;
        sprite.setFitWidth(Width*ratio[0]);
        sprite.setFitHeight(Height*ratio[1]);
        sprite.setX((Pos[0]-Width/2-Cam[0])*ratio[0]); 
        sprite.setY((1080-Pos[1]-Height+Cam[1])*ratio[1]);
    }

    @Override
    public void act(){
        setPos(getX(),getY());
    }
}
