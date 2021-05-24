package loadSave;

import entity.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;

public class LoadSave {
    public static void reset(){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("loadSave/savedata.txt"));
            writer.println(" ");
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save(Player p){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("loadSave/savedata.txt"));
            writer.println(p.getX());
            writer.println(p.getY());
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        double[] temp = {0,0,0};
        try{
            int i = 0;
            Scanner scanner = new Scanner(new FileInputStream("loadSave/savedata.txt"));
            while(scanner.hasNextLine()){
                String aline = scanner.nextLine();
                temp[i] = Double.valueOf(aline);
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
