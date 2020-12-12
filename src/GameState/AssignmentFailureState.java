package GameState;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AssignmentFailureState extends GameState
{
    private Background bg;
    private int currentChoice = 0;
    private String[] options = {
            "Restart",
            "Quit"
    };
    private Color titleColor;
    private Font titleFont;
    private Font font;
    public AssignmentFailureState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            bg= new Background("/Backgrounds/deathbyassignments.gif",1);

            titleColor = new Color(255,255,255);
            titleFont = new Font("Century Gothic", Font.PLAIN, 15);
            font  = new Font("Arial",Font.PLAIN, 12);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void init() { }

    public void update() {
        bg.update();
    }
    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        // make function later to find center upper corner
        g.drawString("You got crushed by", 18, 30);
        g.drawString("assignments :(", 18, 50);
        g.setFont(font);
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
}
