import java.awt.*;

public abstract class Character {
    private int x;
    private int y;
    private int size;

    public Character(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public abstract void move(int keyCode);

    public void draw(Graphics g) {
        // Default implementation for drawing a character
    }

    // Getters and setters for x, y, and size
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
