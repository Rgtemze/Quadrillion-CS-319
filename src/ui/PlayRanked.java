package ui;

import core.LevelManager;

public class PlayRanked extends PlayGame {


    public PlayRanked() {
        super();

        LevelManager manager = LevelManager.getInstance();
        manager.createLevel(false, 0, root);
        manager.draw();
    }

    @Override
    public void prepareDesign(){
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        addButton("Check",0,40, event -> {uploadResults();});
        addCounters();
    }

    public void uploadResults() {
        LevelManager manager = LevelManager.getInstance();
        manager.setTimeElapsed((int) timeElapsed);
        manager.uploadResults();
    }

    public void openRandomLevel() {

    }
}
