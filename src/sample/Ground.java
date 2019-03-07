package sample;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ground extends Drawable{

    private int[][] frontBoard;
    private int[][] backBoard;
    private boolean isFront = true;
    public static final int EDGE_LENGTH = 2 * Piece.RADIUS;
    private final static int NUMBER_OF_EDGE = 4;
    private final static int WIDTH = EDGE_LENGTH * NUMBER_OF_EDGE;
    private final static int HEIGHT = EDGE_LENGTH * NUMBER_OF_EDGE;

    public Ground(Group root, int x, int y) {
        location.x = x;
        location.y = y;
        this.root = root;
    }

    public int[][] getActiveBoard() {
        if( isFront ) return frontBoard;
        return backBoard;
    }

    public void flip() {
        isFront = !isFront;
    }

    @Override
    public void rotate() {

    }

    @Override
    public void draw() {
        Rectangle rect = new Rectangle();
        rect.setX(location.getX());
        rect.setY(location.getY());
        rect.setWidth(WIDTH);
        rect.setHeight(HEIGHT);
        rect.setStroke(Paint.valueOf("beige"));
        root.getChildren().add(rect);

        //Draw circle slots
        for(int i = 0; i < NUMBER_OF_EDGE; i++){
            for(int j = 0; j < NUMBER_OF_EDGE; j++){

                Circle c = new Circle();
                c.setRadius(Piece.RADIUS);
                c.setCenterX(location.getX() + EDGE_LENGTH / 2.0 + EDGE_LENGTH * i);
                c.setCenterY(location.getY() + EDGE_LENGTH / 2.0 + EDGE_LENGTH * j);
                if(i == j){
                    c.setFill(Paint.valueOf("black"));
                    c.setStroke(Paint.valueOf("white"));
                } else {
                    c.setFill(Paint.valueOf("white"));
                    getActiveBoard()[i][j] = 1;
                }
                root.getChildren().add(c);
            }
        }
    }

    @Override
    public void remove() {

    }

    @Override
    public void onDrag() {

    }

    @Override
    public void onClick() {

    }
}
