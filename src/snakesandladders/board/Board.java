package snakesandladders.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {
    private final int size;
    private final int lastCell;
    private final Map<Integer, Cell> cells;
    private final List<IBoardElement> elements;

    public Board(int size, List<IBoardElement> elements) {
        if (size < 2) {
            throw new IllegalArgumentException("Board size must be at least 2");
        }

        this.size = size;
        this.lastCell = size * size;
        this.elements = new ArrayList<>(elements);
        this.cells = new HashMap<>();

        for (int i = 1; i <= lastCell; i++) {
            cells.put(i, new Cell(i));
        }

        for (IBoardElement element : elements) {
            validatePosition(element.start());
            validatePosition(element.end());
            cells.get(element.start()).setElement(element);
        }
    }

    public int size() {
        return size;
    }

    public int lastCell() {
        return lastCell;
    }

    public Optional<IBoardElement> elementAt(int position) {
        validatePosition(position);
        return Optional.ofNullable(cells.get(position).element());
    }

    public List<IBoardElement> elements() {
        return new ArrayList<>(elements);
    }

    private void validatePosition(int position) {
        if (position < 1 || position > lastCell) {
            throw new IllegalArgumentException("Invalid board position: " + position);
        }
    }
}
