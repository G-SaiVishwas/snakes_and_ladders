package snakesandladders.board;

public class Ladder implements IBoardElement {
    private final int bottom;
    private final int top;

    public Ladder(int bottom, int top) {
        if (top <= bottom) {
            throw new IllegalArgumentException("Ladder top must be greater than bottom");
        }
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    public int start() {
        return bottom;
    }

    @Override
    public int end() {
        return top;
    }

    @Override
    public String eventType() {
        return "LADDER";
    }
}
