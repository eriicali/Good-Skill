package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// the assignments represent enemies
public class WritingAssignment extends Enemy{
    private  BufferedImage[] sprites;
    public WritingAssignment(TileMap tm) {
        super(tm);
        loadSprites("/Enemies/writingassignment.gif");

    }
}
