package Entity.Enemies;
import Entity.MapObject;
import TileMap.*;

import java.awt.*;

public abstract class Enemy extends MapObject {
    // init variables
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage; //on contact, player runs into enemy
    protected boolean flinching;
    protected long flinchTimer;

    // constructor
    public Enemy (TileMap tm){
        super(tm);
        //enemy stats
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        // enemy properties
        width = 30;
        height = 30;
        characterWidth = 20;
        characterHeight = 20;
        health = maxHealth = 2;
        // damage dealt to player
        damage = 1;
    }

    // finds next position of enemy
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
            dy += fallSpeed;
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
            if(elapsed > 400){
                flinching = false;
            }
        }
        //if it hits wall switch directions
        //dx automatically set to 0 when hits wall MapObject
        if (right && dx == 0) {
            //hit a wall
            right = false;
            left = true;
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

    // gets dead value
    public boolean getDead() {
        return dead;
    }

    // gets damage value of enemy
    public int getDamage() {
        return damage;
    }

    // this method is called when the enemy is hit by the player
    public void hit (int damage){
        // if the enemy is dead or flinching don't do anything
        if(dead||flinching) return;
        // otherwise remove some of their hit points
        health -= damage;
        // if health becomes less than 0, set back to 0
        if(health < 0) health = 0;
        // if health is = 0, then the enemy is dead
        if(health == 0) dead = true;
        // set flinching to true
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    //override parent class to draw assignment on screen
    public void draw(Graphics2D g){
        // sets map position of the map object / enemy
        setMapPosition();
        // draws enemy
        super.draw(g);
    }
}
