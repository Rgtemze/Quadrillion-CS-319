package ui;

import core.LevelManager;
import database.DatabaseConnection;

public class PlayRanked extends PlayGame {


    public PlayRanked() {
        super();

        int levelCount = DatabaseConnection.getInstance().getLevelCount();

        int randLevel = (int) (Math.random() * levelCount + 1);
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
        manager.showLeaderboard();
    }

    public void openRandomLevel() {

    }
}
