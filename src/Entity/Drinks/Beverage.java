package Entity.Drinks;
import Entity.MapObject;
import TileMap.*;
import java.awt.*;

//abstract ->cant instantiate beverage, only a specific type of beverage
public abstract class Beverage extends MapObject {

    protected boolean pickedUp;
    protected int health;
    //initialize attributes
    public Beverage (TileMap tm){
        super(tm);
        pickedUp = false;
        health = 1;

        width = 30;
        height = 30;
        characterWidth = 20;
        characterHeight = 20;
    }

    public void update(){
        //put object on tilemap
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        //update animation
        animation.update();
    }
    //override parent class to draw assignment on screen
    public void draw(Graphics2D g){
        //set objects position on the map
        setMapPosition();
        //call parent's method to handle the similar functionality
        super.draw(g);
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
    public boolean getPickedUp() {
        return pickedUp;
    }
    public int getHealth() {
        return health;
    }
}
