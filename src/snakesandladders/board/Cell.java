package snakesandladders.board;

public class Cell {
    private final int number;
    private IBoardElement element;

    public Cell(int number) {
        this.number = number;
    }

    public int number() {
        return number;
    }

    public IBoardElement element() {
        return element;
    }

    public void setElement(IBoardElement element) {
        this.element = element;
    }
}
