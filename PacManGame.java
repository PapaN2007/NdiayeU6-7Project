import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
public class PacManGame {
    private Ghost[][] board;
    private PacMan player;
    private Scanner scanner;
    private int playerRow;
    private int playerColumn;
    private static int BOARD_SIZE = 21;
    private int totalCookies = (int) (Math.random() * 10) + 1;
    private int currentCookies;
    private int cookiesonBoard;
    AudioPlayer audio = new AudioPlayer("pacman_beginning.wav");

    public PacManGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
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
        audio.playSound();
        System.out.println("Welcome to Pacman Ghost buster edition");
        player = new PacMan();
        playerRow = 10;
        playerColumn = 10;
    }

    private void setupBoard() {
        board = new Ghost[21][21];
        board[10][10] = player;
        board[20][20] = new Casper();
        board[10][20] = new Pikachu();
        board[0][20] = new BeelzeBub();
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
        totalCookies = (int) (Math.random() * 10) + 1;;
        currentCookies = totalCookies;
        cookiesonBoard = totalCookies;
        while (currentCookies > 0) {
            int r = (int) (Math.random() * (BOARD_SIZE));
            int c = (int) (Math.random() * (BOARD_SIZE));
            if (!(board[r][c] instanceof Jeffery) && !(board[r][c] instanceof Casper) && !(board[r][c] instanceof Pikachu) && !(board[r][c] instanceof BeelzeBub) && !(board[r][c] instanceof PacMan) && !(board[r][c] instanceof Cookies)) {
                board[r][c] = new Cookies();
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
            int points = (int)  (Math.random() * 10) + 1;
            player.addPoints(points);
            System.out.println(points + " points were added to the player.");
            board[playerRow][playerColumn] = new Ghost("⬜");
            cookiesonBoard--;
        } if (cookiesonBoard == 0){
            spawnCookies();
        }
    }
    private void gameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        audio.pause();
        AudioPlayer end = new AudioPlayer("34 Pac Man World 2 End credits montage.wav");
        end.playSound();
        System.out.println("You collided with a ghost! Game over!");
        System.out.println("Your cookie score is: " + player.getScore());
        System.out.println("HIGHSCORE: Papa, Points: 999");
        Thread.sleep(10000);
        System.exit(0);
    }

    private void checkCollision() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] instanceof Jeffery || board[r][c] instanceof Pikachu || board[r][c] instanceof BeelzeBub || board[r][c] instanceof Casper) {
                    if (r == playerRow && c == playerColumn) {
                        gameOver();
                    }
                }
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    private void moveGhosts() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if ((board[r][c] instanceof Jeffery) || (board[r][c] instanceof Casper) || (board[r][c] instanceof Pikachu) || (board[r][c] instanceof BeelzeBub)) {
                    Ghost ghost = board[r][c];
                    int ghostRow = r;
                    int ghostCol = c;
                    int distRow = playerRow - ghostRow;
                    int distCol = playerColumn - ghostCol;
                    if (Math.abs(distRow) > Math.abs(distCol)) {
                        if (distRow > 0 && isValidMove(ghostRow + 1, ghostCol)) ghostRow++;
                        else if (isValidMove(ghostRow - 1, ghostCol)) ghostRow--;
                    } else {
                        if (distCol > 0 && isValidMove(ghostRow, ghostCol + 1)) ghostCol++;
                        else if (isValidMove(ghostRow, ghostCol - 1)) ghostCol--;
                    }
                    board[r][c] = new Ghost("⬜");
                    if (board[ghostRow][ghostCol] instanceof Cookies) {
                        board[ghostRow + 1][ghostCol - 1] = ghost;
                    } else {
                        board[ghostRow][ghostCol] = ghost;
                    }
                }
            }
        }
    }
    private void play() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        while (player.getScore() != 100) {
            printBoard();
            System.out.print("Enter a direction: ");
            String direction = scanner.nextLine();
            if (direction.equals("w")) {
                if (playerRow - 1 >= 0) {
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow--;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("a")) {
                if (playerColumn - 1 >= 0) {
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn--;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("d")) {
                if (playerColumn + 1 <= 20) {
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn++;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("s")) {
                if (playerRow + 1 <= 20) {
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow++;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else {
                System.out.println("Invalid!");
            }
            checkCollision();
            checkTreasure();
            moveGhosts();
            board[playerRow][playerColumn] = player;
        }
    }
}
