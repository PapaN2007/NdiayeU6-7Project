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

    public int moveRow(){
        return (int) (Math.random() * 20) +1;
    }
    public int moveColumn(){
        return (int) (Math.random() * 20) +1;
    }
}
