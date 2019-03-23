package core;

import data.GroundData;
import data.Record;
import data.User;
import database.DatabaseConnection;
import interfaces.MoveObserver;
import javafx.scene.Group;

import java.util.Arrays;

public class LevelManager {
    private Level currentLevel;
    private int levelID;
    private static LevelManager instance = new LevelManager();
    private int numberOfMoves;
    private int timeElapsed;
    private MoveObserver observer;

    private LevelManager() {
        reset();
    }

    private void reset(){
        numberOfMoves = 0;
        timeElapsed = 0;
        currentLevel = null;
        levelID = -1;
    }

    public static LevelManager getInstance(){
        return instance;
    }
    public void createLevel(boolean isMovable, int levelID, Group root ) {
        reset();
        this.levelID = levelID;
        DatabaseConnection db = DatabaseConnection.getInstance();
        GroundData[] gdatas = db.getLevel(levelID);

        ComponentFactory gameComp = new ComponentFactory(root);

        currentLevel = new Level( gameComp.createGrounds(isMovable, gdatas), gameComp.createPieces() );
    }

    public void createLevel(boolean isMovable, Group root){
        reset();
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

    public void uploadResults(){
        if(levelID == -1){
            return;
        }
        DatabaseConnection db = DatabaseConnection.getInstance();
        db.executeSQL(String.format("INSERT INTO leaderboards (USER_NICK, LEVEL_ID, TIME_ELAPSED, MOVES, TOTAL_SCORE) " +
                "VALUE ('%s',%d,%d,%d,%d)",
                User.getInstance().getNickName(), levelID, timeElapsed, numberOfMoves, 1000 / (numberOfMoves * timeElapsed)));
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

    public void showLeaderboard(){
        Record[] lboard = DatabaseConnection.getInstance().getLeaderboard(levelID);
        System.out.println(Arrays.toString(lboard));
    }
}
