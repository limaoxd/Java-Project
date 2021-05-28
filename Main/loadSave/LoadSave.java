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

    public static double[] temp = {0,0,0};
    public static int phase = 0;

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

    public static void save(Player p,Savepoint s){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("loadSave/savedata.txt"));
            writer.println(p.getX());
            writer.println(p.getY());
            writer.println(phase);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        switch(phase){
            case 0:
                s.posx = 6180;
                s.posy = 900;
                break;
            case 1:
                s.posx = 2080;
                s.posy = 1100;
                break;
            default:
                s.posx = 100;
                s.posy = 100;
                s.setSize(1,1);
                break;
        }
    }

    public static void load() {
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
