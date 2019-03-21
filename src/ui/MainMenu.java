package ui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
        addButton("Play Casual", 0,0,event -> Screen.switchPage(new SelectLevel()));
        addButton("Compose Level",0,40,event -> Screen.switchPage(new ComposeLevel()));
        addButton("Play Ranked",0,80,event -> Screen.switchPage(new PlayRanked()));
    }
}
