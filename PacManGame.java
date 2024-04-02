import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.Scanner;
public class PacManGame {
    private Ghost[][] board;
    private PacMan player;
    private Scanner scanner;
    private int playerRow;
    private int playerColumn;


    public PacManGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        scanner = new Scanner(System.in);
        createPlayer();
        promptEnterKey();
        setupBoard();
        play();
    }

    public static void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        try {
            int read = System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createPlayer() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioPlayer audio = new AudioPlayer("pacman_beginning.wav");
        audio.playSound();
        System.out.println("Welcome to Pacman Ghost buster edition");
        player = new PacMan();
        playerRow = 7;
        playerColumn = 0;
    }
    private void setupBoard() {
        board = new Ghost[21][21];
        board[7][0] = player;
        //board[6][5] = new Jeffery();
        board[5][3] = new Casper();
        board[1][2] = new Pikachu();
        board[1][2] = new BeelzeBub();
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
            System.out.print("Enter a direction: ");
            String direction = scanner.nextLine();
            if (direction.equals("w")) {
                if (playerRow - 1 >= 0) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow--;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("a")) {
                if (playerColumn - 1 >= 0) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn--;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("d")) {
                if (playerColumn + 1 < 8) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerColumn++;
                } else {
                    System.out.println("Out of bounds!");
                }
            } else if (direction.equals("s")) {
                if (playerRow + 1 < 8) {
                    player.move();
                    board[playerRow][playerColumn] = new Ghost("⬜");
                    playerRow++;
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
}
