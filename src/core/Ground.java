package core;

import data.GroundData;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static core.Piece.RADIUS;

public class Ground extends Drawable{

    private int[][] frontBoard;
    private int[][] backBoard;
    private boolean isFront = true;
    public static final int EDGE_LENGTH = 2 * RADIUS;
    private final static int NUMBER_OF_EDGE = 4;
    private final static int WIDTH = EDGE_LENGTH * NUMBER_OF_EDGE;
    private final static int HEIGHT = EDGE_LENGTH * NUMBER_OF_EDGE;
    private ArrayList<Circle> circles;
    private boolean isMovable;
    private Rectangle rect;


    private GroundData result;

    private Ground(GroundBuilder builder) {
        location.x = builder.x;
        isMovable = builder.isMovable;
        location.y = builder.y;
        frontBoard = builder.frontBoard;
        backBoard = builder.backBoard;
        this.root = builder.root;
        circles = new ArrayList<>();
        rect = new Rectangle();
        result = new GroundData();
    }

    public int[][] getActiveBoard() {
        if( isFront ) return frontBoard;
        return backBoard;
    }

    public void flip() {
        for(int i = 0; i < NUMBER_OF_EDGE; i++){
            for (int j = 0; j < NUMBER_OF_EDGE; j++){
                backBoard[i][j] = frontBoard[NUMBER_OF_EDGE-i][j];
            }
        }
        isFront = !isFront;
    }

    @Override
    public void rotate() {
        remove();
        int[][] currentBoard = isFront ? frontBoard : backBoard;
        int t;
        for(int i = 0; i < NUMBER_OF_EDGE/2; i++){
            for(int j = i; j < NUMBER_OF_EDGE - i - 1; j++){
                t = currentBoard[i][j];
                currentBoard[i][j] = currentBoard[NUMBER_OF_EDGE-j-1][i];
                currentBoard[NUMBER_OF_EDGE-j-1][i] = currentBoard[NUMBER_OF_EDGE-i-1][NUMBER_OF_EDGE-j-1];
                currentBoard[NUMBER_OF_EDGE-i-1][NUMBER_OF_EDGE-j-1] = currentBoard[j][NUMBER_OF_EDGE-i-1];
                currentBoard[j][NUMBER_OF_EDGE-i-1] = t;
            }
        }
        draw();
        result.rotation = (result.rotation + 1) % 4;
    }

    @Override
    public void draw() {
        rect.setX(location.getX());
        rect.setY(location.getY());
        rect.setWidth(WIDTH);
        rect.setHeight(HEIGHT);
        rect.setStroke(Paint.valueOf("beige"));
        root.getChildren().add(rect);
        DragHandler handler = new DragHandler();
        //Draw circle slots
        for(int i = 0; i < NUMBER_OF_EDGE; i++){
            for(int j = 0; j < NUMBER_OF_EDGE; j++){
                Circle c = new Circle();
                c.setRadius(RADIUS);
                c.setFill((getActiveBoard()[i][j] == 1 ? Paint.valueOf("black") : Paint.valueOf("white")));
                c.setStroke(Paint.valueOf("white"));
                c.setCenterX(location.getX() + EDGE_LENGTH / 2.0 + EDGE_LENGTH * i);
                c.setCenterY(location.getY() + EDGE_LENGTH / 2.0 + EDGE_LENGTH * j);
                circles.add(c);
                root.getChildren().add(c);

                if(isMovable) {
                    c.setOnMouseDragged(handler);
                    c.setOnMouseClicked(event->{
                        if(event.getButton() == MouseButton.MIDDLE)
                        rotate();

                    });
                }
            }
        }

        if(isMovable) {
            rect.setOnMouseDragged(handler);
            rect.setOnMouseClicked(event->{
                if(event.getButton() == MouseButton.MIDDLE)
                    rotate();

            });
        }
    }

    @Override
    public void remove() {
        root.getChildren().remove(rect);
        for(Circle c: circles){
            root.getChildren().remove(c);
        }
    }

    private class DragHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            int previousX = location.x;
            int previousY = location.y;

            double bottom = location.getY() + HEIGHT;
            double top = location.getY();
            double left = location.getX();
            double right = location.getX() + WIDTH;
            if(event.getY() <= bottom && event.getY() >= bottom - EDGE_LENGTH){
                location.y += EDGE_LENGTH;
            }
            if(event.getY() >= top && event.getY() <= top + EDGE_LENGTH){
                location.y -= EDGE_LENGTH;
            }
            if(event.getX() >= left && event.getX() <= left + EDGE_LENGTH){
                location.x -= EDGE_LENGTH;
            }
            if(event.getX() <= right && event.getX() >= right - EDGE_LENGTH){
                location.x += EDGE_LENGTH;
            }
            rect.setX(location.getX());
            rect.setY(location.getY());

            for (Circle c : circles) {
                c.setCenterX(c.getCenterX() + location.getX() - previousX);
                c.setCenterY(c.getCenterY() + location.getY() - previousY);
            }
        }
    }
    public static class GroundBuilder{
        private boolean isMovable;
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

        public GroundBuilder setMovable(boolean isMovable){
            this.isMovable = isMovable;
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

    public GroundData getResult() {
        result.location = location;
        result.isFront = isFront;
        return result;
    }
}
