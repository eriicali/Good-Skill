package Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import TileMap.TileMap;

import javax.imageio.ImageIO;

public class Splat extends MapObject {
    private int x;
    private int y;
    private int xmap;
    private int ymap;

    private int width;
    private int height;

    private Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    public Splat(int x, int y, TileMap tm) {
        super(tm);
        this.x = x;
        this.y = y;
        width = 30;
        height = 30;
        loadSprites("/Enemies/splat.gif");
    }

    //overriding parent methods
    //bc sprite image is different
    public void loadSprites(String Filename){
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/splat.gif"));
            sprites = new BufferedImage[6];
            for(int i =0; i<sprites.length;i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width,height);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }

    public void update() {
        animation.update();
        if(animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public boolean getRemove() {return remove;}

    //overriding for splat animation
    public void setMapPosition(int x, int y) {
        xmap = x;
        ymap = y;
    }
    public void draw(Graphics2D g) {
        g.drawImage(
                animation.getImage(),
                x + xmap - width /2,
                y + ymap - height/2,
                null
        );
    }
}
