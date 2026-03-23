package snakesandladders.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoardBuilder {
    protected final int size;
    protected final BoardElementFactory factory;
    protected final List<IBoardElement> elements;
    protected final Set<Integer> occupiedStarts;

    public BoardBuilder(int size, BoardElementFactory factory) {
        if (size < 2) {
            throw new IllegalArgumentException("Board size must be at least 2");
        }
        this.size = size;
        this.factory = factory;
        this.elements = new ArrayList<>();
        this.occupiedStarts = new HashSet<>();
    }

    public BoardBuilder add_snake(int head, int tail) {
        addElement("snake", head, tail);
        return this;
    }

    public BoardBuilder add_ladder(int bottom, int top) {
        addElement("ladder", bottom, top);
        return this;
    }

    public BoardBuilder addSnake(int head, int tail) {
        return add_snake(head, tail);
    }

    public BoardBuilder addLadder(int bottom, int top) {
        return add_ladder(bottom, top);
    }

    public Board build() {
        return new Board(size, elements);
    }

    protected void addElement(String type, int start, int end) {
        int lastCell = size * size;

        if (start <= 1 || start >= lastCell) {
            throw new IllegalArgumentException("Element start must be between 2 and " + (lastCell - 1));
        }

        if (end < 1 || end > lastCell) {
            throw new IllegalArgumentException("Element end out of board range");
        }

        if (!occupiedStarts.add(start)) {
            throw new IllegalArgumentException("Another element already starts at cell " + start);
        }

        elements.add(factory.create(type, start, end));
    }
}
