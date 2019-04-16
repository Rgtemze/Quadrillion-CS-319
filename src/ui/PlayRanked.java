package ui;

import core.LevelManager;
import database.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class PlayRanked extends PlayGame {

    LevelManager manager = LevelManager.getInstance();
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
        Button check = addButton("Check",0,60, event -> {uploadResults();});
        pen.getChildren().addAll(menu, check);
        addCounters(pen);
    }

    public void uploadResults() {
        if(!manager.isGameWon()) {

            Alert notFin = new Alert(Alert.AlertType.CONFIRMATION);
            notFin.setTitle("Warning");
            notFin.setHeaderText("Level not complete");
            notFin.setContentText("Do you want to continue playing?");

            ((Button) notFin.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) notFin.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
            Optional<ButtonType> opt = notFin.showAndWait();

            if(opt.get() == ButtonType.OK){
                notFin.close();
            }
            else if(opt.get() == ButtonType.CANCEL){
                //TODO: show leaderboard
                Screen.switchPage(new MainMenu());
            }
        } else {
            Alert fin = new Alert(Alert.AlertType.INFORMATION);
            fin.setHeaderText("You have completed this level");
            fin.setTitle("Congratulations");
            Optional<ButtonType> opt = fin.showAndWait();
            if(opt.get() == ButtonType.OK){
                manager.setTimeElapsed((int) timeElapsed);
                manager.uploadResults();
                manager.showLeaderboard();
                Screen.switchPage(new MainMenu());
            }
        }
    }

    public void openRandomLevel() {

    }
}
