package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Screen extends Application {

    private static Scene gameScene;

    public static void switchPage(Page page) {
        gameScene.setRoot(page.getRoot());
    }

    public static void main( String[] args ) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //PosService pos = new PosService();
        //pos.pay();

        MainMenu menu = new MainMenu();
        gameScene = new Scene(menu.getRoot(), 1600, 800);

        primaryStage.setTitle("Quadrillion");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static double getSceneWidth(){
        return gameScene.getWidth();
    }

    public static double getSceneHeight(){
        return gameScene.getHeight();
    }

}
