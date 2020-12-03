package TileMap;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

public class Tile {
    private BufferedImage image;
    private int type;
    
    // tile types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;
    
    public Tile(BufferedImage image, int type){
        this.image = image;
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public BufferedImage getImage(){
        return this.image;
    }
    
}
