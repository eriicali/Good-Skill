package GameState;
import java.awt.*;

// this class inherits the GameOverState Class
public abstract class FailureState extends GameOverState {
    protected String string1;
    protected String string2;
    // constructor
    public FailureState(GameStateManager gsm) {
        // calls the super constructor
        super(gsm);
        // options is an array instantiated in GameOverState
        options[0] = "Replay";
        options[1] = "Quit";
    }
    // this method draws the failure text on the screen
    public void draw(Graphics2D g) {
        // calls the super method in GameOverState
        super.draw(g);
        g.drawString(string1, 18, 30);
        g.drawString(string2, 18, 50);
        // iterates through the options (replay and quit)
        // whichever option the player is currently selecting will turn red
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
