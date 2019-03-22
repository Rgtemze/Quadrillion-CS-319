package core;

import data.GroundData;
import javafx.scene.Group;
import javafx.scene.paint.Paint;

public class ComponentFactory {

    private Piece[] pieces;
    private Ground[] grounds;
    private Group root;

    public ComponentFactory(Group root){
        this.root = root;
    }

    public Piece[] createPieces() {
        Piece[] pieces = new Piece[12];
        pieces[0] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,0)
                .setX(700).setY(50).setColor(Paint.valueOf("red")).build();
        pieces[1] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,2)
                .addOffset(1,0).addOffset(1,3).setX(700).setY(350).setColor(Paint.valueOf("blue")).build();
        pieces[2] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(2,1).addOffset(1,0)
                .addOffset(1,1).setX(700).setY(650).build();
        pieces[3] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,0).addOffset(2,0)
                .addOffset(1,1).addOffset(1,2).setX(900).setY(50).build();
        pieces[4] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,2)
                .addOffset(0,1).addOffset(1,3).setX(900).setY(350).build();
        pieces[5] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,0).addOffset(1,1)
                .addOffset(2,1).addOffset(2,2).setX(900).setY(650).build();
        pieces[6] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(0,1)
                .addOffset(1,1).addOffset(2,1).setX(1100).setY(50).build();
        pieces[7] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(0,1)
                .addOffset(0,2).addOffset(1,2).setX(1100).setY(350).build();
        pieces[8] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(1,1)
                .addOffset(2,1).addOffset(1,2).setX(1100).setY(650).build();
        pieces[9] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(0,1).addOffset(0,2)
                .addOffset(1,2).addOffset(2,2).setX(1300).setY(50).build();
        pieces[10] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(0,1).addOffset(1,1)
                .addOffset(2,1).addOffset(2,2).setX(1300).setY(350).build();
        pieces[11] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(0,1).addOffset(0,2)
                .addOffset(0,3).addOffset(1,1).setX(1300).setY(650).build();
        return pieces;
    }

    public Ground[] createGrounds(boolean isMovable, GroundData[] gd) {
        Ground[] grounds = new Ground[4];
        Ground g1 = (new Ground.GroundBuilder(root))
                .setX(gd[0].location.x)
                .setY(gd[0].location.y)
                .setMovable(isMovable)
                .setOccupied(true, 0, 0)
                .setOccupied(true, 2, 1)
                .build();

        Ground g2 = (new Ground.GroundBuilder(root))
                .setX(gd[1].location.x)
                .setY(gd[1].location.y)
                .setMovable(isMovable)
                .setOccupied(true, 3, 0)
                .setOccupied(true, 1, 1)
                .build();
        Ground g3 = (new Ground.GroundBuilder(root))
                .setX(gd[2].location.x)
                .setY(gd[2].location.y)
                .setMovable(isMovable)
                .setOccupied(true, 2, 0)
                .build();
        Ground g4 = (new Ground.GroundBuilder(root))
                .setX(gd[3].location.x)
                .setY(gd[3].location.y)
                .setMovable(isMovable)
                .setOccupied(true, 1, 0)
                .setOccupied(true, 3, 0)
                .build();

        grounds[0] = g1;
        grounds[1] = g2;
        grounds[2] = g3;
        grounds[3] = g4;
        return grounds;
    }
    public Ground[] createGrounds(boolean isMovable) {
        Ground[] grounds = new Ground[4];
        Ground g1 = (new Ground.GroundBuilder(root))
                    .setX(100)
                    .setY(100)
                    .setMovable(isMovable)
                    .setOccupied(true, 0, 0)
                    .setOccupied(true, 2, 1)
                    .build();

        Ground g2 = (new Ground.GroundBuilder(root))
                .setX(340)
                .setY(160)
                .setMovable(isMovable)
                .setOccupied(true, 3, 0)
                .setOccupied(true, 1, 1)
                .build();
        Ground g3 = (new Ground.GroundBuilder(root))
                .setX(100)
                .setY(340)
                .setMovable(isMovable)
                .setOccupied(true, 2, 0)
                .build();
        Ground g4 = (new Ground.GroundBuilder(root))
                .setX(340)
                .setY(400)
                .setMovable(isMovable)
                .setOccupied(true, 1, 0)
                .setOccupied(true, 3, 0)
                .build();

        grounds[0] = g1;
        grounds[1] = g2;
        grounds[2] = g3;
        grounds[3] = g4;
        return grounds;
    }
}
