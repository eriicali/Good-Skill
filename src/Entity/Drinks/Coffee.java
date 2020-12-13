package Entity.Drinks;
import TileMap.TileMap;
public class Coffee extends Beverage {
    public Coffee(TileMap tm) {
        super(tm);
        loadSprites("/Drinks/coffee.gif");
    }
}
