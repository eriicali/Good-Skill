package GameState;

public class ProcrastinationState extends FailureState {
    public ProcrastinationState(GameStateManager gsm) {
        super(gsm);
        setBackground("/procrastination.gif");
        string1 ="You fell into a hole of";
        string2 = "procrastination :(";
    }
}
