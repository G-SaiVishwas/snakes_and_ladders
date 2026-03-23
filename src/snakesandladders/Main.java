package snakesandladders;

import snakesandladders.board.Board;
import snakesandladders.board.BoardElementFactory;
import snakesandladders.board.RandomBoardBuilder;
import snakesandladders.dice.Dice;
import snakesandladders.dice.RandomDiceStrategy;
import snakesandladders.game.GameEngine;
import snakesandladders.game.GameResult;
import snakesandladders.observer.ConsoleLogger;
import snakesandladders.observer.StatsTracker;
import snakesandladders.player.Player;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = readBoardSize(scanner);
        List<Player> players = readPlayers(scanner);

        BoardElementFactory factory = new BoardElementFactory();
        RandomBoardBuilder randomBuilder = new RandomBoardBuilder(n, factory, new Random());
        Board board = randomBuilder.buildRandom(n, n);

        Dice dice = new Dice(new RandomDiceStrategy(new Random()));

        GameEngine engine = new GameEngine(board, dice, players);
        ConsoleLogger logger = new ConsoleLogger();
        StatsTracker stats = new StatsTracker();

        engine.registerObserver(logger);
        engine.registerObserver(stats);

        System.out.println();
        System.out.println("Starting game with board size " + n + "x" + n + " and " + players.size() + " players.");
        System.out.println();

        GameResult result = engine.play();
        printLeaderboard(result);
        printStats(stats, result);
    }

    private static int readBoardSize(Scanner scanner) {
        while (true) {
            System.out.print("Enter board size n (recommended >= 3): ");
            String input = scanner.nextLine().trim();

            try {
                int n = Integer.parseInt(input);
                if (n < 3) {
                    System.out.println("Please enter n >= 3.");
                    continue;
                }
                return n;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static List<Player> readPlayers(Scanner scanner) {
        while (true) {
            System.out.print("Enter player names separated by commas (at least 2): ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Names cannot be empty.");
                continue;
            }

            String[] rawNames = line.split(",");
            Set<String> uniqueNames = new LinkedHashSet<>();
            for (String name : rawNames) {
                String cleaned = name.trim();
                if (!cleaned.isEmpty()) {
                    uniqueNames.add(cleaned);
                }
            }

            if (uniqueNames.size() < 2) {
                System.out.println("Please enter at least two distinct player names.");
                continue;
            }

            List<Player> players = new ArrayList<>();
            for (String name : uniqueNames) {
                players.add(new Player(name));
            }
            return players;
        }
    }

    private static void printLeaderboard(GameResult result) {
        System.out.println();
        System.out.println("Final Leaderboard");
        System.out.println("-----------------");

        List<Player> winners = result.winnersInOrder();
        for (int i = 0; i < winners.size(); i++) {
            Player player = winners.get(i);
            System.out.println((i + 1) + ". " + player.name() + " (finished at turn rank " + player.finishRank() + ")");
        }

        Player loser = result.loser();
        System.out.println((winners.size() + 1) + ". " + loser.name() + " (last active player, loser)");
    }

    private static void printStats(StatsTracker stats, GameResult result) {
        System.out.println();
        System.out.println("Stats");
        System.out.println("-----");

        for (Player player : result.leaderboard()) {
            String name = player.name();
            int rolls = stats.totalRollsByPlayer().getOrDefault(name, 0);
            int moves = stats.totalMovesByPlayer().getOrDefault(name, 0);
            int bites = stats.snakeBitesByPlayer().getOrDefault(name, 0);
            int ladders = stats.laddersByPlayer().getOrDefault(name, 0);

            System.out.println(name + ": rolls=" + rolls + ", moves=" + moves + ", snake_bites=" + bites + ", ladders=" + ladders);
        }
    }
}
