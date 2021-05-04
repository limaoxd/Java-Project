package entity;

public class Obj extends Entity{
    public Player(int x,int y) throws FileNotFoundException{
        //image = new Image(new FileInputStream("pic/test1.png"));
        //flipimage = getFlip(image);
        sprite = new Rectangle(x,y,50,50);
        sprite.setSmooth(true);
        sprite.setPreserveRatio(true);
        setSize(50,50);
        setPos(x,y); 
    }
}