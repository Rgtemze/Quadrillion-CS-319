package core;

import data.GroundData;
import data.Record;
import interfaces.MoveObserver;

import java.awt.*;
import java.util.Arrays;

public class Level {

    private Piece[] pieces;
    private Ground[] grounds;
    private int[][] combination;
    private Record[] leaderboard;
    private int minX;
    private int minY;
    private MoveObserver observer;

    public Level( Ground[] grounds, Piece[] pieces, MoveObserver observer ) {
        this.pieces = pieces;
        this.grounds = grounds;
        this.observer = observer;
        combination = new int[16][16];

        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                combination[i][j] = 1;
            }
        }

        combineGrounds();
        adjustPieces();
        HintManager.getInstance().createPieceStates( pieces );
    }

    public Level( Ground[] grounds) {
        this.grounds = grounds;
        combination = new int[16][16];

        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                combination[i][j] = 1;
            }
        }

        combineGrounds();
    }

    private void adjustPieces() {
        for(Piece p : pieces){
            p.setLevel(this);
            p.setObserver(observer);
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
                    int slotIndexX = (int)((ground.location.getX() + i * Ground.EDGE_LENGTH - minX) / Ground.EDGE_LENGTH);
                    int slotIndexY = (int)((ground.location.getY() + j * Ground.EDGE_LENGTH - minY) / Ground.EDGE_LENGTH);
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
        for (int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(combination[i][j] == 0) return false;
            }
        }
        return true;
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

        if(pieces != null) {
            for (Piece p : pieces) {
                p.draw();
            }
        }
    }

    void showHint( ) {
        HintManager.getInstance().showHint( combination, pieces );
        System.out.println("Hint shown.");
    }

    public GroundData[] get4GroundData(){
        relocateGrounds();
        GroundData[] groundComb = new GroundData[grounds.length];
        for(int i = 0; i < grounds.length; i++){
            groundComb[i] = grounds[i].getResult();
        }
        return groundComb;
    }

    public int[][] getCombination() {
        return combination;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public boolean isValid(){
        for(int i = 0; i < grounds.length-1; i++){
            Point loc1 = grounds[i].location;
            for (int j = i+1; j < grounds.length; j++){
                Point loc2 = grounds[j].location;
                if (isIntersect(loc1.x, loc1.y, loc2.x, loc2.y)) {
                    return false;
                }
            }
        }
        System.out.println("Not intersect");
        int[] conn = new int[4];
        for(int i = 0; i < 4; i++)
            conn[i] = i;

        for(int i = 0; i < grounds.length-1; i++){
            Point loc1 = grounds[i].location;
            for (int j = i+1; j < grounds.length; j++){
                Point loc2 = grounds[j].location;
                if (isConnected(loc1.x, loc1.y, loc2.x, loc2.y)) {
                    System.out.println("Grounds " + i + loc1 + " and " + j + loc2 + "connected");
                    conn[i] = Math.min(conn[i], conn[j]);
                    conn[j] = Math.min(conn[i], conn[j]);
                }
            }
        }

        for(int i = 0; i < 4; i++)
            if (conn[i] != 0) return false;

        return true;
    }



    private boolean isIntersect(int x1, int y1, int x2, int y2){
        int distx = Math.abs(x1 - x2);
        int disty = Math.abs(y1 - y2);

        return distx < 240 && disty < 240;
    }

    private boolean isConnected(int x1, int y1, int x2, int y2){
        int distx = Math.abs(x1 - x2);
        int disty = Math.abs(y1 - y2);

        return (distx <= 180 && disty == 240) || (distx == 240 && disty <= 180);
    }

    private void relocateGrounds(){
        int minx = Integer.MAX_VALUE;
        int miny = Integer.MAX_VALUE;
        final int OFFSET = 100;
        for (Ground ground : grounds) {
            if (ground.location.x < minx)
                minx = ground.location.x;
            if (ground.location.y < miny)
                miny = ground.location.y;
        }
        for (Ground ground : grounds){
            ground.location.x -= minx - OFFSET;
            ground.location.y -= miny - OFFSET;
        }
    }

    public boolean isOccupied(int x, int y) {
        return combination[x][y] == 1;
    }

    public void setOccupation(int x, int y, int occupation) {
        combination[x][y] = occupation;
    }

    public void printOccupation(){

        for(int i = 0; i < combination.length; i++){
            System.out.println(i + " " + Arrays.toString(combination[i]));
        }
    }



}
