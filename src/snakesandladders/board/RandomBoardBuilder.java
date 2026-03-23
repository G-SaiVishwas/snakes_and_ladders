package snakesandladders.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomBoardBuilder extends BoardBuilder {
    private final Random random;

    public RandomBoardBuilder(int size, BoardElementFactory factory, Random random) {
        super(size, factory);
        this.random = random;
    }

    public Board buildRandom(int snakeCount, int ladderCount) {
        int totalRequired = snakeCount + ladderCount;
        int maxStarts = (size * size) - 2;

        if (totalRequired > maxStarts) {
            throw new IllegalArgumentException("Board is too small for requested snakes and ladders");
        }

        placeSnakes(snakeCount);
        placeLadders(ladderCount);
        return build();
    }

    private void placeSnakes(int snakeCount) {
        int placed = 0;
        int attempts = 0;
        int maxAttempts = snakeCount * 5000;
        int lastCell = size * size;

        while (placed < snakeCount) {
            if (++attempts > maxAttempts) {
                throw new IllegalStateException("Could not place snakes without violating rules");
            }

            int head = randomInRange(2, lastCell - 1);
            if (occupiedStarts.contains(head)) {
                continue;
            }

            int tail = randomInRange(1, head - 1);
            if (violatesCycle(head, tail)) {
                continue;
            }

            add_snake(head, tail);
            placed++;
        }
    }

    private void placeLadders(int ladderCount) {
        int placed = 0;
        int attempts = 0;
        int maxAttempts = ladderCount * 5000;
        int lastCell = size * size;

        while (placed < ladderCount) {
            if (++attempts > maxAttempts) {
                throw new IllegalStateException("Could not place ladders without violating rules");
            }

            int bottom = randomInRange(2, lastCell - 1);
            if (occupiedStarts.contains(bottom)) {
                continue;
            }

            int top = randomInRange(bottom + 1, lastCell);
            if (violatesCycle(bottom, top)) {
                continue;
            }

            add_ladder(bottom, top);
            placed++;
        }
    }

    private int randomInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid random range: " + min + " to " + max);
        }
        return random.nextInt(max - min + 1) + min;
    }

    private boolean violatesCycle(int candidateStart, int candidateEnd) {
        Map<Integer, Integer> transitions = new HashMap<>();
        for (IBoardElement element : elements) {
            transitions.put(element.start(), element.end());
        }
        transitions.put(candidateStart, candidateEnd);
        return hasCycle(transitions);
    }

    private boolean hasCycle(Map<Integer, Integer> transitions) {
        for (int start : transitions.keySet()) {
            Set<Integer> path = new HashSet<>();
            int current = start;

            while (transitions.containsKey(current)) {
                if (!path.add(current)) {
                    return true;
                }
                current = transitions.get(current);
            }
        }
        return false;
    }
}
