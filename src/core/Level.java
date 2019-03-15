package core;

import data.Record;

import java.util.Arrays;

public class Level {

    private Piece[] pieces;
    private Ground[] grounds;
    private int[][] combination;
    private Record[] leaderboard;
    private boolean isGameFinished;
    private int minX;
    private int minY;

    public Level( Ground[] grounds, Piece[] pieces ) {
        this.pieces = pieces;
        this.grounds = grounds;
        combination = new int[16][16];

        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                combination[i][j] = 1;
            }
        }

        combineGrounds();
        adjustPieces();
    }

    private void adjustPieces() {
        for(Piece p : pieces){
            p.setLevel(this);
        }
    }

    public void combineGrounds() {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        for(Ground ground: grounds){
            minX = Math.min(minX, ground.location.x);
            minY = Math.min(minY, ground.location.y);
        }

        for(Ground ground: grounds) {
            System.out.println(ground.location);
            for (int i = 0; i < ground.getActiveBoard().length; i++) {

                for (int j = 0; j < ground.getActiveBoard().length; j++) {
                    int slotIndexX = (int)(ground.location.getX() + i * Ground.EDGE_LENGTH - minX) / Ground.EDGE_LENGTH;
                    int slotIndexY = (int)(ground.location.getY() + j * Ground.EDGE_LENGTH - minY) / Ground.EDGE_LENGTH;
                    //System.out.println("Slot X: " + slotIndexX + " Slot Y: " + slotIndexY);
                    combination[slotIndexY][slotIndexX] = ground.getActiveBoard()[i][j];
                }
            }
        }
        System.out.println("Min X: " + minX + ", Min Y: " + minY);

    }

    /*
    * The game finishes when all the pieces settled,
    * which is a win state.
    */
    public boolean isGameWon() {
        return isGameFinished;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    /*
    * Draw all pieces and grounds of the game
    */
    public void drawLevel() {

        for(Ground g : grounds){
            g.draw();
        }
        for(Piece p : pieces){
            p.draw();
        }
    }

    void showHint( ) {

    }

    void seeLeaderBoard() {


    }

    public boolean isOccupied(int x, int y) {
        return combination[x][y] == 1;
    }

    public void setOccupation(int x, int y, int occupation) {
        combination[x][y] = occupation;
    }

    public void printOccupation(){

        for(int i = 0; i < combination.length; i++){
            System.out.println(Arrays.toString(combination[i]));
        }
    }
}