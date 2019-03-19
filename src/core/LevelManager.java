package core;

import data.GroundData;
import database.DatabaseConnection;
import javafx.scene.Group;

import java.util.Arrays;

public class LevelManager {
    private ComponentFactory gameComp;
    private Level currentLevel;
    public LevelManager(Group root) {
        gameComp = new ComponentFactory(root);
    }

    public void createLevel(boolean isMovable, int levelID ) {
        /*DatabaseConnection conn = DatabaseConnection.getInstance();

        String columns = conn.executeSQL("SELECT * FROM combinations WHERE ID = '" + levelNo + "'", "ROTATIONS");
        String locations = conn.executeSQL("SELECT * FROM combinations WHERE ID = '" + levelNo + "'", "LOCATIONS");

        System.out.println(columns);
        System.out.println(locations);
        */
        currentLevel = new Level( gameComp.createGrounds(isMovable), gameComp.createPieces() );
    }

    public void createLevel(boolean isMovable){
        currentLevel = new Level(gameComp.createGrounds(isMovable), gameComp.createPieces());
    }

    public void draw(){
        currentLevel.drawLevel();
    }
    public void uploadLevel() {
        GroundData[] results = currentLevel.get4GroundData();
        System.out.print(Arrays.toString(results));


    }

    public boolean isValidComb() {
        return currentLevel.isValid();
    }


}
