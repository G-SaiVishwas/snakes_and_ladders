package snakesandladders.dice;

import java.util.ArrayList;
import java.util.List;

public class DeterministicDiceStrategy implements DiceStrategy {
    private final List<Integer> rolls;
    private int index;

    public DeterministicDiceStrategy(List<Integer> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException("Deterministic rolls cannot be empty");
        }

        for (int value : rolls) {
            if (value < 1 || value > 6) {
                throw new IllegalArgumentException("Dice values must be between 1 and 6");
            }
        }

        this.rolls = new ArrayList<>(rolls);
        this.index = 0;
    }

    @Override
    public int roll() {
        int value = rolls.get(index);
        index = (index + 1) % rolls.size();
        return value;
    }
}
