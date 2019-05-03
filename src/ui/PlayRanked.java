package ui;

import core.LevelManager;
import database.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class PlayRanked extends PlayGame {

    public PlayRanked() {
        super();
        int levelCount = DatabaseConnection.getInstance().getLevelCount();
        int randLevel = (int) (Math.random() * levelCount);
        pen.getChildren().clear();
        manager.createLevel(false, randLevel, getPen());
        manager.draw();
        prepareDesign();
    }

    @Override
    public void prepareDesign(){
        Button menu = addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        Button check = addButton("Check",0,60, event -> {uploadResults(true);});
        pen.getChildren().addAll(menu, check);
        addCounters(pen);
    }


    public void openRandomLevel() {

    }
}
