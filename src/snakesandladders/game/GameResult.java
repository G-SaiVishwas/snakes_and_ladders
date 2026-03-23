package snakesandladders.game;

import snakesandladders.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameResult {
    private final List<Player> winnersInOrder;
    private final Player loser;

    public GameResult(List<Player> winnersInOrder, Player loser) {
        this.winnersInOrder = new ArrayList<>(winnersInOrder);
        this.loser = loser;
    }

    public List<Player> winnersInOrder() {
        return new ArrayList<>(winnersInOrder);
    }

    public Player loser() {
        return loser;
    }

    public List<Player> leaderboard() {
        List<Player> board = new ArrayList<>(winnersInOrder);
        board.add(loser);
        return board;
    }
}
