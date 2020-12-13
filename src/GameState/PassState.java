package GameState;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PassState extends NotFailState {
    //protected String[] options;
    public PassState(GameStateManager gsm) {
        super(gsm);
        setBackground("/grades.gif");
        options[0] = "Replay";
        options[1] = "Quit";
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        titleColor = new Color(207, 227, 236);
    }




    public void draw(Graphics2D g) {
        super.draw(g);
        // make function later to find center upper corner
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("You Passed!", 80, 30);    }

}
