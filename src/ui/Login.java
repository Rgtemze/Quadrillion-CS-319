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


    //using MySql, users can login.
    public void login() {

    }

    //using MySql, users can also register.
    public void register() {

    }

    @Override
    public void prepareDesign() {

        Label welcome = new Label("Welcome to Quadrillion!");
        welcome.setStyle("-fx-font-family: \"Dokdo\";");
        welcome.setFont(new Font(60));
        welcome.setLayoutX(Screen.getWidth() / 2 + 50);
        welcome.setLayoutY(100);


        TextField userName = new TextField();
        userName.setPromptText("User Name");
        userName.setLayoutX(Screen.getWidth() / 2 + 5);
        userName.setLayoutY(200);
        userName.setMinSize(BTN_WIDTH, BTN_HEIGHT);
        userName.setFont(new Font(30));


        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        pass.setLayoutX(Screen.getWidth() / 2 + 5);
        pass.setLayoutY(290);
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


        root.getChildren().addAll(welcome, userName,  pass, login, signUp);
    }

    private Alert createAlert(String text){
        Alert resultAlert = new Alert(Alert.AlertType.INFORMATION);
        resultAlert.setTitle("Info");
        resultAlert.setContentText(text);
        return resultAlert;
    }
}
