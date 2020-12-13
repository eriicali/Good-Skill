package TileMap;
import Main.GamePanel;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
    //init variables
    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double moveScale;

    // constructor
    public Background(String s, double ms) {
        // reads image as a resource stream and sets move scale
        try {
            image = ImageIO.read(
                getClass().getResourceAsStream(s)
            );
            moveScale = ms;
        }
        // catches exception
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // sets position of background when updates in level (aids with parallax effect)
    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }
    // updates moving vector (scale)
    public void update() {
        x += dx;
        y += dy;
    }

    // draws images for game states
    // draws parallax effect
    public void draw(Graphics2D g) {
        //draw image
        g.drawImage(image, (int)x,(int)y,null);
        // if x is smaller than 0 or smaller than 0, draw the image (cut off part) and the rest accordingly
        if(x < 0) {
            g.drawImage(image, (int)x+GamePanel.WIDTH, (int)y, null);
        }
        if (x>0) {
            g.drawImage(image, (int)x -GamePanel.WIDTH, (int) y, null);
        }
    }
}
