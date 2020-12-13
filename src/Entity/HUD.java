package Entity;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
    private Player player;
    private BufferedImage image;
    private Font font;

    public HUD(Player p) {
        player = p;
        //when instantiated, will display image to screen
        try{
            //read image from the file
            image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
            //decide the font
            font = new Font("Arial", Font.PLAIN, 14);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g){
        //draw image and text
        g.drawImage(image, 0, 10, null);
        g.setFont(font);
        g.setColor(Color.WHITE);
        //display player's current health and inkBlobs left
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(),20, 25);
        g.drawString(player.getInk()/100 + "/" + player.getMaxInk()/100, 20, 45);
    }
}
