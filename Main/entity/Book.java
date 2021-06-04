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

    public Book(int w,int h,double x,double y,int type) throws FileNotFoundException{
        
        if(type==1) image=new Image(new FileInputStream("pic/p1.png"));
        else if(type==2) image=extendSprite(new Image(new FileInputStream("pic/spike_d.png")),w);
        else if(type==3) image=extendSprite(new Image(new FileInputStream("pic/spike_u.png")),w);
        sprite = new ImageView(image);
        setSize(w,h);
        setPos(x,y);
    }

    public static void createBook(List<Book> book) throws FileNotFoundException{
        int i=0;
        double length_2=0;
        double length_3=0;
        for(String col : MAP.book1){
            for(int j = 0;j<col.length();j++){
                if(col.charAt(j)>'0'&&col.charAt(j)<='9'){
                    int height = (int)col.charAt(j)-'0';
                    Book pillier = new Book(200,150*height,200*(j+0.5)+50,150*(MAP.book1.length-1-i),1);
                    book.add(pillier);
                }
                if(col.charAt(j)=='d'){
                    length_2++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Book spike = new Book((int)length_2*200,150,200*(j+1-length_2/2)+50,150*(MAP.map1.length-1-i),2);
                        book.add(spike);
                    }
                }
                else if(length_2>0){
                    Book spike = new Book((int)length_2*200,150,200*(j-length_2/2)+50,150*(MAP.map1.length-1-i),2);
                    book.add(spike);
                    length_2 = 0;
                }
                if(col.charAt(j)=='u'){
                    length_3++;
                    //If col end but number is "2"
                    if(j==col.length()-1){
                        Book spike = new Book((int)length_3*200,150,200*(j+1-length_3/2)+50,150*(MAP.map1.length-1-i),3);
                        book.add(spike);
                    }
                }
                else if(length_3>0){
                    Book spike = new Book((int)length_3*200,150,200*(j-length_3/2)+50,150*(MAP.map1.length-1-i),3);
                    book.add(spike);
                    length_3 = 0;
                }
            }
            i++;
         }
    }

    public Image extendSprite(Image img,int w){
        int width = 200,height = 150;
        int blocklength=w/width;

        PixelReader pixelReader = img.getPixelReader();

        WritableImage c_img = new WritableImage(w,height);
        PixelWriter writer = c_img.getPixelWriter();
        for(int i=0; i<height; i++){
            for(int j=0; j<w; j++){
                Color color = pixelReader.getColor(j%width,i);
                writer.setColor(j,i,color);
            }
        }
        return (Image)c_img;
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
