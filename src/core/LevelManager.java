package core;

import data.GroundData;
import database.DatabaseConnection;
import interfaces.MoveObserver;
import javafx.scene.Group;

import java.util.Arrays;

public class LevelManager {
    private Level currentLevel;
    private static LevelManager instance = new LevelManager();
    private int numberOfMoves;
    private int timeElapsed;
    private MoveObserver observer;

    private LevelManager() {
        numberOfMoves = 0;
        timeElapsed = 0;
    }

    public static LevelManager getInstance(){
        return instance;
    }
    public void createLevel(boolean isMovable, int levelID, Group root ) {
        DatabaseConnection db = DatabaseConnection.getInstance();

        GroundData[] gdatas = db.getLevel(levelID);

        ComponentFactory gameComp = new ComponentFactory(root);

        currentLevel = new Level( gameComp.createGrounds(isMovable, gdatas), gameComp.createPieces() );
    }

    public void createLevel(boolean isMovable, Group root){
        ComponentFactory gameComp = new ComponentFactory(root);
        currentLevel = new Level(gameComp.createGrounds(isMovable));
    }

    public void draw(){
        currentLevel.drawLevel();
    }
    public void uploadLevel() {
        GroundData[] results = currentLevel.get4GroundData();
        // System.out.print(Arrays.toString(results));
        DatabaseConnection db = DatabaseConnection.getInstance();
        String rotations = "", locations = "", isFront = "";
        for(GroundData result : results){
            rotations += String.format("%d;",result.rotation);
            locations += String.format("%d,%d;", result.location.x, result.location.y);
            isFront += String.format("%s;", (result.isFront) ? "1" : "0");
        }
        rotations = rotations.substring(0, rotations.length() - 1);
        locations = locations.substring(0, locations.length() - 1);
        isFront = isFront.substring(0, isFront.length() - 1);
        db.executeSQL(String.format("INSERT INTO level (ROTATIONS, LOCATIONS, ISFRONT) VALUES ('%s', '%s', '%s')"
                ,rotations, locations, isFront));

    }

    public boolean isValidComb() {
        return currentLevel.isValid();
    }


    public void incrementMoves() {
        numberOfMoves++;
        observer.notifyMoveChanged(numberOfMoves);
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void showHint(){
        currentLevel.showHint();
    }

    public void setObserver(MoveObserver observer){
        this.observer = observer;
    }
}
