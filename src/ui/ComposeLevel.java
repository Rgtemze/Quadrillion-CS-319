package ui;

import core.LevelManager;
import core.Level;
import data.GroundData;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ComposeLevel extends Page{
    private Group pen;
    public ComposeLevel() {
        pen = new Group();
        root.add(pen,0,0);
        manager.createLevel(true, pen);
        manager.draw();
        prepareDesign();
        scale.setX(SCALE_FACTORX);
        scale.setY(SCALE_FACTORY);
        pen.getTransforms().add(scale);
    }


    @Override
    public void prepareDesign() {
        Button menu = addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        Button check = addButton("Check",0,60   , event -> {
            if(manager.isValidComb()) {
                try {
                    manager.uploadLevel();
                    Alert valid = new Alert(Alert.AlertType.INFORMATION);
                    valid.setTitle("Congrulations");
                    valid.setHeaderText("Level is valid");
                    valid.setContentText("Thanks for improving Quadrillion");
                    valid.showAndWait();
                    Screen.switchPage(new MainMenu());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Alert notValid = new Alert(Alert.AlertType.CONFIRMATION);
                notValid.setTitle("Error");
                notValid.setHeaderText("Level is not valid!");
                notValid.setContentText("Do you want to continue to compose level?");

                ((Button) notValid.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
                ((Button) notValid.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

                Optional<ButtonType> opt = notValid.showAndWait();

                if(opt.get() == ButtonType.OK){
                    notValid.close();
                }
                else if(opt.get() == ButtonType.CANCEL){
                    Screen.switchPage(new MainMenu());
                }
            }
        });
        pen.getChildren().addAll(menu, check);
    }
}
