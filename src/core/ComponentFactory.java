package core;

import javafx.scene.Group;

public class GameComponent {

    private Piece[] pieces;
    private Ground[] grounds;
    private Group root;

    public GameComponent(Group root){
        this.root = root;
        pieces = new Piece[8];
        grounds = new Ground[4];
    }

    public void init(){
        initPieces();
        initGrounds();
    }

    private void initPieces() {
        pieces[0] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,0)
                .addOffset(1,0).setX(700).setY(50).build();
        pieces[1] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,2)
                .addOffset(1,0).addOffset(1,3).setX(700).setY(250).build();
        pieces[2] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(2,1).addOffset(1,0)
                .addOffset(1,1).setX(700).setY(450).build();
        pieces[3] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,0).addOffset(2,0)
                .addOffset(1,1).addOffset(1,2).setX(900).setY(50).build();
        pieces[4] = (new Piece.PieceBuilder(root)).addOffset(1, 1).addOffset(0,0).addOffset(1,2)
                .addOffset(0,1).addOffset(1,3).setX(900).setY(250).build();
        pieces[5] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,0).addOffset(1,1)
                .addOffset(2,1).addOffset(2,2).setX(900).setY(450).build();
        pieces[6] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(0,1)
                .addOffset(1,1).addOffset(2,1).setX(1100).setY(50).build();
        pieces[7] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(0,1)
                .addOffset(0,2).addOffset(1,2).setX(1100).setY(250).build();
    }

    private void initGrounds() {
        Ground g1 = (new Ground.GroundBuilder(root))
                    .setX(100)
                    .setY(100)
                    .setOccupied(true, 1, 2)
                    .setOccupied(true, 3, 3)
                    .build();

        Ground g2 = (new Ground.GroundBuilder(root))
                .setX(340)
                .setY(160)
                .setOccupied(true, 1, 2)
                .setOccupied(true, 3, 3)
                .build();
        Ground g3 = (new Ground.GroundBuilder(root))
                .setX(100)
                .setY(340)
                .setOccupied(true, 1, 2)
                .setOccupied(true, 3, 3)
                .build();
        Ground g4 = (new Ground.GroundBuilder(root))
                .setX(340)
                .setY(400)
                .setOccupied(true, 1, 2)
                .setOccupied(true, 3, 3)
                .build();

        grounds[0] = g1;
        grounds[1] = g2;
        grounds[2] = g3;
        grounds[3] = g4;
    }

    public Piece[] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[] pieces) {
        this.pieces = pieces;
    }

    public Ground[] getGrounds() {
        return grounds;
    }

    public void setGrounds(Ground[] grounds) {
        this.grounds = grounds;
    }
}