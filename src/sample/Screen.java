package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Screen extends Application {



    public void switchPage() {

    }

    public static void main( String[] args ) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group gameRoot = new Group();

        Scene gameScene = new Scene(gameRoot, 1600, 800);
        gameScene.setFill(Paint.valueOf("beige"));
        primaryStage.setTitle("Quadrillion");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }
}
