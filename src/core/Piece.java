package core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class Piece extends Drawable {

    private ArrayList<Point> circleOffsets;
    private ArrayList<Circle> circles;

    private Level level;
    private boolean isEmbedded;
    private int numberOfMoves;
    private Paint color;

    private Point initialLocation;

    private Piece(PieceBuilder builder) {
        this.circleOffsets = builder.circleOffsets;
        this.root = builder.root;
        location.x = builder.x;
        location.y = builder.y;
        color = builder.color;
        initialLocation = location;
        circles = new ArrayList<>();
        isEmbedded = false;

    }

    public void setLevel(Level level){
        this.level = level;
    }

    public static final int RADIUS = 30;
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

            c.setOnMouseReleased(new DragDropped());
            c.setOnMouseDragged(new DragHandler());
            c.setOnMouseClicked(new ClickHandler());
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

    }

    private class ClickHandler implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.MIDDLE) {
                rotate();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                //flip();
                rotate();
            }
        }
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
            for (Circle c : circles) {
                c.setCenterX(c.getCenterX() + event.getX() - location.getX());
                c.setCenterY(c.getCenterY() + event.getY() - location.getY());
                int x = (int) Math.round((c.getCenterX() - RADIUS - level.getMinX()) / Ground.EDGE_LENGTH);
                int y = (int) Math.round((c.getCenterY() - RADIUS -  level.getMinY()) / Ground.EDGE_LENGTH);
                //System.out.println("X: " + x + ", Y: " + y);
                if(isEmbedded){
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
            location = new Point((int) event.getX(), (int) event.getY());
        }
    }

    private class DragDropped implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
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
                recalculatePoints();
            }
            //level.printOccupation();
        }
    }

    private void increaseNoOfMoves(){
        numberOfMoves++;
        LevelManager.getInstance().incrementMoves();
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
