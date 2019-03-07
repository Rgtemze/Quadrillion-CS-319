package sample;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MainMenu extends Page {

    public void startGame() {

    }

    public void exit() {

    }


    public void ComposeLevel() {

    }


    public void startRankedGame() {

    }

    @Override
    public void prepareDesign() {

        Button playCasual = new Button("Play Casual");
        playCasual.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Screen.switchPage(new SelectLevel());
            }
        });

        playCasual.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().add(playCasual);
    }
}
