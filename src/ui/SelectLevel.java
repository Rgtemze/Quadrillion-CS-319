package ui;

import core.LevelManager;
import core.Level;
import data.Record;
import database.DatabaseConnection;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class SelectLevel extends Page {
    private int selectedLevel;

    public void openLevel() {

    }

    @Override
    public void prepareDesign() {

        double rectWidth = 120;
        double rectHeight = 120;
        int rectNo = (int)((Screen.getWidth() - 2 * rectWidth) / rectWidth);
        int levelCount = 30;
        //int levelCount = DatabaseConnection.getInstance().getLevelCount();
        int levelIndex = 0;
        for(int i = 0; i < 3 && levelIndex < levelCount; i++){
            for(int j = 0; j < rectNo && levelIndex < levelCount; j++) {

                Rectangle rect = new Rectangle();
                rect.setX(120 * (j + 1));
                rect.setY(120 * (i + 1));
                rect.setStroke(Paint.valueOf("black"));
                rect.setFill(Paint.valueOf("beige"));
                rect.setWidth(100);
                rect.setHeight(100);

                Label levelLabel = new Label(levelIndex + "");
                levelLabel.setLayoutX(rect.getX() + rect.getWidth() / 2);
                levelLabel.setLayoutY(rect.getY() + rect.getHeight() / 2);
                levelLabel.setFont(new Font(30));

                AnchorPane.setLeftAnchor(levelLabel, 0.0);
                AnchorPane.setRightAnchor(levelLabel, 0.0);
                levelLabel.setAlignment(Pos.CENTER);

                int finalI = levelIndex;
                rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PlayGame play = new PlayGame();
                        LevelManager manager = LevelManager.getInstance();
                        manager.createLevel(false, play.root);
                        manager.draw();
                        //manager.showLeaderboard();
                        Screen.switchPage(play);
                    }
                });
                root.getChildren().addAll(rect, levelLabel);
                levelIndex++;
            }
        }
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
    }


}
