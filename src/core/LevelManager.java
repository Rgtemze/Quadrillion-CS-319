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
        /*DatabaseConnection conn = DatabaseConnection.getInstance();

        String columns = conn.executeSQL("SELECT * FROM combinations WHERE ID = '" + levelNo + "'", "ROTATIONS");
        String locations = conn.executeSQL("SELECT * FROM combinations WHERE ID = '" + levelNo + "'", "LOCATIONS");

        System.out.println(columns);
        System.out.println(locations);
        */
        ComponentFactory gameComp = new ComponentFactory(root);

        currentLevel = new Level( gameComp.createGrounds(isMovable), gameComp.createPieces() );
    }

    public void createLevel(boolean isMovable, Group root){
        ComponentFactory gameComp = new ComponentFactory(root);
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


    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
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
