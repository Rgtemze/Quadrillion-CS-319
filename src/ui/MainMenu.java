package ui;

import data.User;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import ui.Page;
import ui.Screen;
import ui.SelectLevel;

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
        Label welcomeText = new Label(String.format("Enjoy the Quadrillion %s!", User.getInstance().getNickName()));
        welcomeText.setLayoutX(Screen.getWidth() / 2 - 60);
        welcomeText.setLayoutY(50);
        welcomeText.setFont(new Font(30));

        Label hintText = new Label(String.format("You have %d hints", User.getInstance().getHint()));
        hintText.setLayoutX(Screen.getWidth() / 2 - welcomeText.getLayoutBounds().getWidth() / 2);
        hintText.setLayoutY(100);
        hintText.setAlignment(Pos.CENTER_LEFT);
        hintText.setFont(new Font(30));

        AnchorPane.setLeftAnchor(welcomeText, 0.0);
        AnchorPane.setRightAnchor(welcomeText, 0.0);
        AnchorPane.setLeftAnchor(hintText, 0.0);
        AnchorPane.setRightAnchor(hintText, 0.0);


        Button b1 = createButon("Play Casual", Screen.getWidth() / 2,160, BTN_WIDTH, BTN_HEIGHT, 30, event -> Screen.switchPage(new SelectLevel()));
        Button b2 = createButon("Compose Level",Screen.getWidth() / 2,250, BTN_WIDTH, BTN_HEIGHT, 30,event -> Screen.switchPage(new ComposeLevel()));
        Button b3 = createButon("Play Ranked",Screen.getWidth() / 2,340, BTN_WIDTH, BTN_HEIGHT, 30,event -> Screen.switchPage(new PlayRanked()));


        root.getChildren().addAll(b1, b2, b3, welcomeText, hintText);

    }
}
