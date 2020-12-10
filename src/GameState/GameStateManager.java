package GameState;

import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> levelStates;
    private int currentState;
    public static final int MENUSTATE = 0;
    //do we really need this?
    public static final int LEVEL1STATE = 1;
    public static final int FAILURESTATE = 2;
    public static final int PASSSTATE =3;
    public static final int PROCRASTINATIONSTATE =4;

    public GameStateManager() {
        levelStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        levelStates.add(new MenuState(this));
        levelStates.add(new Level1State(this));
        levelStates.add(new FailureState(this));
        levelStates.add(new PassState(this));
        levelStates.add(new ProcrastinationState(this));
    }

    public void setState(int state) {
        currentState = state;
        levelStates.get(currentState).init();
    }
    public int getState(){
        return currentState;
    }
    public void update() {
        levelStates.get(currentState).update();
    }
    public void draw(java.awt.Graphics2D g) {
        levelStates.get(currentState).draw(g);
    }
    public void keyPressed(int key) {
        levelStates.get(currentState).keyPressed(key);
    }
    public void keyReleased(int key) {
        levelStates.get(currentState).keyReleased(key);
    }
    // public boolean gameOver(){}

}
