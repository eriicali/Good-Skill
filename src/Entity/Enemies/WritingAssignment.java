package Entity.Enemies;
import TileMap.TileMap;
import java.awt.image.BufferedImage;


// the assignments represent enemies
public class WritingAssignment extends Enemy{
    // constructor
    public WritingAssignment(TileMap tm) {
        // calls parent constructor
        super(tm);
        // loads sprites
        loadSprites("/Enemies/writingassignment.gif");
    }
}
