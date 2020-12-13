package Main;

import GameState.GameStateManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

// uses runnable and keylistener interface for user input
public class GamePanel extends JPanel implements Runnable, KeyListener {

    // dimensions of the window panel
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    // game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    // images used
    private BufferedImage image;
    private Graphics2D g;

    // init game state manager
    private GameStateManager gsm;

    //constructor of game panel
    public GamePanel() {
        // calls parent constructor
        super();
        // sets preferred size of given dimensions
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }
    // overrides default and adds thread and key listener
    public void addNotify() {
        // calls parent
        super.addNotify();
        //adds thread and listener
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    // overrides required init function of game panel
    private void init() {
        // initializes objects needed
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        gsm = new GameStateManager();
    }

    // default running method
    public void run() {
        init();
        long start;
        long elapsed;
        long wait;

        // while running update and draw images
        while(running) {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) wait = 5;

            // makes game run at a slow pace for the player while running
            // otherwise game thread runs very fast
            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    // updates game state manager
    private void update() {
        gsm.update();
    }

    // draws graphics
    private void draw() {
        gsm.draw(g);
    }

    //used to draw graphics to screen when running
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0,
                WIDTH *SCALE, HEIGHT*SCALE,
                null);
        // closes resource stream
        g2.dispose();
    }

    // interface key listener
    public void keyTyped(KeyEvent key) {}
    // key pressed
    public void keyPressed(KeyEvent key) {
        // sends information to gsm
        gsm.keyPressed(key.getKeyCode());
    }
    // key released
    public void keyReleased(KeyEvent key) {
        // sends information to gsm
        gsm.keyReleased(key.getKeyCode());
    }
}
