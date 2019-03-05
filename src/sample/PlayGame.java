package sample;

public class PlayGame {
    private boolean isGamePused;
    private Level currentLevel;
    private int numberOfMoves;
    private int timeElapsed;

    public PlayGame( Level currentLevel ) {
        this.currentLevel = currentLevel;
    }

    private void goNextLevel() {

    }

    private void showHint() {

    }

    public void prepareGame() {

    }

    public void buyHint() {
        // Probably user will have hint points
        // so that they have limited number of changes
        // to see hints
    }

}
