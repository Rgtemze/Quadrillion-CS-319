package core;

import data.GroundData;
import data.Record;
import data.User;
import database.DatabaseConnection;
import interfaces.MoveObserver;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

public class LevelManager implements MoveObserver{
    private Level currentLevel;
    private int levelID;
    private int numberOfMoves;
    private int timeElapsed;
    private MoveObserver observer;

    public LevelManager() {
        reset();
    }

    private void reset(){
        numberOfMoves = 0;
        timeElapsed = 0;
        currentLevel = null;
        levelID = -1;
    }

    public void createLevel(boolean isMovable, int levelID, Group root ) {
        reset();
        this.levelID = levelID;
        DatabaseConnection db = DatabaseConnection.getInstance();
        GroundData[] gdatas = db.getLevel(levelID);

        ComponentFactory gameComp = new ComponentFactory(root);

        currentLevel = new Level( gameComp.createGrounds(isMovable, gdatas), gameComp.createPieces(), this);
    }

    public void createLevel(boolean isMovable, Group root){
        // Reset the LevelManager attributes.
        reset();
        ComponentFactory gameComp = new ComponentFactory(root);
        currentLevel = new Level(gameComp.createGrounds(isMovable));
    }

    public void draw(){
        currentLevel.drawLevel();
    }
    public void uploadLevel() {
        GroundData[] results = currentLevel.get4GroundData();
        //System.out.print(Arrays.toString(results));
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

    public void uploadResults(User user){
        if(levelID == -1 || numberOfMoves == 0 || timeElapsed == 0){
            return;
        }
        DatabaseConnection db = DatabaseConnection.getInstance();
        db.executeSQL(String.format("INSERT INTO leaderboards (USER_NICK, LEVEL_ID, TIME_ELAPSED, MOVES, TOTAL_SCORE) " +
                "VALUE ('%s',%d,%d,%d,%d)",
                user.getNickName(), levelID, timeElapsed, numberOfMoves, 1000 / (numberOfMoves * timeElapsed)));
    }

    public boolean isValidComb() {
        return currentLevel.isValid();
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
        openDialog(DatabaseConnection.getInstance().getLeaderboard(levelID));

    }

    private void openDialog(Record[] leaderboard){
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Leaderboard");
        dialog.setHeaderText("Top records of this level");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Nickname"), 0, 0);
        grid.add(new Label("# of Moves"), 1, 0);
        grid.add(new Label("Time Elapsed"), 2, 0);
        grid.add(new Label("Score"), 3, 0);


        for(int i = 0; i < leaderboard.length; i++) {
            grid.add(new Label(leaderboard[i].getUserID()), 0, i + 1);
            grid.add(new Label(leaderboard[i].getMoves() + ""), 1, i + 1);
            grid.add(new Label(leaderboard[i].getTimes() + "sec"), 2, i + 1);
            grid.add(new Label(leaderboard[i].getScore() + ""), 3, i + 1);
        }

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();
    }

    public boolean isGameWon() {
        return currentLevel.isGameWon();
    }

    @Override
    public void notifyMoveChanged(int numberOfMoves) {
        numberOfMoves++;
        observer.notifyMoveChanged(numberOfMoves);
    }
}
