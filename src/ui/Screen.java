package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Screen extends Application {

    private static Scene gameScene;
    private static int width = 1850;
    private static int height = 900;
    public static void switchPage(Page page) {
        gameScene.setRoot(page.getRoot());
    }

    public static void main( String[] args ) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Login login = new Login();
        gameScene = new Scene(login.getRoot(), width, height);

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
