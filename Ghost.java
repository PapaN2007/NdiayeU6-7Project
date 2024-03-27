public class Ghost {
    private String symbol;
    private boolean eaten;

    public Ghost(String symbol) {
        this.symbol = symbol;
        eaten = false;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setEaten(){
        eaten = true;
    }
    public boolean isEaten(){
        return false;
    }
}
