import javax.swing.*;
import java.awt.*;

public class Jeffery extends Ghost {
    private Image image; // Image of Jeffery (replace with your image)
    private int teleport;

    public Jeffery() {
        super("hi");
    }



    public int getTeleport(){
        return teleport;
    }

    public void randomizeTeleport(){
        teleport = (int) (Math.random() * 10);
    }
}
