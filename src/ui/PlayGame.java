package ui;

import core.Level;

public class PlayGame extends Page {
    private boolean isGamePaused;
    private int numberOfMoves;
    private int timeElapsed;
    @Override
    public void prepareDesign() {
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
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
}
