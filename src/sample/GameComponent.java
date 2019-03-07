package sample;

import javafx.scene.Group;

public class GameComponent {

    private Piece[] pieces;
    private Ground[] grounds;
    private Group root;

    public GameComponent(Group root){
        this.root = root;
        initPieces();
        initGrounds();
    }

    void initPieces() {
        Piece p = (new Piece.PieceBuilder(root)).addOffset(5, 5).build();

        pieces[0] = p;
    }

    void initGrounds() {
        Ground g1 = new Ground(root,100,100);

        Ground g2 = new Ground(root,340,160);

        Ground g3 = new Ground(root,100, 340);

        Ground g4 = new Ground(root,340, 400);

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
