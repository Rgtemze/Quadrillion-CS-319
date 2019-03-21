package ui;

import core.LevelManager;
import data.PurchaseInfo;
import data.User;
import interfaces.MoveObserver;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import pos.Entity;
import pos.PosService;

public class PlayGame extends Page implements MoveObserver {
    private boolean isGamePaused;
    private int numberOfMoves;
    private long timeElapsed;
    private Label moves, time;


    public PlayGame(){
        LevelManager.getInstance().setObserver(this);
    }
    @Override
    public void prepareDesign() {
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        time = new Label();
        time.setLayoutX(100);
        time.setLayoutY(0);
        time.setFont(new Font("Arial", 30));
        root.getChildren().add(time);
        moves = new Label();
        moves.setLayoutX(100);
        moves.setLayoutY(30);
        moves.setFont(new Font("Arial", 30));
        root.getChildren().add(moves);

        AnimationTimer timer = new AnimationTimer() {

            private long startTime ;

            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                timeElapsed = (now - startTime) / 1000;
                time.setText("Time Elapsed: " + timeElapsed + " sec");
            }
        };
        timer.start();

        moves.setText("Number of Moves: 0");

        addButton("Get Hint", 0,20, event ->{

            User user = User.getInstance();
            if(user.hasHint()){
                user.useHint();
                LevelManager.getInstance().showHint();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Enough Hints");
                alert.setHeaderText(null);
                alert.setContentText("You do not have enough hints.\nWould you like to buy some?");
                alert.showAndWait();

                PurchaseInfo pinfo = new PurchaseInfo("John", "Doe","5528790000000008",
                        "123","12.2030", "1.2");
                Entity entity = new Entity(pinfo);
                boolean res = entity.doPayment();
                System.out.println("Status: " + res);
            }


        });
    }


    private void goNextLevel() {

    }

    private void showHint() {

    }

    public void prepareGame() {

    }


    public void buyHint() {
        // Probably user will have hint points
        // so that they have limited number of changes
        // to see hints
    }

    @Override
    public void notifyMoveChanged(int numberOfMoves) {
        moves.setText("Number of Moves: " + numberOfMoves);
    }
}
