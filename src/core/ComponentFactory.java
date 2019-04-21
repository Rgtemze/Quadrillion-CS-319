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
                .setX(1000).setY(50).setId( 2 ).setColor(Paint.valueOf("red")).build();
        pieces[1] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,2)
                .addOffset(1,0).addOffset(1,3).setX(1000).setY(350).setId( 3 ).setColor(Paint.valueOf("blue")).build();
        pieces[2] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(2,1).addOffset(1,0)
                .addOffset(1,1).setX(1000).setY(650).setId( 4 ).setColor(Paint.valueOf("green")).build();
        pieces[3] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,0).addOffset(2,0)
                .addOffset(1,1).addOffset(1,2).setX(1200).setY(50).setId( 5 ).setColor(Paint.valueOf("yellow")).build();
        pieces[4] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,1).addOffset(1,2)
                .addOffset(0,1).addOffset(1,3).setX(1200).setY(350).setId( 6 ).setColor(Paint.valueOf("cyan")).build();
        pieces[5] = (new Piece.PieceBuilder(root)).addOffset(0, 0).addOffset(1,0).addOffset(1,1)
                .addOffset(2,1).addOffset(2,2).setX(1200).setY(650).setId( 7 ).setColor(Paint.valueOf("gray")).build();
        pieces[6] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(0,1)
                .addOffset(1,1).addOffset(2,1).setX(1400).setY(50).setId( 8 ).setColor(Paint.valueOf("magenta")).build();
        pieces[7] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(0,1)
                .addOffset(0,2).addOffset(1,2).setX(1400).setY(350).setId( 9 ).setColor(Paint.valueOf("pink")).build();
        pieces[8] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(1,0).addOffset(1,1)
                .addOffset(2,1).addOffset(1,2).setX(1400).setY(650).setId( 10 ).setColor(Paint.valueOf("purple")).build();
        pieces[9] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(0,1).addOffset(0,2)
                .addOffset(1,2).addOffset(2,2).setX(1600).setY(50).setId( 11 ).setColor(Paint.valueOf("orange")).build();
        pieces[10] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(0,1).addOffset(1,1)
                .addOffset(2,1).addOffset(2,2).setX(1600).setY(350).setId( 12 ).setColor(Paint.valueOf("turquoise")).build();
        pieces[11] = (new Piece.PieceBuilder(root)).addOffset(0,0).addOffset(0,1).addOffset(0,2)
                .addOffset(0,3).addOffset(1,1).setX(1600).setY(650).setId( 13 ).setColor(Paint.valueOf("lightgreen")).build();
        return pieces;
    }

    public Ground[] createGrounds(boolean isMovable, GroundData[] gd) {
        Ground[] grounds = new Ground[4];
        Ground g1 = (new Ground.GroundBuilder(root))
                .setX(gd[0].location.x)
                .setY(gd[0].location.y)
                .setMovable(isMovable)
                .setRotation(gd[0].rotation)
                .setOccupied(true, 0, 0)
                .setOccupied(true, 0, 2)
                .build();

        Ground g2 = (new Ground.GroundBuilder(root))
                .setX(gd[1].location.x)
                .setY(gd[1].location.y)
                .setMovable(isMovable)
                .setRotation(gd[1].rotation)
                .setOccupied(true, 0, 1)
                .build();
        Ground g3 = (new Ground.GroundBuilder(root))
                .setX(gd[2].location.x)
                .setY(gd[2].location.y)
                .setMovable(isMovable)
                .setRotation(gd[2].rotation)
                .setOccupied(true, 0, 0)
                .setOccupied(true, 1, 2)
                .build();
        Ground g4 = (new Ground.GroundBuilder(root))
                .setX(gd[3].location.x)
                .setY(gd[3].location.y)
                .setMovable(isMovable)
                .setRotation(gd[3].rotation)
                .setOccupied(true, 0, 0)
                .setOccupied(true, 2, 1)
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
                    .setOccupied(true, 0, 2)
                    .build();

        Ground g2 = (new Ground.GroundBuilder(root))
                .setX(340)
                .setY(160)
                .setMovable(isMovable)
                .setOccupied(true, 0, 1)
                .build();
        Ground g3 = (new Ground.GroundBuilder(root))
                .setX(100)
                .setY(340)
                .setMovable(isMovable)
                .setOccupied(true, 0, 0)
                .setOccupied(true, 1, 2)
                .build();
        Ground g4 = (new Ground.GroundBuilder(root))
                .setX(340)
                .setY(400)
                .setMovable(isMovable)
                .setOccupied(true, 0, 0)
                .setOccupied(true, 2, 1)
                .build();

        grounds[0] = g1;
        grounds[1] = g2;
        grounds[2] = g3;
        grounds[3] = g4;
        return grounds;
    }
}
