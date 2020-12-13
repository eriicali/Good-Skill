package GameState;

// this class is for implementing death by assignment attacks
public class AssignmentFailureState extends FailureState {
    // constructor
    public AssignmentFailureState(GameStateManager gsm) {
        // calls the super constructor of failurestate
        super(gsm);
        // sets the background to the appropriate gif
        setBackground("/deathbyassignments.gif");
        string1 ="You got crushed by";
        string2 ="assignments :(";
    }
}
