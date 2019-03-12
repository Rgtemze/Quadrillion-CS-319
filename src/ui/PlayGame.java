package ui;

import core.Level;

public class PlayGame extends Page {
    private boolean isGamePused;
    private Level currentLevel;
    private int numberOfMoves;
    private int timeElapsed;
    @Override
    public void prepareDesign() {
    }

    public PlayGame() {
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

    public void setLevel(Level l) {
        this.currentLevel = l;
        currentLevel.drawLevel();
    }
}
