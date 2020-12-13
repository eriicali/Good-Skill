package Entity.Drinks;
import TileMap.TileMap;
public class Coffee extends Beverage {
    //when collected will increase player's health by 1
    //can only be collectd when player has less than 5 health
    public Coffee(TileMap tm) {
        //call super's constructor to initialize vars
        super(tm);
        //calls method in MapObjet ancestor to handling displaying images to screen
        //gives correct filename
        loadSprites("/Drinks/coffee.gif");
    }
}
