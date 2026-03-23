package snakesandladders.observer;

public class ConsoleLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        switch (event.type()) {
            case DICE_ROLLED -> System.out.println(event.playerName() + " rolled " + event.roll());
            case PLAYER_MOVED -> System.out.println(event.playerName() + " moved from " + event.from() + " to " + event.to());
            case SNAKE_BITTEN -> System.out.println(event.playerName() + " got bitten by a snake: " + event.from() + " -> " + event.to());
            case LADDER_CLIMBED -> System.out.println(event.playerName() + " climbed a ladder: " + event.from() + " -> " + event.to());
            case PLAYER_FINISHED -> System.out.println(event.playerName() + " finished at rank " + event.message());
            case GAME_OVER -> System.out.println(event.message());
        }
    }
}
