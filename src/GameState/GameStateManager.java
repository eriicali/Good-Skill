package GameState;

import java.util.ArrayList;
import Entity.*;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;
    public static final int MENUSTATE = 0;
    //do we really need this?
    public static final int LEVEL1STATE = 1;
    public static final int FAILURESTATE = 2;
    public static final int PASSSTATE =3;
    public static final int PROCRASTINATIONSTATE =4;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
        gameStates.add(new FailureState(this));
        gameStates.add(new PassState(this));
        gameStates.add(new ProcrastinationState(this));
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }
    public int getState(){
        return currentState;
    }
    public void update() {
        gameStates.get(currentState).update();
    }
    public void draw(java.awt.Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }
    public void keyPressed(int key) {
        gameStates.get(currentState).keyPressed(key);
    }
    public void keyReleased(int key) {
        gameStates.get(currentState).keyReleased(key);
    }
    // public boolean gameOver(){}

}
