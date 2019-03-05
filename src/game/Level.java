package game;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;

public class Level {
    private static final int NUMBER_OF_PIECES = 12;
    private static final int NUMBER_OF_GROUNDS = 4;
    private int minX;
    private int minY;
    public static int [][] board;
    private Group root;
    private Piece[] pieces;
    private Ground[] grounds;
    Level(Group root){
        this.root = root;
        board = new int[16][16];
        pieces = new Piece[NUMBER_OF_PIECES];
        grounds = new Ground[NUMBER_OF_GROUNDS];
        prepareGrounds();
        //minX and minY defined below
        combineGrounds(grounds);
        preparePieces();
    }

    private void prepareGrounds(){
        Ground g1 = new Ground(100,100);
        g1.drawGround(root);

        Ground g2 = new Ground(340,160);
        g2.drawGround(root);

        Ground g3 = new Ground(100, 340);
        g3.drawGround(root);

        Ground g4 = new Ground(340, 400);
        g4.drawGround(root);

        grounds[0] = g1;
        grounds[1] = g2;
        grounds[2] = g3;
        grounds[3] = g4;
    }

    private void preparePieces(){
        Piece p = new Piece(root, minX, minY);
        p.drawPiece();

        pieces[0] = p;
    }

    public void removeLevel(){
        for(Piece p: pieces){
            root.getChildren().remove(p);
        }
        for(Ground g: grounds){
            root.getChildren().remove(g);
        }
    }

    private void combineGrounds(Ground[] grounds){
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        for(Ground ground: grounds){
            minX = Math.min(minX, ground.location.x);
            minY = Math.min(minY, ground.location.y);
        }

        for(Ground ground: grounds) {
            System.out.println(ground.location);
            for (int i = 0; i < ground.ground.length; i++) {

                for (int j = 0; j < ground.ground.length; j++) {
                    int slotIndexX = (int)(ground.location.getX() + i * Ground.EDGE_LENGTH - minX) / Ground.EDGE_LENGTH;
                    int slotIndexY = (int)(ground.location.getY() + j * Ground.EDGE_LENGTH - minY) / Ground.EDGE_LENGTH;
                    //System.out.println("Slot X: " + slotIndexX + " Slot Y: " + slotIndexY);
                    board[slotIndexY][slotIndexX] = ground.ground[i][j];
                }
            }
        }
        for(int i = 0; i < board.length; i++){
            System.out.println(Arrays.toString(board[i]));
        }
        System.out.println("Min X: " + minX + ", Min Y: " + minY);
    }



}
