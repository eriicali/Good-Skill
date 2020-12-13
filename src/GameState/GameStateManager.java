package GameState;

import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> levelStates;
    private int currentState;
    // constants for each game state
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int FAILURESTATE = 2;
    public static final int PASSSTATE =3;
    public static final int PROCRASTINATIONSTATE =4;
    // constructor
    public GameStateManager() {
        levelStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        // adds the 5 states the the levelStates arrayList
        levelStates.add(new MenuState(this));
        levelStates.add(new Level1State(this));
        levelStates.add(new AssignmentFailureState(this));
        levelStates.add(new PassState(this));
        levelStates.add(new ProcrastinationState(this));
    }
    // this method sets the state
    public void setState(int state) {
        currentState = state;
        levelStates.get(currentState).init();
    }
    // this method gets the current state
    public int getState(){
        return currentState;
    }
    // this method updates the level states
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
