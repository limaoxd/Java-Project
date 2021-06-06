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

    public static void reset(Savepoint s,Player p){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("loadSave/savedata.txt"));
            writer.println(p.getX());
            writer.println(p.getY()+100);
            writer.println(phase);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        s.posx = 9890;
        s.posy = 850;
    }

    public static void testSave(Player p){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("loadSave/savedata.txt"));
            writer.println(p.getX());
            writer.println(p.getY()+100);
            writer.println(phase);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save(Savepoint s){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("loadSave/savedata.txt"));
            writer.println(s.getX());
            writer.println(s.getY()+100);
            writer.println(phase);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        switch(phase){
            case 0:
                s.posx = 9890;
                s.posy = 850;
                break;
            case 1:
                s.posx = 10637;
                s.posy = 2350;
                break;
            case 2:
                s.posx = 4561;
                s.posy = 2350;
                break;
            case 3:
                s.posx = 107;
                s.posy = 1750;
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
