package Entity.Enemies;
import Entity.MapObject;
import TileMap.*;

import java.awt.*;

public abstract class Enemy extends MapObject{
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage; //on contact, player runs into enemy
    protected boolean flinching;
    protected long flinchTimer;

    public Enemy (TileMap tm){
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
    }

    private void findNextPosition(){
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
        // enemy is falling
        if (falling) {
            //if falls off cliff
            dy +=fallSpeed;
        }

    }

    public void update(){
        //update position
        findNextPosition();
        // check if the character's coordinates are overlapping with a tile
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching
        //helpful for stronger enemies, take multiple hits to defeat
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

    public boolean getDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

    // this method is called when the enemy is hit by the player
    public void hit (int damage){
        if(dead||flinching) return;
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    //override parent class to draw assignment on screen
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }
}
