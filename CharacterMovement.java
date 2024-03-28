import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharacterMovement extends JFrame {
    private final int CELL_SIZE = 25; // Size of each cell
    private final int NUM_ROWS = 20; // Number of rows in the grid
    private final int NUM_COLS = 20; // Number of columns in the grid
    private final int SCREEN_WIDTH = NUM_COLS * CELL_SIZE; // Width of the screen
    private final int SCREEN_HEIGHT = NUM_ROWS * CELL_SIZE; // Height of the screen

    private int characterX = 0; // Initial x-coordinate of the character
    private int characterY = 0; // Initial y-coordinate of the character
    private final int CHARACTER_SIZE = CELL_SIZE; // Size of the character

    public CharacterMovement() {
        setTitle("Grid With Character");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        // Add key listener to the frame
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
            case KeyEvent.VK_UP:
                if (characterY > 0) // Prevent moving off the top of the grid
                    characterY -= CELL_SIZE; // Move character up
                break;
            case KeyEvent.VK_DOWN:
                if (characterY < SCREEN_HEIGHT - CELL_SIZE) // Prevent moving off the bottom of the grid
                    characterY += CELL_SIZE; // Move character down
                break;
            case KeyEvent.VK_LEFT:
                if (characterX > 0) // Prevent moving off the left side of the grid
                    characterX -= CELL_SIZE; // Move character left
                break;
            case KeyEvent.VK_RIGHT:
                if (characterX < SCREEN_WIDTH - CELL_SIZE) // Prevent moving off the right side of the grid
                    characterX += CELL_SIZE; // Move character right
                break;
        }
        repaint(); // Redraw the frame to update character position
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw grid lines
        g.setColor(Color.WHITE);
        // Draw horizontal lines
        for (int row = 0; row <= NUM_ROWS; row++) {
            int y = row * CELL_SIZE;
            g.drawLine(0, y, SCREEN_WIDTH, y);
        }
        // Draw vertical lines
        for (int col = 0; col <= NUM_COLS; col++) {
            int x = col * CELL_SIZE;
            g.drawLine(x, 0, x, SCREEN_HEIGHT);
        }

        // Draw character
        g.setColor(Color.RED);
        g.fillRect(characterX, characterY, CHARACTER_SIZE, CHARACTER_SIZE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CharacterMovement frame = new CharacterMovement();
            frame.setVisible(true);
        });
    }
}
