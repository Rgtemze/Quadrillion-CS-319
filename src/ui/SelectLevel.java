package ui;

import core.LevelManager;
import core.Level;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SelectLevel extends Page {
    private int selectedLevel;

    public void openLevel() {

    }

    @Override
    public void prepareDesign() {

        double rectWidth = 120;
        double rectHeight = 120;
        int rectNo = (int)((Screen.getSceneWidth()-2*rectWidth)/rectWidth);
        System.out.println(rectNo);
        for(int i = 0; i < rectNo; i++){
            for(int j = 0; j < 3; j++) {
                Rectangle rect = new Rectangle();
                rect.setX(120 * (i + 1));
                rect.setY(120 * (j + 1));
                rect.setStroke(Paint.valueOf("black"));
                rect.setWidth(100);
                rect.setHeight(100);
                int finalI = i;
                rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Selam");

                        PlayGame play = new PlayGame();
                        LevelManager manager = LevelManager.getInstance();
                        manager.createLevel(false, finalI, play.root);
                        manager.draw();
                        manager.showLeaderboard();
                        Screen.switchPage(play);
                    }
                });
                root.getChildren().add(rect);
            }
        }
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});


    }
}
