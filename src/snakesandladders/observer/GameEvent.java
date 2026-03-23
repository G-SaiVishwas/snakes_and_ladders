package snakesandladders.observer;

public class GameEvent {
    private final GameEventType type;
    private final String playerName;
    private final Integer roll;
    private final Integer from;
    private final Integer to;
    private final String message;

    public GameEvent(GameEventType type, String playerName, Integer roll, Integer from, Integer to, String message) {
        this.type = type;
        this.playerName = playerName;
        this.roll = roll;
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public GameEventType type() {
        return type;
    }

    public String playerName() {
        return playerName;
    }

    public Integer roll() {
        return roll;
    }

    public Integer from() {
        return from;
    }

    public Integer to() {
        return to;
    }

    public String message() {
        return message;
    }
}
