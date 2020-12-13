package GameState;

public abstract class GameState
{
    protected GameStateManager gsm;
    // these are all abstract methods that will be implemented in the child classes of GameState
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int key);
    public abstract void keyReleased(int key);
}
