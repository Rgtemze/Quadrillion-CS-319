package core;

import interfaces.MoveObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class Piece extends Drawable {

    private ArrayList<Point> circleOffsets;
    private ArrayList<Point> initialCircleOffsets;
    private ArrayList<Circle> circles;

    private Level level;
    private boolean isEmbedded;
    private int numberOfMoves;
    private Paint color;

    private MoveObserver observer;
    private Point initialLocation;
    private KeyboardHandler kb;
    private static int pivot;

    private Piece(PieceBuilder builder) {
        this.circleOffsets = builder.circleOffsets;
        this.initialCircleOffsets = copyOffsetList(circleOffsets);
        this.root = builder.root;
        location.x = builder.x;
        location.y = builder.y;
        color = builder.color;
        initialLocation = location;
        circles = new ArrayList<>();
        isEmbedded = false;

        kb = new KeyboardHandler();
        root.setOnKeyPressed(kb);
    }

    public void setObserver(MoveObserver observer) {
        this.observer = observer;
    }

    private class KeyboardHandler implements EventHandler<KeyEvent>{

        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.S){
                pivot = Math.max(0, pivot - 1);
            } else if(event.getCode() == KeyCode.W){
                pivot = Math.min(3, pivot + 1);
            }
            level.adjustVisibility(pivot);

            // If piece is already embedded rotation and flip should be avoided.
            if(isEmbedded) return;

            if (event.getCode() == KeyCode.R) {
                rotate();
            } else if(event.getCode() == KeyCode.F){
                flip();
            }
        }
    }
    public void setLevel(Level level){
        this.level = level;
    }

    public static final double RADIUS = 30;
    public void setVisibility(boolean visible){
        for(Circle c: circles){
            c.setVisible(visible);
        }
    }

    @Override
    public void rotate() {
        for(Point point: circleOffsets){
            point.setLocation(point.getY(), -point.getX());
        }
        recalculatePoints();
    }

    @Override
    public void draw() {
        for(Point point: circleOffsets){
            Circle c = new Circle();
            c.setCenterX(location.getX() + point.getX() * RADIUS * 2);
            c.setCenterY(location.getY() + point.getY() * RADIUS * 2);
            c.setRadius(RADIUS);
            c.setFill(color);

            circles.add(c);
            root.getChildren().add(c);

            //if(point.x == 0 && point.y == 0) {
                c.setStroke(Paint.valueOf("black"));
                c.setOnMouseReleased(new DragDropped());
                c.setOnMouseDragged(new DragHandler());
            //}
        }
    }

    @Override
    public void remove() {
        for(Circle c: circles){
            root.getChildren().remove(c);
        }
        circles.clear();
    }

    @Override
    public void flip() {
        for(Point point: circleOffsets){
            point.setLocation(-point.getX(), point.getY());
        }
        recalculatePoints();
    }

    public boolean isEmbedded(){
        return isEmbedded;
    }

    private void recalculatePoints(){
        for(int i = 0; i < circleOffsets.size(); i++){
            Circle c = circles.get(i);
            Point point = circleOffsets.get(i);
            c.setCenterX(location.getX() + point.getX() * RADIUS * 2);
            c.setCenterY(location.getY() + point.getY() * RADIUS * 2);
        }
    }

    private class DragHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            boolean ejected = false;

            final Circle target = (Circle) event.getTarget();
            int targetX = (int) target.getCenterX();
            int targetY = (int) target.getCenterY();
            kb = new KeyboardHandler();
            root.setOnKeyPressed(kb);
            for (Circle c : circles) {

                c.setCenterX(c.getCenterX() + event.getX() - targetX);
                c.setCenterY(c.getCenterY() + event.getY() - targetY);
                System.out.println(event.getX() - target.getCenterX());
                System.out.println(event.getY() - target.getCenterY());
                if(isEmbedded){
                    int x = (int) Math.round((c.getCenterX() - RADIUS - level.getMinX()) / Ground.EDGE_LENGTH);
                    int y = (int) Math.round((c.getCenterY() - RADIUS -  level.getMinY()) / Ground.EDGE_LENGTH);
                    System.out.println("x: " + x + " y: " + y + " isEmbeded: " + isEmbedded);
                    level.setOccupation(y, x, 0);
                    ejected = true;
                }
            }
            System.out.println();
            //source.setFill(Paint.valueOf("red"));
            if(ejected){
                isEmbedded = false;
                //increaseNoOfMoves();
            }
            location = new Point((int) circles.get(0).getCenterX(), (int) circles.get(0).getCenterY());
        }
    }

    private class DragDropped implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if(isEmbedded) return;
            if(event.getButton() != MouseButton.PRIMARY) {return;}
            boolean hasNotValid = false;
            for (Circle c : circles) {
                int x = (int) Math.round((c.getCenterX() - RADIUS - level.getMinX()) / Ground.EDGE_LENGTH);
                int y = (int) Math.round((c.getCenterY() - RADIUS -  level.getMinY()) / Ground.EDGE_LENGTH);

                if( y < 0 || x < 0 || y >= 16 || x >= 16 || level.isOccupied(y,x)){
                    hasNotValid = true;
                }
            }
            if(!hasNotValid) {
                System.out.println("found");
                for (Circle c : circles) {
                    int x = (int) Math.round((c.getCenterX() - RADIUS - level.getMinX()) / Ground.EDGE_LENGTH);
                    int y = (int) Math.round((c.getCenterY() - RADIUS -  level.getMinY()) / Ground.EDGE_LENGTH);
                    level.setOccupation(y, x, 1);
                    c.setCenterX(x * Ground.EDGE_LENGTH + level.getMinX() + Ground.EDGE_LENGTH / 2);
                    c.setCenterY(y * Ground.EDGE_LENGTH + level.getMinY() + Ground.EDGE_LENGTH / 2);

                }
                isEmbedded = true;
                increaseNoOfMoves();
                location.x = (int) circles.get(0).getCenterX();
                location.y = (int) circles.get(0).getCenterY();
            } else {
                location = initialLocation;
                circleOffsets = copyOffsetList(initialCircleOffsets);
                recalculatePoints();
            }
            //level.printOccupation();
        }
    }

    private ArrayList<Point> copyOffsetList(ArrayList<Point> offset){
        ArrayList<Point> result = new ArrayList<>();

        for(Point p: offset){
            result.add(new Point(p.x, p.y));
        }
        return result;
    }

    private void increaseNoOfMoves(){
        numberOfMoves++;
        observer.notifyMoveChanged(numberOfMoves);
    }
    public static class PieceBuilder{

        private Paint color;
        private Group root;
        private ArrayList<Point> circleOffsets;
        private int x;
        private int y;

        public PieceBuilder(Group root){
            this.root = root;
            color = Paint.valueOf("black");
            circleOffsets = new ArrayList<>();
        }

        public PieceBuilder addOffset(int x, int y){
            circleOffsets.add(new Point(x, y));
            return this;
        }
        public PieceBuilder setX(int x){
            this.x = x;
            return this;
        }

        public PieceBuilder setY(int y){
            this.y = y;
            return this;
        }

        public PieceBuilder setColor(Paint color){
            this.color = color;
            return this;
        }

        public Piece build(){
            return new Piece(this);
        }
    }

}
