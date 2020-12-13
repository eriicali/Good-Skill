package GameState;
import java.awt.*;

public abstract class FailureState extends GameOverState {
    protected String string1;
    protected String string2;

    public FailureState(GameStateManager gsm) {
        super(gsm);
        options[0] = "Replay";
        options[1] = "Quit";
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawString(string1, 18, 30);
        g.drawString(string2, 18, 50);
        for (int i =0; i< options.length;i++){
            if (i == currentChoice) {
                g.setColor(Color.RED);
                g.drawString("<-",280, 140+i*15);
            }
            else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 230, 140+i*15);
        }
    }

}
