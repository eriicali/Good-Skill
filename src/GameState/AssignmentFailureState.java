package GameState;

public class AssignmentFailureState extends FailureState {
    public AssignmentFailureState(GameStateManager gsm) {
        super(gsm);
        setBackground("/deathbyassignments.gif");
        string1 ="You got crushed by";
        string2 ="assignments :(";
    }
}
