package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private int type;

    // tile player can traverse through
    public static final int NORMAL = 0;
    // tile player cannot traverse through
    public static final int BLOCKED = 1;

    // constructor of tile
    public Tile(BufferedImage image, int type){
        this.image = image;
        this.type = type;
    }
    // returns type of tile
    public int getType(){
        return this.type;
    }

    // returns image of tile
    public BufferedImage getImage(){
        return this.image;
    }
    
}
