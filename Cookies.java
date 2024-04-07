public class Cookies extends Ghost{
    private int pointValue;

    public Cookies() {
        super("🍪");
    }

    @Override
    public int moveRow(){
        return (int) (Math.random() * 20) +1;
    }
    @Override
    public int moveColumn(){
        return (int) (Math.random() * 20) +1;
    }
}
