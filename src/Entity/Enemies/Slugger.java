package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Slugger extends Enemy{
    private  BufferedImage[] sprites;
    public Slugger(TileMap tm) {
        super(tm);
        //enemy stats
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cwidth = 20;
        cheight =20;
        health = maxHealth =2;
        damage = 1;

        //load sprites
        try{
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream("/Sprites/Enemies/homework.gif")
            );

            sprites = new BufferedImage[3];
            for (int i=0; i<sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;//slugger starts heading right
        facingRight = true;
    }

    private void getNextPosition(){
        //move back and forth bw walls
        //movement
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
        else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        if (falling) {
            //if falls off cliff
            dy +=fallSpeed;
        }

    }

    public void update(){
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching
        if (flinching) {
            long elapsed = (System.nanoTime()-flinchTimer)/1000000;
            if(elapsed>400){
                flinching = false;
            }
            //if it hits wall switch directions
            //dx automatically set to 0 when hits wall MapObject
        }
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
    public void draw(Graphics2D g){
        //if(notOnScreen()) return;//only draw if on the screen
        setMapPosition();
        super.draw(g);

    }
}
