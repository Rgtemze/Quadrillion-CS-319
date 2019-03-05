package sample;

public class Ground {

    private int[][] frontBoard;
    private int[][] backBoard;
    private boolean isFront = true;

    public Ground() {
    }

    public int[][] getActiveBoard() {
        if( isFront ) return frontBoard;
        return backBoard;
    }

    public void flipBoard() {
        isFront = !isFront;
    }

}
