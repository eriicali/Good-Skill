package Entity.Drinks;
import Entity.MapObject;
import TileMap.*;

import java.awt.*;

// keeping abstract
public abstract class Beverage extends MapObject {

    protected boolean pickedUp;
    protected int health;

    public Beverage (TileMap tm){
        super(tm);
        pickedUp = false;
        health = 1;

        width = 30;
        height = 30;
        characterWidth = 20;
        characterHeight = 20;
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
    public void update(){
        //put object on tilemap
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        //update animation
        animation.update();
    }
    //override parent class to draw assignment on screen
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);

    }

}
