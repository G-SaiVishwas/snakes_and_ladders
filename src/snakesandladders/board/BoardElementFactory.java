package snakesandladders.board;

public class BoardElementFactory {
    public IBoardElement create(String type, int start, int end) {
        if (type == null) {
            throw new IllegalArgumentException("Element type cannot be null");
        }

        return switch (type.toLowerCase()) {
            case "snake" -> new Snake(start, end);
            case "ladder" -> new Ladder(start, end);
            default -> throw new IllegalArgumentException("Unsupported element type: " + type);
        };
    }
}
