package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InkBlob extends MapObject {
    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
    
    public InkBlob(TileMap tm, boolean right){
        super(tm);
        facingRight=right;

        moveSpeed = 3.8;
        if(right) dx = moveSpeed;
        else dx = -moveSpeed;
        width = 30;
        height = 30;
        characterWidth = 14;
        characterHeight = 14;
        loadSprites("/Player/inkblob.gif");

    }
    public void loadSprites(String filename){
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites"+filename));
            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
            hitSprites = new BufferedImage[3];
            for(int i = 0; i < hitSprites.length; i++){
                hitSprites[i] = spritesheet.getSubimage(i*width, height, width, height);
            }
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    // this method sets a hit from the inkBlob
    public void setHit(){
        hit = true;
        // changes the animation frames to the hit sprites
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx = 0;
    }
    
    public boolean getRemove() {return remove;}
    
    public void update() {
        // when the inkBlob exits the screen, remove it from the array list
        if(x > 2449){
            remove = true;
        }
        else{
            checkTileMapCollision();
            setPosition(xtemp, ytemp);
            if(dx == 0 && !hit){
                setHit();
            }
            animation.update();
            // once the inkBlob hits the wall or enemy, remove it from the array list
            // so the animation doesn't play more than once
            if(hit && animation.hasPlayedOnce()){
                remove = true;
            }
        }
    }

    // this method draws the inkBlob on the screen
    // calls the super method from the parent class MapObject (this is an example of inheritance)
    public void draw(Graphics2D g){
        //calls method in MapObject
        setMapPosition();
        super.draw(g);
    }
}
