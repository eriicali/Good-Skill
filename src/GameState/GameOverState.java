package GameState;
import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverState extends GameState {
    protected Background bg;
    protected int currentChoice = 0;
    protected String[] options = new String[2];
    protected Color titleColor;
    protected Font titleFont;
    protected Font font;

    public GameOverState(GameStateManager gsm) {
        this.gsm=gsm;
    }

    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setFont(font);

    }

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
    public void update() {
        bg.update();
    }

    public void select() {
        if (currentChoice == 0) {
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if (currentChoice == 1) {
            System.exit(0);
        }
    }

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
    public void keyReleased(int key) {}

    public void init() { }
}
