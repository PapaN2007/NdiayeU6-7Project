import javax.swing.*;
import java.awt.*;

public class Jeffery extends Character {
    private Image image; // Image of Jeffery (replace with your image)
    private int teleport;

    public Jeffery(int x, int y) {
        super(x, y, 50); // Initialize Jeffery with x, y coordinates and a size of 50
        // Load Jeffery's image (replace "Jeffery.png" with your image file path)
        image = new ImageIcon("Jeffery.png").getImage();
        // Initialize teleport to a random number between 0 and 9
        teleport = (int) (Math.random() * 10);
    }

    @Override
    public void move(int keyCode) {
        // Implement Jeffery's movement logic
        // Example:
        // Update x and y based on the keyCode (e.g., arrow keys)
    }

    public void draw(Graphics g) {
        // Draw Jeffery on the screen
        g.drawImage(image, getX(), getY(), getSize(), getSize(), null);
    }

    public int getTeleport(){
        return teleport;
    }

    public void randomizeTeleport(){
        teleport = (int) (Math.random() * 10);
    }
}
