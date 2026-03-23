package snakesandladders.player;

public class Player {
    private final String name;
    private int position;
    private Integer finishRank;
    private boolean loser;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        this.name = name.trim();
        this.position = 0;
        this.finishRank = null;
        this.loser = false;
    }

    public String name() {
        return name;
    }

    public int position() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFinished() {
        return finishRank != null;
    }

    public Integer finishRank() {
        return finishRank;
    }

    public void setFinishRank(int rank) {
        this.finishRank = rank;
    }

    public boolean isLoser() {
        return loser;
    }

    public void markLoser() {
        this.loser = true;
    }
}
