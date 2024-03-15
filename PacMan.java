public class PacMan extends Ghost{
    private int score;


    public PacMan() {
        super( " ᗧ"); // symbol is first initial
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int amt) {
        score += amt;
    }
}
