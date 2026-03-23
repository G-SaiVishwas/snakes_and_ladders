package snakesandladders.dice;

public class Dice {
    private DiceStrategy strategy;

    public Dice(DiceStrategy strategy) {
        this.strategy = strategy;
    }

    public int roll() {
        return strategy.roll();
    }

    public void setStrategy(DiceStrategy strategy) {
        this.strategy = strategy;
    }
}
