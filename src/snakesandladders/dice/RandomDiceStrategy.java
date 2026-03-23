package snakesandladders.dice;

import java.util.Random;

public class RandomDiceStrategy implements DiceStrategy {
    private final Random random;

    public RandomDiceStrategy(Random random) {
        this.random = random;
    }

    @Override
    public int roll() {
        return random.nextInt(6) + 1;
    }
}
