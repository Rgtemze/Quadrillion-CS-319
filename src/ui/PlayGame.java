package ui;

import com.iyzipay.model.Status;
import core.LevelManager;
import data.PurchaseInfo;
import data.User;
import database.DatabaseConnection;
import interfaces.MoveObserver;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pos.Entity;
import pos.PosService;

import java.util.Optional;

public class PlayGame extends Page implements MoveObserver {
    private boolean isGamePaused;
    protected int numberOfMoves;
    protected long timeElapsed;
    protected Label moves, time, hints;
    LevelManager manager;
    boolean stopCountingTime;

    public PlayGame(){
        manager = LevelManager.getInstance();
        manager.setObserver(this);
        stopCountingTime = false;
    }

    protected void showSubmissionDialog(){
        if(!manager.isGameWon()){
            Alert notFin = new Alert(Alert.AlertType.CONFIRMATION);
            notFin.setTitle("Warning");
            notFin.setHeaderText("Level not complete");
            notFin.setContentText("Do you want to continue playing?");

            ((Button) notFin.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) notFin.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
            Optional<ButtonType> opt = notFin.showAndWait();

            if(opt.get() == ButtonType.OK){
                notFin.close();
            }
            else if(opt.get() == ButtonType.CANCEL){
                //TODO: show leaderboard
                Screen.switchPage(new MainMenu());
            }
        }
        else{
            Alert fin = new Alert(Alert.AlertType.INFORMATION);
            fin.setHeaderText("You have completed this level");
            fin.setTitle("Congratulations");
            Optional<ButtonType> opt = fin.showAndWait();
            if(opt.get() == ButtonType.OK){
                manager.showLeaderboard();
                Screen.switchPage(new SelectLevel());
            }
        }
    }

    @Override
    public void prepareDesign() {
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        addButton("Submit", 0, 80, event -> {
            showSubmissionDialog();
        });
        addCounters();
        User user = User.getInstance();

        addButton("Get Hint", 0,40, event ->{

            if(user.hasHint()){
                user.useHint();
                DatabaseConnection.getInstance().updateHint();
                LevelManager.getInstance().showHint();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Enough Hints");
                alert.setHeaderText(null);
                alert.setContentText("You do not have enough hints.\nWould you like to buy some?");
                //alert.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                Optional<ButtonType> opt = alert.showAndWait();
                if(opt.get() == ButtonType.OK) {

                    Dialog<PurchaseInfo> crediCardInfoAlert = new Dialog<PurchaseInfo>();

                    // Create the username and password labels and fields.
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));
                    crediCardInfoAlert.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    TextField name = new TextField();
                    name.setPromptText("Name");
                    TextField surname = new TextField();
                    surname.setPromptText("Surname");
                    TextField cardNo = new TextField();
                    cardNo.setPromptText("Card No");
                    TextField cvc = new TextField();
                    cvc.setPromptText("CVC");
                    TextField date = new TextField();
                    date.setPromptText("Expiry Date");

                    grid.add(new Label("Name:"), 0, 0);
                    grid.add(name, 1, 0);
                    grid.add(new Label("Surname:"), 0, 1);
                    grid.add(surname, 1, 1);
                    grid.add(new Label(" CardNo:"), 0, 2);
                    grid.add(cardNo, 1, 2);
                    grid.add(new Label(" CVC:"), 0, 3);
                    grid.add(cvc, 1, 3);
                    grid.add(new Label(" Expiry Date:"), 0, 4);
                    grid.add(date, 1, 4);

                    crediCardInfoAlert.getDialogPane().setContent(grid);

                    crediCardInfoAlert.setResultConverter(dialogButton -> {
                        if (dialogButton == ButtonType.OK) {
                            PurchaseInfo pinfo = new PurchaseInfo(name.getText(), surname.getText(), cardNo.getText()
                            ,cvc.getText(), date.getText(), "1.2");
                            return pinfo;
                        }
                        return null;
                    });


                    Optional<PurchaseInfo> result = crediCardInfoAlert.showAndWait();
                    result.ifPresent(pinfo -> {
                        /*PurchaseInfo pinfo = new PurchaseInfo("John", "Doe", "5528790000000008",
                                "123", "12.2030", "1.2");*/
                        Entity entity = new Entity(pinfo);
                        String error = entity.doPayment();
                        //TODO Check internet

                        Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
                        resultAlert.setTitle("Payment Result");
                        if(error == null) {
                            resultAlert.setContentText("You successfully bought hints.");
                        } else {
                            resultAlert.setContentText("An error occured: " + error);
                        }
                        resultAlert.showAndWait();

                    });
                }
            }
            hints.setText("Number of Hints: " + user.getHint());

        });
        hints = new Label("Number of Hints: " + user.getHint());
        hints.setLayoutX(100);
        hints.setLayoutY(60);
        root.getChildren().add(hints);
    }

    protected void addCounters(){
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
            private long excessTime;
            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                excessTime = 0;
                super.start();
            }

            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                if(stopCountingTime) {
                    excessTime++;
                }
                timeElapsed = (now - startTime) / 1000;
                time.setText("Time Elapsed: " + timeElapsed + " sec");
            }
        };
        timer.start();

        moves.setText("Number of Moves: 0");
    }


    private void goNextLevel() {

    }

    private void showHint() {
        LevelManager.getInstance().showHint();
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
        this.numberOfMoves = numberOfMoves;
        moves.setText("Number of Moves: " + numberOfMoves);
    }
}
