package ui;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public abstract class Page {
    protected GridPane root;

    protected final int BTN_WIDTH = 200;
    protected final int BTN_HEIGHT = 70;

    public Page() {
        root = new GridPane();
    }

    public GridPane getRoot() {
        return root;
    }

    public abstract void prepareDesign();

    protected Button createButon(String text, int x, int y, int width, int height, int size, EventHandler handler) {
        Button btn = new Button(text);
        btn.setOnMouseClicked(handler);
        GridPane.setHalignment(btn, HPos.CENTER);
        btn.setMinSize(width, height);
        btn.setFont(new Font(size));
        btn.getStyleClass().add("record-sales");
        return btn;
    }

    protected Button addButton(String text, int x, int y, EventHandler handler) {
        Button btn = new Button(text);
        btn.setOnMouseClicked(handler);
        btn.setAlignment(Pos.CENTER);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.getStyleClass().add("record-sales");
        return btn;
    }
}