package GameState;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
    private Background bg;
    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Help",
            "Quit"
    };
    private Color titleColor;
    private Font titleFont;
    private Color selectionColor;
    private Font font;
    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            bg= new Background("/Backgrounds/menubg.gif",1);
            selectionColor = new Color(156, 62, 40);
            titleColor = new Color(207, 227, 236);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
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
       g.drawString("Good Skill", 90, 45);

       g.setFont(font);

       for (int i =0; i< options.length;i++){
           if (i == currentChoice) {
               g.setColor(selectionColor);
               g.drawString("<-",75, 120+i*15);
           }
           else {
               g.setColor(titleColor);
           }
           g.drawString(options[i], 46, 120+i*15);
       }
    }
    public void select() {
        if (currentChoice == 0) {
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if (currentChoice == 1) {
            // help
        }
        if (currentChoice == 2) {
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
