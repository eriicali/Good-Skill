package GameState;

import java.awt.*;

//
public abstract class NotFailState extends GameOverState {
    private Color selectionColor;
    public NotFailState(GameStateManager gsm) {
        super(gsm);
        selectionColor = new Color(156, 62, 40);

    }
    // overrides the draw() method in GameOverState
    public void draw(Graphics2D g) {
        super.draw(g);
        //
        for (int i =0; i< options.length;i++){
            if (i == currentChoice) {
                g.setColor(selectionColor);
                g.drawString("<-",75, 120+i*15);
            }
            else {
                g.setColor(titleColor);
            }
            g.drawString(options[i], 32, 120+i*15);
        }

    }

}
