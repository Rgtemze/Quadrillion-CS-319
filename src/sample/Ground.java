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

    private Ground(GroundBuilder builder) {
        location.x = builder.x;
        location.y = builder.y;
        frontBoard = builder.frontBoard;
        backBoard = builder.backBoard;
        this.root = builder.root;
    }

    public int[][] getActiveBoard() {
        if( isFront ) return frontBoard;
        return backBoard;
    }

    public void flipBoard() {
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
                c.setFill((getActiveBoard()[i][j] == 1 ? Paint.valueOf("black") : Paint.valueOf("white")));
                c.setStroke(Paint.valueOf("white"));
                c.setCenterX(location.getX() + EDGE_LENGTH / 2.0 + EDGE_LENGTH * i);
                c.setCenterY(location.getY() + EDGE_LENGTH / 2.0 + EDGE_LENGTH * j);
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

    public static class GroundBuilder{
        private Group root;
        private int x;
        private int y;
        private int[][] frontBoard;
        private int[][] backBoard;
        public GroundBuilder(Group root){
            this.root = root;
            frontBoard = new int[4][4];
            backBoard = new int[4][4];
        }

        public GroundBuilder setX(int x){
            this.x = x;
            return this;
        }

        public GroundBuilder setY(int y){
            this.y = y;
            return this;
        }

        public GroundBuilder setOccupied(boolean isFront, int x, int y){

            if(isFront){
                frontBoard[x][y] = 1;
            } else {
                backBoard[x][y] = 1;
            }
            return this;
        }

        public Ground build(){
            return new Ground(this);
        }


    }
}
