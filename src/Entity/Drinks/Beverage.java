package Entity.Drinks;
public abstract class Beverage {
    protected int healthChange;
    protected int energyChange;
    protected int speedChange;

    public Beverage(int healthChange, int energyChange, int speedChange) {
        this.healthChange = healthChange;
        this.energyChange = energyChange;
        this.speedChange = speedChange;
    }

    public String toString() {
        return "Beverage{" +
                "healthChange=" + healthChange +
                ", energyChange=" + energyChange +
                ", speedChange=" + speedChange +
                '}';
    }
}
