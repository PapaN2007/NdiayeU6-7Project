import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class PacManGame {
    private Ghost[][] board;
    private PacMan player;
    private Scanner scanner;
    private int playerRow;
    private int playerColumn;
    private static int BOARD_SIZE = 21;
    private int totalCookies = 10;
    private int currentCookies;
    private Timer respawnTimer;
    AudioPlayer audio = new AudioPlayer("pacman_beginning.wav");



    public PacManGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        scanner = new Scanner(System.in);
        createPlayer();
        promptEnterKey();
        setupBoard();
        startRespawnTimer();
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
        audio.playSound();
        System.out.println("Welcome to Pacman Ghost buster edition");
        player = new PacMan();
        playerRow = 10;
        playerColumn = 10;
    }

    private void setupBoard() {
        board = new Ghost[21][21];
        board[10][10] = player;
        board[0][20] = new Casper();
        board[20][20] = new Pikachu();
        board[0][0] = new BeelzeBub();
        board[20][0] = new Jeffery();
        currentCookies = totalCookies;
        spawnCookies();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == null) {
                    board[r][c] = new Ghost("⬜");
                }
            }
        }
    }

    private void spawnCookies() {
        Random random = new Random();
        while (currentCookies > 0) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (!(board[row][col] instanceof Ghost) && !(board[row][col] instanceof PacMan) && !(board[row][col] instanceof Cookies)) {
                board[row][col] = new Cookies();
                currentCookies--;
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
        System.out.println("Points: " + player.getScore());
    }

    private void checkTreasure() {
        if (board[playerRow][playerColumn] instanceof Cookies) {
            int points = ((Cookies) board[playerRow][playerColumn]).getPointValue();
            player.addPoints(points);
            System.out.println(points + " points were added to the player.");
            board[playerRow][playerColumn] = new Ghost("⬜");
            currentCookies--;
        }
    }

    private void startRespawnTimer() {
        respawnTimer = new Timer();
        respawnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                respawnCookies();
            }
        }, 0, 5000); // Respawn cookies every 5 seconds (adjust as needed)
    }


    private void respawnCookies() {
        Random random = new Random();
        int numCookies = random.nextInt(5) + 1; // Generate a random number of cookies to respawn (between 1 and 5)

        // Respawn the specified number of cookies at random locations on the board
        for (int i = 0; i < numCookies; i++) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);

            // Check if the chosen location is empty (not already occupied by a ghost or the player)
            if (!(board[row][col] instanceof Ghost) && !(board[row][col] instanceof PacMan) && !(board[row][col] instanceof Cookies)) {
                board[row][col] = new Cookies();
            }
        }
    }


    private void checkCollision() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] instanceof Jeffery || board[r][c] instanceof Pikachu || board[r][c] instanceof BeelzeBub || board[r][c] instanceof Casper) {
                    if (r == playerRow && c == playerColumn) {
                        audio.pause();
                        AudioPlayer end = new AudioPlayer("34 Pac Man World 2 End credits montage.wav");
                        end.playSound();
                        System.out.println("You collided with a ghost! Game over!");
                        System.out.println("Your cookie score is: " + player.getScore());
                        Thread.sleep(10000);
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void play() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        while (!(board[0][7] instanceof PacMan)) {
            printBoard();
            System.out.print("Enter a direction: ");
            String direction = scanner.nextLine();
            if (direction.equals("w")) {
                if (playerRow - 1 >= 0) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow--;
                    player.move(); // Move the player after updating the position
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("a")) {
                if (playerColumn - 1 >= 0) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn--;
                    player.move(); // Move the player after updating the position
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("d")) {
                if (playerColumn + 1 <= 21) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn++;
                    player.move(); // Move the player after updating the position
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("s")) {
                if (playerRow + 1 <= 21) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow++;
                    player.move(); // Move the player after updating the position
                } else {
                    System.out.println("Out of bounds!");
                }
            } else {
                System.out.println("Invalid!");
            }
            checkCollision();
            checkTreasure();
            respawnCookies();
            board[playerRow][playerColumn] = player;
        }
        printBoard();
    }


}
