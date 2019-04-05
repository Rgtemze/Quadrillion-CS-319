package ui;

import core.LevelManager;
import core.Level;
import data.GroundData;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ComposeLevel extends Page {
    private LevelManager fetcher;
    public ComposeLevel(){
        fetcher = LevelManager.getInstance();
        fetcher.createLevel(true, root);
        fetcher.draw();
    }


    @Override
    public void prepareDesign() {
        addButton("Menu",0,0, event -> {Screen.switchPage(new MainMenu());});
        addButton("Check",0,40, event -> {
            if(fetcher.isValidComb()) {
                try {
                    fetcher.uploadLevel();
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
    }
}
