package ui;

import data.User;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import ui.Page;
import ui.Screen;
import ui.SelectLevel;

public class MainMenu extends Page {
    public MainMenu() {
        prepareDesign();
    }

    @Override
    public void prepareDesign() {
        Label welcomeText = new Label(String.format("Enjoy the Quadrillion %s!", User.getInstance().getNickName()));
        welcomeText.setStyle("-fx-font-family: \"Dokdo\";");
        welcomeText.setFont(new Font(50));

        Label hintText = new Label(String.format("You have %d hints", User.getInstance().getHint()));
        hintText.setStyle("-fx-font-family: \"Dokdo\";");
        hintText.setAlignment(Pos.CENTER_LEFT);
        hintText.setFont(new Font(50));

        GridPane.setHalignment(hintText, HPos.CENTER);

        Button b1 = createButon("Play Casual", Screen.getWidth() / 2,160, BTN_WIDTH, BTN_HEIGHT, 30, event -> Screen.switchPage(new SelectLevel()));
        Button b2 = createButon("Compose Level",Screen.getWidth() / 2,250, BTN_WIDTH, BTN_HEIGHT, 30,event -> Screen.switchPage(new ComposeLevel()));
        Button b3 = createButon("Play Ranked",Screen.getWidth() / 2,340, BTN_WIDTH, BTN_HEIGHT, 30,event -> Screen.switchPage(new PlayRanked()));


        //root.getChildren().addAll(b1, b2, b3, welcomeText, hintText);
        root.setVgap(30);
        root.setAlignment(Pos.CENTER);
        root.add(welcomeText,1,0);
        root.add(hintText,1,1);
        root.add(b1,1,2);
        root.add(b2,1,3);
        root.add(b3,1,4);
    }
}
