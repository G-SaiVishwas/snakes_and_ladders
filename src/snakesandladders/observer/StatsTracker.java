package snakesandladders.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsTracker implements GameObserver {
    private final Map<String, Integer> totalRollsByPlayer = new HashMap<>();
    private final Map<String, Integer> totalMovesByPlayer = new HashMap<>();
    private final Map<String, Integer> snakeBitesByPlayer = new HashMap<>();
    private final Map<String, Integer> laddersByPlayer = new HashMap<>();
    private final List<String> finishOrder = new ArrayList<>();
    private String loser;

    @Override
    public void onEvent(GameEvent event) {
        String player = event.playerName();
        switch (event.type()) {
            case DICE_ROLLED -> increment(totalRollsByPlayer, player);
            case PLAYER_MOVED -> {
                if (event.from() != null && event.to() != null && !event.from().equals(event.to())) {
                    increment(totalMovesByPlayer, player);
                }
            }
            case SNAKE_BITTEN -> increment(snakeBitesByPlayer, player);
            case LADDER_CLIMBED -> increment(laddersByPlayer, player);
            case PLAYER_FINISHED -> finishOrder.add(player);
            case GAME_OVER -> loser = player;
        }
    }

    private void increment(Map<String, Integer> map, String player) {
        map.put(player, map.getOrDefault(player, 0) + 1);
    }

    public List<String> finishOrder() {
        return new ArrayList<>(finishOrder);
    }

    public String loser() {
        return loser;
    }

    public Map<String, Integer> totalRollsByPlayer() {
        return new HashMap<>(totalRollsByPlayer);
    }

    public Map<String, Integer> totalMovesByPlayer() {
        return new HashMap<>(totalMovesByPlayer);
    }

    public Map<String, Integer> snakeBitesByPlayer() {
        return new HashMap<>(snakeBitesByPlayer);
    }

    public Map<String, Integer> laddersByPlayer() {
        return new HashMap<>(laddersByPlayer);
    }
}
