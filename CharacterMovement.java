import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class CharacterMovement extends JFrame {
    private final int CELL_SIZE = 50; // Size of each cell
    private final int NUM_ROWS = 12; // Number of rows in the grid
    private final int NUM_COLS = 12; // Number of columns in the grid
    private final int SCREEN_WIDTH = NUM_COLS * CELL_SIZE; // Width of the screen
    private final int SCREEN_HEIGHT = NUM_ROWS * CELL_SIZE; // Height of the screen
    private final int CHARACTER_SIZE = CELL_SIZE;
    private int characterX = 1;
    private int characterY = 1;
    // Initial y-coordinate of the character
    // Size of the character
    private Image characterImage;
    private Image offScreenImage;
    private Jeffery jeffery;


    public CharacterMovement() {
        setTitle("PacMan Game");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        jeffery = new Jeffery(5, 5);
        characterImage = new ImageIcon("PacMan.png").getImage();
        if (characterImage == null) {
            System.out.println("Error: Failed to load character image");
        }        // Add key listener to the frame
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moveCharacter(e.getKeyCode());
            }
        });

        setFocusable(true);
    }

    private void moveCharacter(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                characterY = (characterY - CELL_SIZE + SCREEN_HEIGHT) % SCREEN_HEIGHT;
                break;
            case KeyEvent.VK_S:
                characterY = (characterY + CELL_SIZE) % SCREEN_HEIGHT;
                break;
            case KeyEvent.VK_A:
                characterX = (characterX - CELL_SIZE + SCREEN_WIDTH) % SCREEN_WIDTH;
                break;
            case KeyEvent.VK_D:
                characterX = (characterX + CELL_SIZE) % SCREEN_WIDTH;
                break;
        }
        repaint(); // Redraw the frame to update character position
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(getWidth(), getHeight());
        }
        Graphics offScreenGraphics = offScreenImage.getGraphics();
        super.paint(offScreenGraphics);

        // Draw grid lines
        offScreenGraphics.setColor(Color.WHITE);
        for (int x = 0; x <= SCREEN_WIDTH; x += CELL_SIZE) {
            offScreenGraphics.drawLine(x, 0, x, SCREEN_HEIGHT);
        }
        for (int y = 0; y <= SCREEN_HEIGHT; y += CELL_SIZE) {
            offScreenGraphics.drawLine(0, y, SCREEN_WIDTH, y);
        }

        // Draw character image
        offScreenGraphics.drawImage(characterImage, characterX, characterY, CHARACTER_SIZE, CHARACTER_SIZE, null);

        // Draw off-screen image onto the screen
        g.drawImage(offScreenImage, 0, 0, this);
        jeffery.draw(g);

    }


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        SwingUtilities.invokeLater(() -> {
            CharacterMovement frame = new CharacterMovement();
            frame.setVisible(true);
        });
        AudioPlayer audio = new AudioPlayer("pacman_beginning.wav");
        audio.playSound();
    }
}
