package ui;

import core.LevelManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Screen extends Application {

    private static Stage stage;
    private static Scene gameScene;
    //private static int width = 1600;
    //private static int height = 800;
    public static void switchPage(Page page) {
        gameScene.setRoot(page.getRoot());
    }



    public static void main( String[] args ) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        Login login = new Login();
        gameScene = new Scene(login.getRoot(), getWidth(), getHeight());
        gameScene.getStylesheets().add("/css/button_style.css");


        primaryStage.setTitle("Quadrillion");
        primaryStage.setScene(gameScene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static int getWidth(){
        return (int)stage.getWidth();
    }

    public static int getHeight(){
        return (int)stage.getHeight();
    }

}
