package Entity.Enemies;
import TileMap.TileMap;

// the assignments represent enemies
public class CompAssignment extends Enemy{
    // constructor and loads sprites
    public CompAssignment(TileMap tm) {
        // calls parent constructor
        super(tm);
        // loads sprites
        loadSprites("/Enemies/compassignment.gif");
    }

}
