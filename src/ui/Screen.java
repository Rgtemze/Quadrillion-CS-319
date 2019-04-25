package ui;

import core.LevelManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Screen extends Application {

    private static Scene gameScene;
    private static int width = 1600;
    private static int height = 800;
    public static void switchPage(Page page) {
        gameScene.setRoot(page.getRoot());
    }



    public static void main( String[] args ) {
        launch(args);
    }
    protected static Button addButton(String text, int x, int y, EventHandler handler) {
        Button btn = new Button(text);
        btn.setOnMouseClicked(handler);
        btn.setAlignment(Pos.CENTER);
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.getStyleClass().add("record-sales");
        return btn;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {


        Login login = new Login();
        gameScene = new Scene(login.getRoot(), width, height);
        gameScene.getStylesheets().add("/css/button_style.css");


        primaryStage.setTitle("Quadrillion");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }

}
