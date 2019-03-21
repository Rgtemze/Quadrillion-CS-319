package ui;

import core.LevelManager;
import core.Level;
import data.GroundData;

public class ComposeLevel extends Page {
    private Level currentLevel;
    private LevelManager fetcher;
    public ComposeLevel(){
        fetcher = LevelManager.getInstance();
        fetcher.createLevel(true, root);
        fetcher.draw();
    }


    @Override
    public void prepareDesign() {
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        addButton("Check",0,30, event -> {fetcher.uploadLevel();});

    }
}
