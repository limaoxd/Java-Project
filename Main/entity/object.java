package entity;

public class Obj extends Entity{
    public Player(int x,int y) throws FileNotFoundException{
        //image = new Image(new FileInputStream("pic/test1.png"));
        //flipimage = GetFlip(image);
        sprite = new Rectangle(x,y,50,50);
        sprite.setSmooth(true);
        sprite.setPreserveRatio(true);
        Setsize(50,50);
        Setpos(x,y); 
    }
}
