package GameState;

public class ProcrastinationState extends FailureState {
    // constructor
    public ProcrastinationState(GameStateManager gsm) {
        // calls the super constructor
        super(gsm);
        setBackground("/procrastination.gif");
        string1 ="You fell into a hole of";
        string2 = "procrastination :(";
    }
}
