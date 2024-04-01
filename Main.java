/*
 * Main.java
 * Spelet Nm
 * Alexander Lindgren, alexander2000alle@gmail.com
 * alli1660
 * 2024-01-14
 */
import java.util.Scanner;

// Abstract class defining a player
abstract class Player {
    protected String name;

    // Constructor to initialize player's name
    public Player(String name) {
        this.name = name;
    }

    // Abstract method to make a move, to be implemented by concrete players
    public abstract int makeMove(int sticksLeft);

    // Method to get player's name
    public String getName() {
        return name;
    }
}

// Concrete class representing a human player
class HumanPlayer extends Player {
    private Scanner scanner;

    // Constructor to initialize human player's name and scanner for input
    public HumanPlayer(String name, Scanner scanner) {
        super(name);
        this.scanner = scanner;
    }

    // Method to make a move by taking input from the user
    @Override
    public int makeMove(int sticksLeft) {
        return scanner.nextInt();
    }
}

// Concrete class representing a computer player
class ComputerPlayer extends Player {
    // Constructor to initialize computer player's name
    public ComputerPlayer(String name) {
        super(name);
    }

    // Method to make a move by choosing sticks automatically
    @Override
    public int makeMove(int sticksLeft) {
        int takenSticks = sticksLeft / 2;
        System.out.println(name + " took " + takenSticks + " sticks.");
        return takenSticks;
    }
}

// Class representing the Nm game
class NmGame {
    private Player player1;
    private Player player2;
    private int sticks;

    // Constructor to initialize the game with players and sticks
    public NmGame(Player player1, Player player2, int sticks) {
        this.player1 = player1;
        this.player2 = player2;
        this.sticks = sticks;
    }

    // Method to play the game
    public void play() {
        Player currentPlayer = player1;
        Scanner scanner = new Scanner(System.in);
        
        while (sticks > 1) {
            int takenSticks;
        
            // Check if current player is human or computer
            if (currentPlayer instanceof HumanPlayer) {
                // Prompt human player to make a move
                System.out.print("Human, take sticks between 1-" + sticks / 2 + ": ");
                while (true) {
                    // Validate input from human player
                    String input = scanner.nextLine().trim();
                    if (input.matches("\\d+\\s*")) {
                        takenSticks = Integer.parseInt(input);
                        if (takenSticks >= 1 && takenSticks <= sticks / 2) {
                            break;
                        }
                    }
                    System.out.print("Invalid input! Please enter a valid number 1-" + sticks / 2 + ": ");
                }
            } else {
                // Let computer player make a move
                takenSticks = currentPlayer.makeMove(sticks);
            }
        
            // Update remaining sticks
            sticks -= takenSticks;
            System.out.println("There are " + sticks + " sticks left.");
        
            // Switch to the other player
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
        scanner.close();
        // Determine the winner
        Player winner = (currentPlayer == player1) ? player2 : player1;
        System.out.println("\n" + winner.name + " wins the game!");
    }
}

// Main class to run the Nm game
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("* * * * * * * * * *");
        System.out.println("*  Welcome to Nm! *");
        System.out.println("* * * * * * * * * *");
        int sticks;
        // Ask user to pick the number of sticks
        while (true) {
            System.out.print("Pick how many sticks you want (10 or more): ");
            
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+") && Integer.parseInt(input) >= 10) {
                sticks = Integer.parseInt(input);
                break;
            }
            else {
                System.out.println("Invalid input! must be 10 or more.");
            }
           
        }

        System.out.println("Game starts with " + sticks + " sticks.");

        // Create human and computer players
        Player player1 = new HumanPlayer("Human", scanner);
        Player player2 = new ComputerPlayer("Computer");
        
        // Start the game
        NmGame game = new NmGame(player1, player2, sticks);
        game.play();
        
        scanner.close();
    }
}

