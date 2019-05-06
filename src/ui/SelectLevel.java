package ui;

import core.LevelManager;
import core.Level;
import data.Record;
import database.DatabaseConnection;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class SelectLevel extends Page {
    private int selectedLevel;
    private Group pen;

    public SelectLevel() {
        pen = new Group();
        root.add(pen,0,0);
        prepareDesign();
        scale.setX(SCALE_FACTORX);
        scale.setY(SCALE_FACTORY);
        pen.getTransforms().add(scale);
    }

    @Override
    public void prepareDesign() {

        double rectWidth = 130;
        double rectHeight = 130;
        double radius = 60;
        int rectNo = (int)((Screen.getWidth() - 2 * rectWidth) / rectWidth);
        int levelCount = DatabaseConnection.getInstance().getLevelCount();
        int levelIndex = 0;

        for(int i = 0; i < levelCount / rectNo + 1 && levelIndex < levelCount; i++){
            for(int j = 0; j < rectNo && levelIndex < levelCount; j++) {

                Circle rect = new Circle();
                rect.setCenterX(rectWidth * (j + 1));
                rect.setCenterY(1.2 * rectHeight * (i + 1));
                rect.setStroke(Paint.valueOf("black"));
                rect.setFill(Paint.valueOf("beige"));
                rect.setRadius(radius);

                Label levelLabel = new Label(levelIndex + "");
                levelLabel.setLayoutX(rect.getCenterX() - 10);
                levelLabel.setLayoutY(rect.getCenterY() - 5 + radius);
                levelLabel.setFont(new Font(30));

                int finalI = levelIndex;
                rect.setOnMouseClicked((event) -> {
                    PlayGame play = new PlayGame();
                    manager.createLevel(false, finalI, play.getPen());
                    manager.draw();
                    manager.showLeaderboard(user);
                    Screen.switchPage(play);
                });
                rect.setOnMouseEntered((event) -> {
                    Color lastColor = Color.color(Math.random(), Math.random(), Math.random());
                    rect.setFill(lastColor);
                });

                levelLabel.setOnMouseEntered(null);

                rect.setOnMouseExited((event) -> {
                    rect.setFill(Paint.valueOf("beige"));
                });
                pen.getChildren().addAll(rect, levelLabel);
                levelIndex++;
            }
        }
        Button menu = addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        pen.getChildren().add(menu);
    }


}
