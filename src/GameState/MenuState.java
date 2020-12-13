package GameState;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends NotFailState {
    public MenuState(GameStateManager gsm) {
        super(gsm);
        setBackground("/menubg.gif");
        options[0] = "Start";
        options[1] = "Quit";
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        // make function later to find center upper corner
        g.drawString("Good Skill", 90, 45);
    }
    /*
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ENTER){
            select();
        }
        if (key == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length-1;
            }
        }
        if (key == KeyEvent.VK_DOWN){
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
    public void keyReleased(int key) {}*/
}
