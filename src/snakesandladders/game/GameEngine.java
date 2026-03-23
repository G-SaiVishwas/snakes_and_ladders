package snakesandladders.game;

import snakesandladders.board.Board;
import snakesandladders.board.IBoardElement;
import snakesandladders.dice.Dice;
import snakesandladders.observer.GameEvent;
import snakesandladders.observer.GameEventType;
import snakesandladders.observer.GameObserver;
import snakesandladders.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameEngine {
    private final Board board;
    private final Dice dice;
    private final List<Player> allPlayers;
    private final List<GameObserver> observers;

    public GameEngine(Board board, Dice dice, List<Player> players) {
        if (board == null || dice == null || players == null || players.size() < 2) {
            throw new IllegalArgumentException("GameEngine requires board, dice, and at least two players");
        }
        this.board = board;
        this.dice = dice;
        this.allPlayers = new ArrayList<>(players);
        this.observers = new ArrayList<>();
    }

    public void registerObserver(GameObserver observer) {
        this.observers.add(observer);
    }

    public GameResult play() {
        List<Player> activePlayers = new ArrayList<>(allPlayers);
        List<Player> finishedPlayers = new ArrayList<>();
        int turnIndex = 0;
        int rank = 1;

        while (activePlayers.size() > 1) {
            Player player = activePlayers.get(turnIndex);
            int roll = dice.roll();
            notifyObservers(new GameEvent(GameEventType.DICE_ROLLED, player.name(), roll, null, null, null));

            int from = player.position();
            int tentative = from + roll;
            int to;

            if (tentative > board.lastCell()) {
                to = from;
            } else {
                to = tentative;
                Optional<IBoardElement> maybeElement = board.elementAt(to);
                if (maybeElement.isPresent()) {
                    IBoardElement element = maybeElement.get();
                    int transportedTo = element.end();

                    if ("SNAKE".equalsIgnoreCase(element.event_type())) {
                        notifyObservers(new GameEvent(GameEventType.SNAKE_BITTEN, player.name(), null, to, transportedTo, null));
                    } else if ("LADDER".equalsIgnoreCase(element.event_type())) {
                        notifyObservers(new GameEvent(GameEventType.LADDER_CLIMBED, player.name(), null, to, transportedTo, null));
                    }

                    to = transportedTo;
                }
            }

            player.setPosition(to);
            notifyObservers(new GameEvent(GameEventType.PLAYER_MOVED, player.name(), null, from, to, null));

            if (to == board.lastCell()) {
                player.setFinishRank(rank++);
                finishedPlayers.add(player);
                notifyObservers(new GameEvent(GameEventType.PLAYER_FINISHED, player.name(), null, null, null, String.valueOf(player.finishRank())));
                activePlayers.remove(turnIndex);
                if (turnIndex >= activePlayers.size()) {
                    turnIndex = 0;
                }
            } else {
                turnIndex = (turnIndex + 1) % activePlayers.size();
            }
        }

        Player loser = activePlayers.get(0);
        loser.markLoser();
        notifyObservers(new GameEvent(GameEventType.GAME_OVER, loser.name(), null, null, null,
                "Game over. Last active player (loser): " + loser.name()));

        return new GameResult(finishedPlayers, loser);
    }

    private void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}
