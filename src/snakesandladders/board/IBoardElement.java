package snakesandladders.board;

public interface IBoardElement {
    int start();

    int end();

    String eventType();

    default String event_type() {
        return eventType();
    }
}
