package ui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;

public abstract class Page {
    protected Group root;

    public Page(){
        root = new Group();
        prepareDesign();
    }

    public Group getRoot(){
        return root;
    }

    public abstract void prepareDesign();

    protected void addButton(String text, int x, int y, EventHandler handler){
        Button mainMenu = new Button(text);
        mainMenu.setOnMouseClicked(handler);
        mainMenu.setLayoutX(x);
        mainMenu.setLayoutY(y);
        root.getChildren().add(mainMenu);
    }
}
