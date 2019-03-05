package sample;

public class Level {

    private Piece[] pieces;
    private Ground[] grounds;
    private int[][] combination;
    private Record[] leaderboard;
    private boolean isGameFinished;

    public Level( Ground[] grounds, Piece[] pieces ) {
        this.pieces = pieces;
        this.grounds = grounds;
    }

    public void combineGrounds() {


    }

    /*
    * The game finishes when all the pieces settled,
    * which is a win state.
    */
    public boolean isGameWon() {
        return isGameFinished;
    }

    /*
    * Draw all pieces and grounds of the game
    */
    public void drawLevel() {

    }

    void showHint( ) {

    }

    void seeLeaderBoard() {


    }
}
