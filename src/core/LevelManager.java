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

public class LevelManager{
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

    public void createLevel(boolean isMovable, int levelID, Group root) {
        reset();
        this.levelID = levelID;
        DatabaseConnection db = DatabaseConnection.getInstance();
        GroundData[] gdatas = db.getLevel(levelID);

        ComponentFactory gameComp = new ComponentFactory(root);

        currentLevel = new Level( gameComp.createGrounds(isMovable, gdatas), gameComp.createPieces(), this.observer, false);
    }

    public void createLevel(boolean isMovable, Group root){
        // Reset the LevelManager attributes.
        reset();
        ComponentFactory gameComp = new ComponentFactory(root);
        currentLevel = new Level(gameComp.createGrounds(isMovable), gameComp.createPieces(), null, true);
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

    public void uploadResults(User user, boolean isRanked){
        if(levelID == -1 || numberOfMoves == 0 || timeElapsed == 0){
            return;
        }
        DatabaseConnection db = DatabaseConnection.getInstance();
        int score = 10000 / (numberOfMoves * timeElapsed);
        Record highScore = db.getHighScore(user.getNickName(), levelID, isRanked);

        System.out.println(score + "/" + highScore);
        if(highScore == null) {
            db.executeSQL(String.format("INSERT INTO leaderboards (USER_NICK, LEVEL_ID, TIME_ELAPSED, MOVES, TOTAL_SCORE, ISRANKED) " +
                            "VALUE ('%s',%d,%d,%d,%d, %d)",
                    user.getNickName(), levelID, timeElapsed, numberOfMoves, score, isRanked ? 1 : 0));
        } else if(score > highScore.getScore()){
            db.executeSQL(String.format("UPDATE leaderboards SET TIME_ELAPSED = %d, MOVES = %d, TOTAL_SCORE = %d WHERE " +
                    "LEVEL_ID = %d and USER_NICK = '%s' and ISRANKED = %d;",
                    timeElapsed, numberOfMoves, score, levelID, user.getNickName(),isRanked ? 1 : 0));
        }
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

    public void showLeaderboard(final User user){
        DatabaseConnection db = DatabaseConnection.getInstance();
        Record[] leaderboard = db.getLeaderboard(levelID);
        Record highScoreRanked = db.getHighScore(user.getNickName(), levelID, true);
        Record highScoreCasual = db.getHighScore(user.getNickName(), levelID, false);
        openDialog(leaderboard, highScoreCasual, highScoreRanked);

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    private void openDialog(Record[] leaderboard, Record highScoreCasual, Record highScoreRanked){
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

        int rowIndex;
        for(rowIndex = 0; rowIndex < leaderboard.length; rowIndex++) {
            grid.add(new Label(leaderboard[rowIndex].getUserID()), 0, rowIndex + 1);
            grid.add(new Label(leaderboard[rowIndex].getMoves() + ""), 1, rowIndex + 1);
            grid.add(new Label(leaderboard[rowIndex].getTimes() + "sec"), 2, rowIndex + 1);
            grid.add(new Label(leaderboard[rowIndex].getScore() + ""), 3, rowIndex + 1);
        }


        grid.add(new Label("..."), 0, rowIndex + 1);

        // Show highscore of the user in Casual Mode
        if(highScoreCasual != null) {
            rowIndex++;
            grid.add(new Label(highScoreCasual.getUserID()), 0, rowIndex + 1);
            grid.add(new Label(highScoreCasual.getMoves() + ""), 1, rowIndex + 1);
            grid.add(new Label(highScoreCasual.getTimes() + "sec"), 2, rowIndex + 1);
            grid.add(new Label(highScoreCasual.getScore() + " (Your highest score in casual mode)"), 3, rowIndex + 1);
        }
        // Show highscore of the user in Ranked Mode
        if(highScoreRanked != null) {
            rowIndex++;
            grid.add(new Label(highScoreRanked.getUserID()), 0, rowIndex + 1);
            grid.add(new Label(highScoreRanked.getMoves() + ""), 1, rowIndex + 1);
            grid.add(new Label(highScoreRanked.getTimes() + "sec"), 2, rowIndex + 1);
            grid.add(new Label(highScoreRanked.getScore() + " (Your highest score in ranked mode)"), 3, rowIndex + 1);
        }
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();
    }

    public boolean isGameWon() {
        return currentLevel.isGameWon();
    }

    public void setMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }
}
