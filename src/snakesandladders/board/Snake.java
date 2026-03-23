package snakesandladders.board;

public class Snake implements IBoardElement {
    private final int head;
    private final int tail;

    public Snake(int head, int tail) {
        if (tail >= head) {
            throw new IllegalArgumentException("Snake tail must be smaller than head");
        }
        this.head = head;
        this.tail = tail;
    }

    @Override
    public int start() {
        return head;
    }

    @Override
    public int end() {
        return tail;
    }

    @Override
    public String eventType() {
        return "SNAKE";
    }
}
