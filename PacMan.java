public class PacMan extends Ghost{
    private int score;
    private int moves;

    public PacMan() {
        super( "\uD83E\uDD10");
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
