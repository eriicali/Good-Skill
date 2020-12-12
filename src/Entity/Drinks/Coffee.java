package Entity.Drinks;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Coffee extends Beverage {
    private  BufferedImage[] sprites;
    public Coffee(TileMap tm) {
        super(tm);
        //enemy stats
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        characterWidth = 20;
        characterHeight = 20;


        //load sprites
        try{
            //read sprites from file
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Drinks/coffee.gif")
            );

            sprites = new BufferedImage[3];
            //get individual images from sprites sheet (many small images to make animations)
            for (int i=0; i<sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }
            //changed to IOException instead of Exception
        }catch(IOException e){
            e.printStackTrace();
        }
        // create new animation object and set the sprites as the frames of the animation
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;//assignment starts heading right
        facingRight = true;
    }


    public void update(){
        //update position

        // check if the character's coordinates are overlapping with a tile
        checkTileMapCollision();

        setPosition(xtemp, ytemp);
        //if intersects with player remove coffee and add health to the player

        //if it hits wall switch directions
        //dx automatically set to 0 when hits wall MapObjec
        //update animation
        animation.update();
    }
    //override parent class to draw assignment on screen
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);

    }
}
