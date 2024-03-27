public class Jeffery extends Ghost{
    // bro can teleport
    private int teleport;

    public Jeffery(){
        super("ᗣ");
        teleport = (int) (Math.random() * 10) - 1;
    }
    public int getTeleport(){
        return teleport;
    }
    public void randomizeTeleport(){
        teleport = (int) (Math.random() * 10) - 1;
    }
}
