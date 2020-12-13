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
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        // make function later to find center upper corner
        g.drawString("You Passed!", 80, 30);
        g.drawString("Assignments: /5", 130, 45);
        g.drawString("Final Grade", 130, 58);
    }

}
