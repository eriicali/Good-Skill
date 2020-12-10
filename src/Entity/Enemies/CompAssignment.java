package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// the assignments represent enemies
public class CompAssignment extends Enemy{
    private  BufferedImage[] sprites;
    public CompAssignment(TileMap tm) {
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
        health = maxHealth =2;
        damage = 1;

        //load sprites
        try{
            //read sprites from file
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Enemies/compassignment.gif")
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

    private void getNextPosition(){
        // move back and forth between walls
        // movement to the left
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
        // movement to the right
        else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        // character is falling
        if (falling) {
            //if falls off cliff
            dy +=fallSpeed;
        }

    }

    public void update(){
        //update position
        getNextPosition();
        // check if the character's coordinates are overlapping with a tile
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching
        if (flinching) {
            long elapsed = (System.nanoTime()-flinchTimer)/1000000;
            if(elapsed>400){
                flinching = false;
            }
        }
        //if it hits wall switch directions
        //dx automatically set to 0 when hits wall MapObject
        if (right && dx == 0) {
            //hit a wall
            right=false;
            left=true;
            facingRight=false;
        }
        else if (left && dx == 0) {
            right = true;
            left = false;
            facingRight=true;
        }
        //update animation
        animation.update();
    }
    //override parent class to draw assignment on screen
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);

    }
}
