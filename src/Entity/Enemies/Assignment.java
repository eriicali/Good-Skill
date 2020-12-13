package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// the assignments represent enemies
public class Assignment extends Enemy{
    private  BufferedImage[] sprites;
    public Assignment(TileMap tm) {
        super(tm);
        loadSprites("/Enemies/homework.gif");
    }

}
