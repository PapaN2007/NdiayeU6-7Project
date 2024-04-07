 import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
public class PacManGame {
    private Ghost[][] board;
    private PacMan player;
    private Jeffery jeffery;
    private Casper casper;
    private BeelzeBub beelzeBub;
    private Pikachu pikachu;
    private Scanner scanner;
    private int playerRow;
    private int playerColumn;
    private static int BOARD_SIZE = 21;
    private int totalCookies = (int) (Math.random() * 10) + 1;
    private int currentCookies;
    private int cookiesonBoard;
    private int jefferyRow;
    private int jefferyColumn;
    private int pikachuRow;
    private int pikachuColumn;
    private int casperRow;
    private int casperColumn;
    private int beelzebubRow;
    private int beelzebubColumn;
    private Timer stopwatch;

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
        jeffery = new Jeffery();
        beelzeBub = new BeelzeBub();
        casper = new Casper();
        pikachu = new Pikachu();
        stopwatch = new Timer();
        playerRow = 10;
        playerColumn = 10;
        jefferyRow = 20;
        jefferyColumn = 0;
        casperRow = 20;
        casperColumn = 20;
        pikachuColumn = 10;
        pikachuRow = 20;
        beelzebubRow = 0;
        beelzebubColumn = 20;
    }

    private void setupBoard() {
        board = new Ghost[21][21];
        board[10][10] = player;
        board[20][20] = new Casper();
        board[20][10] = new Pikachu();
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
        } if (player.getScore() >= 100){
            win();
        }
    }

    private void gameOver() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        audio.pause();
        stopwatch.stop();
        AudioPlayer end = new AudioPlayer("34 Pac Man World 2 End credits montage.wav");
        end.playSound();
        System.out.println("You collided with a ghost! Game over!");
        double elapsedTimeSeconds = stopwatch.getTimeElapsedSeconds();
        System.out.println("You Lasted " + elapsedTimeSeconds + " seconds");
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

    private void play() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        while (player.getScore() != 100) {
            printBoard();
            stopwatch.start();
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
            board[playerRow][playerColumn] = player;
            moveGhost(jeffery);
            moveGhost(casper);
            moveGhost(pikachu);
            moveGhost(beelzeBub);
        }
        printBoard();
    }
    private void win() {
        double elapsedTimeSeconds = stopwatch.getTimeElapsedSeconds();
        System.out.println("Congratulations! You have gained 100 points and won the game!");
        System.out.println("It took you " + elapsedTimeSeconds + " seconds");
        System.out.println("HIGHSCORE: Papa, Points: 999");        System.exit(0);
    }
    private void moveGhost(Ghost name){
        if (name.getSymbol().equals("🦖")){
            board[jefferyRow][jefferyColumn] = new Ghost("⬜");
            if (jefferyRow >= 17){
                jefferyRow = jeffery.moveGhostRow();
            }else {
                jefferyRow = jeffery.moveGhostRow();
            }
            if (jefferyColumn >= 17){
                jefferyColumn = jeffery.moveGhostColumn();
            }else {
                jefferyColumn = jeffery.moveGhostColumn();
            }
            if (board[jefferyRow][jefferyColumn] instanceof Cookies){
                board[jefferyRow--][jefferyColumn] = jeffery;
            }else {
                board[jefferyRow][jefferyColumn] = jeffery;
            }
        }else if (name.getSymbol().equals("👻")){
            board[casperRow][casperColumn] = new Ghost("⬜");
            if (casperRow >= 17){
                casperRow = casper.moveGhostRow();
            }else {
                casperRow = casper.moveGhostRow();
            }
            if (casperColumn >= 17){
                casperColumn = casper.moveGhostColumn();
            }else {
                casperColumn = casper.moveGhostColumn();
            }
            if (board[casperRow][casperColumn] instanceof Cookies){
                board[casperRow--][casperColumn] = casper;
            }else {
                board[casperRow][casperColumn] = casper;
            }
        }else if (name.getSymbol().equals("👹")){
            board[beelzebubRow][beelzebubColumn] = new Ghost("⬜");
            if (beelzebubRow >= 17){
                beelzebubRow = beelzeBub.moveGhostRow();
            }else {
                beelzebubRow = beelzeBub.moveGhostRow();
            }
            if (beelzebubColumn >= 17){
                beelzebubColumn = beelzeBub.moveGhostColumn();
            }else {
                beelzebubColumn = beelzeBub.moveGhostColumn();
            }
            if (board[beelzebubRow][beelzebubColumn] instanceof Cookies){
                board[beelzebubRow--][beelzebubColumn] = beelzeBub;
            }else {
                board[beelzebubRow][beelzebubColumn] = beelzeBub;
            }
        }else if (name.getSymbol().equals("🐝")){
            board[pikachuRow][pikachuColumn] = new Ghost("⬜");
            if (pikachuRow >= 17){
                pikachuRow = pikachu.moveGhostRow();
            }else {
                pikachuRow = pikachu.moveGhostRow();
            }
            if (pikachuColumn >= 17){
                pikachuColumn = pikachu.moveGhostColumn();
            }else {
                pikachuColumn = pikachu.moveGhostColumn();
            }
            if (board[pikachuRow][pikachuColumn] instanceof Cookies){
                board[pikachuRow--][pikachuColumn] = pikachu;
            }else {
                board[pikachuRow][pikachuColumn] = pikachu;
            }
        }
    }
}
