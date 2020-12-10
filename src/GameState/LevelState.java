package GameState;

import Entity.Player;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class LevelState extends GameState {
    
    private Player player;
    public LevelState(TileMap tilemap){
        this.player = new Player(tilemap);
        
    }
    public LevelState(){
    
    }
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public void keyPressed(int k){
        
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFiring();
    }
    public abstract void keyReleased(int key);
}
