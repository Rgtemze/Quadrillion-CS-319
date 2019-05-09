package ui;

import com.iyzipay.model.Status;
import core.LevelManager;
import data.PurchaseInfo;
import data.User;
import database.DatabaseConnection;
import interfaces.MoveObserver;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pos.Entity;
import pos.PosService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class PlayGame extends Page implements MoveObserver {
    private boolean isGamePaused;
    protected int numberOfMoves;
    protected long timeElapsed;
    protected Label moves, time, hints;
    boolean stopCountingTime;
    private long firstTime;
    protected Group pen;
    AnimationTimer timer;
    private boolean isFirst;
    public PlayGame(){
        stopCountingTime = false;
        manager.setObserver(this);
        firstTime = 0;
        isFirst = true;
        pen = new Group();
        root.add(pen,0,0);
        prepareDesign();
        scale.setX(SCALE_FACTORX);
        scale.setY(SCALE_FACTORY);
        pen.getTransforms().add(scale);


    }

    public Group getPen(){
        return pen;
    }

    protected void showSubmissionDialog(){
        if(!manager.isGameWon()){
            Alert notFin = new Alert(Alert.AlertType.CONFIRMATION);
            notFin.setTitle("Warning");
            notFin.setHeaderText("Level not complete");
            notFin.setContentText("Do you want to continue playing?");

            ((Button) notFin.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) notFin.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
            stopCountingTime = true;
            Optional<ButtonType> opt = notFin.showAndWait();

            if(opt.get() == ButtonType.OK){
                stopCountingTime = false;
                notFin.close();
            }
            else if(opt.get() == ButtonType.CANCEL){
                //TODO: show leaderboard
                Screen.switchPage(new MainMenu());
            }
        }
        else{
            uploadResults(false);
            System.out.println("Results upload");
        }
    }

    @Override
    public void prepareDesign() {
        Button menu = addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        Button submit = addButton("Submit", 0, 60, event -> {
            showSubmissionDialog();
        });

        addCounters(pen);

        Button hint = addButton("Get Hint", 0,120, event ->{

            if(user.hasHint()){
                user.useHint();
                DatabaseConnection.getInstance().updateHint(user);
                manager.showHint();
            } else {
                stopCountingTime = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Enough Hints");
                alert.setHeaderText(null);
                alert.setContentText("You do not have enough hints.\nWould you like to buy some?");

                Optional<ButtonType> opt = alert.showAndWait();
                if(opt.get() == ButtonType.OK) {

                    Dialog<PurchaseInfo> crediCardInfoAlert = new Dialog<PurchaseInfo>();

                    // Create the username and password labels and fields.
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));
                    crediCardInfoAlert.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                    crediCardInfoAlert.setTitle("Purchase Hint");
                    crediCardInfoAlert.setHeaderText("The below fields are all required");

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
                            ,cvc.getText(), date.getText(), "1.2", user);
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
                stopCountingTime = false;
            }
            hints.setText("" + user.getHint());

        });
        hints = new Label("" + user.getHint());
        hints.setLayoutX(470);
        hints.setLayoutY(30);
        hints.setFont(new Font("Arial", 30));

        try {
            FileInputStream input = new FileInputStream("resources/key.png");

            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setLayoutX(400);
            imageView.setLayoutY(10);
            pen.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pen.getChildren().addAll(hints, menu, submit, hint);
    }

    protected void addCounters(Group pen){
        time = new Label();
        time.setLayoutX(170);
        time.setLayoutY(30);
        time.setFont(new Font("Arial", 30));
        pen.getChildren().add(time);
        moves = new Label();
        moves.setLayoutX(320);
        moves.setLayoutY(30);
        moves.setFont(new Font("Arial", 30));
        pen.getChildren().add(moves);

        try {
            FileInputStream input = new FileInputStream("resources/time.png");
            FileInputStream input2 = new FileInputStream("resources/move.png");

            Image image = new Image(input);
            Image image2 = new Image(input2);
            ImageView imageView = new ImageView(image);
            imageView.setLayoutX(100);
            imageView.setLayoutY(10);

            ImageView imageView2 = new ImageView(image2);
            imageView2.setLayoutX(250);
            imageView2.setLayoutY(10);
            pen.getChildren().addAll(imageView, imageView2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(timer == null) {
            timer = new AnimationTimer() {

                private long startTime;
                private long excessTime;

                @Override
                public void start() {
                    startTime = System.currentTimeMillis();
                    super.start();
                }

                @Override
                public void handle(long timestamp) {
                    long now = System.currentTimeMillis();
                    timeElapsed = (now - startTime) / 1000;
                    firstTime = now;
                    time.setText(timeElapsed + " sec");
                }
            };
            timer.start();
        }
        moves.setText("0");
    }


    private void goNextLevel() {

    }

    private void showHint() {
        manager.showHint();
    }

    public void prepareGame() {

    }


    public void buyHint() {
        // Probably user will have hint points
        // so that they have limited number of changes
        // to see hints
    }


    public void uploadResults(boolean isRanked) {
        if(!manager.isGameWon()) {

            Alert notFin = new Alert(Alert.AlertType.CONFIRMATION);
            notFin.setTitle("Warning");
            notFin.setHeaderText("Level not complete");
            notFin.setContentText("Do you want to continue playing?");

            ((Button) notFin.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) notFin.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
            stopCountingTime = true;
            Optional<ButtonType> opt = notFin.showAndWait();
            System.out.println("stop 1:" + stopCountingTime);
            if(opt.get() == ButtonType.OK){
                System.out.println("stop 2:" + stopCountingTime);
                stopCountingTime = false;
                notFin.close();
            }
            else if(opt.get() == ButtonType.CANCEL){
                //TODO: show leaderboard
                Screen.switchPage(new MainMenu());
            }
        } else {

            System.out.println(timeElapsed);
            System.out.println(numberOfMoves);
            timer.stop();
            stopCountingTime = true;
            Alert fin = new Alert(Alert.AlertType.INFORMATION);
            fin.setHeaderText("You have completed this level");
            fin.setTitle("Congratulations");
            System.out.println("Congrats");
            Optional<ButtonType> opt = fin.showAndWait();
            if(opt.get() == ButtonType.OK){
                System.out.println("Congrats ok");
                manager.setTimeElapsed((int) timeElapsed);
                manager.setMoves(numberOfMoves);
                manager.uploadResults(user, isRanked);
                manager.showLeaderboard(user);
                Screen.switchPage(new MainMenu());
            }
        }
    }

    @Override
    public void notifyMoveChanged() {
        this.numberOfMoves++;
        moves.setText("" + numberOfMoves);
    }
}
