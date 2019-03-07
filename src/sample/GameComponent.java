package sample;

import javafx.scene.Group;

public class GameComponent {

    private Piece[] pieces;
    private Ground[] grounds;
    private Group root;

    public GameComponent(Group root){
        this.root = root;
        pieces = new Piece[1];
        grounds = new Ground[4];
        initPieces();
        initGrounds();
    }

    void initPieces() {
        Piece p = (new Piece.PieceBuilder(root)).addOffset(5, 5).build();

        pieces[0] = p;
    }

    void initGrounds() {
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
