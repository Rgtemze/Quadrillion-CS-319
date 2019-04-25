package ui;

import core.LevelManager;
import data.User;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

public abstract class Page {
    protected GridPane root;
    protected static LevelManager manager = new LevelManager();
    protected static User user = new User();
    protected final int BTN_WIDTH = 200;
    protected final int BTN_HEIGHT = 70;
    private final int DEFAULT_WIDTH = 1920;
    private final int DEFAULT_HEIGHT = 1080;
    public double SCALE_FACTORX = (double)Screen.getWidth()/DEFAULT_WIDTH;
    public double SCALE_FACTORY = (double)Screen.getHeight()/DEFAULT_HEIGHT;
    public Scale scale = new Scale();


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