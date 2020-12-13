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
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        titleColor = new Color(207, 227, 236);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        // make function later to find center upper corner
        //SET FONT
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Good Skill", 90, 45);
    }

}
