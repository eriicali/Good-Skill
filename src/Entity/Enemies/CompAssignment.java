package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// the assignments represent enemies
public class CompAssignment extends Enemy{

    public CompAssignment(TileMap tm) {
        super(tm);
        loadSprites("/Enemies/compassignment.gif");
    }

}
