public class Cookies extends Ghost{
    private int pointValue;

    public Cookies(String symbol, int pointValue) {
        super(symbol);
        this.pointValue = pointValue;
    }

    public int getPointValue() {
        return pointValue;
    }
}
