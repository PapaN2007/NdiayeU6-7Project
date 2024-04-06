import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.plaf.metal.MetalIconFactory;
import java.io.IOException;
import java.util.Scanner;

public class PacManGame {
    private Ghost[][] board;
    private PacMan player;
    private Scanner scanner;
    private int playerRow;
    private int playerColumn;
    private static int BOARD_SIZE = 21;

    public PacManGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        scanner = new Scanner(System.in);
        createPlayer();
        promptEnterKey();
        setupBoard();
        play();
    }

    public static void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPlayer() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println("Welcome to Pacman Ghost buster edition");
        player = new PacMan();
        Ghost pikachu = new Pikachu();
        Ghost casper = new Casper();
        Ghost beelzebub = new BeelzeBub();
        Ghost jeffery = new Jeffery();
        playerRow = 20;
        playerColumn = 0;
    }

    private void setupBoard() {
        board = new Ghost[21][21];
        board[20][0] = player;
        board[0][20] = new Casper();
        board[20][20] = new Pikachu();
        board[0][0] = new BeelzeBub();
        board[10][10] = new Jeffery();
        board[20][15] = new Cookies();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == null) {
                    board[r][c] = new Ghost("⬜");
                }
            }
        }
    }

    private void printBoard() {
        for (Ghost[] row : board) {
            for (Ghost element : row) {
                System.out.print(element.getSymbol());
            }
            System.out.println();
        }
    }

    private void checkTreasure() {
        if (board[playerRow][playerColumn] instanceof Cookies) {
            int points = ((Cookies) board[playerRow][playerColumn]).getPointValue();
            player.addPoints(points);
            System.out.println(points + " points were added to the player.");
        }
    }

    private void play() {
        while (!(board[0][7] instanceof PacMan)) {
            printBoard();
            moveGhosts(); // Move ghosts before player's turn
            System.out.print("Enter a direction: ");
            String direction = scanner.nextLine();
            if (direction.equals("w")) {
                if (playerRow - 1 >= 0) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow--;
                    moveGhosts();
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("a")) {
                if (playerColumn - 1 >= 0) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn--;
                    moveGhosts();
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("d")) {
                if (playerColumn + 1 <= 21) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn++;
                    moveGhosts();
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("s")) {
                if (playerRow + 1 <= 21) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow++;
                    moveGhosts();
                } else {
                    System.out.println("Out of bounds!");
                }
            } else {
                System.out.println("Invalid!");
            }
            checkTreasure();
            board[playerRow][playerColumn] = player;
        }
        printBoard();
        System.out.println("You reached the goal!");
        System.out.println("Your score is: " + player.getScore());
        System.out.println("Your total moves is: " + player.getMoves());
    }
    private void moveGhosts() {
        // Create a temporary board to store the new positions of the ghosts
        Random random = new Random();

        // Iterate over the current board to move each ghost
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if ((board[r][c] instanceof Ghost) && !(board[r][c] instanceof PacMan) && !(board[r][c] instanceof Cookies)){
                    Ghost ghost = (Ghost) board[r][c];
                    int ghostRow = r;
                    int ghostCol = c;

                    // Determine the direction in which the ghost should move
                    int moveDirection = random.nextInt(4); // 0: up, 1: down, 2: left, 3: right

                    // Random chance to move towards player
                    if (random.nextDouble() < 0.3) { // Adjust this probability as desired
                        int distRow = playerRow - ghostRow;
                        int distCol = playerColumn - ghostCol;

                        // Prioritize movement towards the player
                        if (Math.abs(distRow) > Math.abs(distCol)) {
                            if (distRow > 0) moveDirection = 1; // Down
                            else moveDirection = 0; // Up
                        } else {
                            if (distCol > 0) moveDirection = 3; // Right
                            else moveDirection = 2; // Left
                        }
                    }

                    // Update ghost position based on the chosen direction
                    switch (moveDirection) {
                        case 0: // Up
                            ghostRow = (ghostRow - 1 + BOARD_SIZE) % BOARD_SIZE;
                            break;
                        case 1: // Down
                            ghostRow = (ghostRow + 1) % BOARD_SIZE;
                            break;
                        case 2: // Left
                            ghostCol = (ghostCol - 1 + BOARD_SIZE) % BOARD_SIZE;
                            break;
                        case 3: // Right
                            ghostCol = (ghostCol + 1) % BOARD_SIZE;
                            break;
                    }

                    // Place the ghost in the new position on the temporary board
                    board[ghostRow][ghostCol] = ghost;
                }
            }
        }
    }

}
