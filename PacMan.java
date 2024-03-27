public class PacMan extends Ghost{
    private int score;
    private int moves;

    public PacMan() {
        super( " ᗧ"); // symbol is first initial
        score = 0;
        moves = 1;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int amt) {
        score += amt;
    }
    public int getMoves() {
        return moves;
    }

    public void move() {
        moves++;
    }
}
