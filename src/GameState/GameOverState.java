package GameState;
import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameOverState extends GameState {
    protected Background bg;
    protected int currentChoice = 0;
    protected String[] options = new String[2];
    protected Color titleColor;
    protected Font titleFont;
    protected Font font;
    // constructor
    public GameOverState(GameStateManager gsm) {
        this.gsm=gsm;
    }
    // this method draws something onto the background
    // it implements the draw() method from the GameState class
    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setFont(font);

    }
    // this method sets the background attribute of the GameOverState object
    // it uses a try catch to avoid throwing exceptions
    public void setBackground(String filename){
        try {
            bg= new Background("/Backgrounds"+filename,1);

            titleColor = new Color(255,255,255);
            titleFont = new Font("Century Gothic", Font.PLAIN, 15);
            font  = new Font("Arial",Font.PLAIN, 12);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // this implements the update() method from the GameState class
    public void update() {
        bg.update();
    }
    // this method either resets the state to LEVEL1STATE or exits the game
    public void select() {
        if (currentChoice == 0) {
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if (currentChoice == 1) {
            System.exit(0);
        }
    }
    // this method sets the current choice based the up/down key pressed by user
    // the currentChoice int wraps around back to the correct number if it becomes negative or larger than 1
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
    // this method implements the keyReleased() method in GameState
    public void keyReleased(int key) {}
    // this method implements the init() method in GameState
    public void init() { }
}
