package Entity;
import TileMap.*;
//keeping abstract
public abstract class Enemy extends MapObject{
    //reorganize vars later, put in MapObject class
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage; //on contact, player runs into enemy
    protected boolean flinching;
    protected long flinchTimer;

    public Enemy (TileMap tm){
        super(tm);
    }

    public boolean isDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

    public void hit (int damage){
        if(dead||flinching) return;
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }
    public void update(){

    }
}
