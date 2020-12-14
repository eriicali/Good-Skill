package Entity.Enemies;
import TileMap.TileMap;

// the assignments represent enemies
public class Assignment extends Enemy{
    // constructor and loads sprites
    public Assignment(TileMap tm) {
        // calls parent constructor (enemy)
        super(tm);
        // loads sprites
        loadSprites("/Enemies/homework.gif");
    }

}
