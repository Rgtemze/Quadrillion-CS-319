package ui;

import data.User;
import database.DatabaseConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import ui.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Login extends Page {

    public Login(){
        prepareDesign();
    }

    @Override
    public void prepareDesign() {

        Label welcome = new Label("Welcome to Quadrillion!");
        welcome.setStyle("-fx-font-family: \"Dokdo\";");
        welcome.setFont(new Font(60));


        TextField userName = new TextField();
        userName.setPromptText("User Name");
        userName.setMinSize(BTN_WIDTH, BTN_HEIGHT);
        userName.setFont(new Font(30));


        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        pass.setMinSize(BTN_WIDTH, BTN_HEIGHT);
        pass.setFont(new Font(30));

        Button login = createButon("Login", Screen.getWidth() / 2 + BTN_WIDTH / 2,400, BTN_WIDTH, BTN_HEIGHT, 30, event -> {

            boolean result = false;
            if(!userName.getText().isEmpty() && !pass.getText().isEmpty()) {
                DatabaseConnection db = DatabaseConnection.getInstance();
                result =db.signIn(userName.getText(), pass.getText());

                if(!result){
                    createAlert("Username and password do not match!").show();
                }
            } else {
                createAlert("Please fill both username and password fields!").show();
            }

        });

        Button signUp = createButon("Create User", Screen.getWidth() / 2 + BTN_WIDTH / 2,500, BTN_WIDTH, BTN_HEIGHT, 30, event -> {

            if(!userName.getText().isEmpty() && !pass.getText().isEmpty()) {
                DatabaseConnection db = DatabaseConnection.getInstance();
                boolean result = db.createUser(userName.getText(), pass.getText());

                if(!result){
                    createAlert("Username already exists!").show();
                }
            } else {
                createAlert("Please fill both username and password fields!").show();
            }
        });
        root.setAlignment(Pos.CENTER);
        root.add(welcome, 0,0);
        root.setVgap(50);
        root.add(userName, 0,1);
        root.setVgap(20);
        root.add(pass, 0,2);
        root.add(login, 0,3);
        root.add(signUp, 0,4);
    }

    private Alert createAlert(String text){
        Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
        resultAlert.setTitle("Info");
        resultAlert.setContentText(text);
        return resultAlert;
    }
}
