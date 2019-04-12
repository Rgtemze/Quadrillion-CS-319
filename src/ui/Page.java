package ui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public abstract class Page {
    protected Group root;

    protected final int BTN_WIDTH = 200;
    protected final int BTN_HEIGHT = 70;

    public Page() {
        root = new Group();
        prepareDesign();
    }

    public Group getRoot() {
        return root;
    }

    public abstract void prepareDesign();

    protected Button createButon(String text, int x, int y, int width, int height, int size, EventHandler handler) {
        Button btn = new Button(text);
        btn.setOnMouseClicked(handler);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setMinSize(width, height);
        btn.setFont(new Font(size));
        btn.getStyleClass().add("record-sales");
        return btn;
    }

    protected void addButton(String text, int x, int y, EventHandler handler) {
        Button btn = new Button(text);
        btn.setOnMouseClicked(handler);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.getStyleClass().add("record-sales");
        root.getChildren().add(btn);
    }
}