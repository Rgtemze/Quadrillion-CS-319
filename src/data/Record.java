package data;

public class Record {
    private String userID;
    private int levelID;
    private int times; // in miliseconds
    private int moves;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    @Override
    public String toString() {
        return "Record{" +
                "\nuserID='" + userID  +
                ", \nlevelID=" + levelID +
                ", \ntimes=" + times +
                ", \nmoves=" + moves +
                ", \nscore=" + score +
                "\n}";
    }
}
