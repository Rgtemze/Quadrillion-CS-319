package sample;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Queue;

public class Piece extends Drawable {

    private ArrayList<Point> circleOffsets;
    private ArrayList<Circle> circles;
    private double minX;
    private double minY;
    private Level level;

    private Piece(PieceBuilder builder) {
        this.circleOffsets = builder.circleOffsets;
        this.root = builder.root;
        circles = new ArrayList<>();
    }

    public void setLevel(Level level){
        this.level = level;
    }
    public ArrayList<Point> getCircleOffsets() {
        return circleOffsets;
    }

    public void setCircleOffsets(ArrayList<Point> circleOffsets) {
        this.circleOffsets = circleOffsets;
    }

    public static final int RADIUS = 30;
    @Override
    public void rotate() {
        remove();

        for(Point point: circleOffsets){
            point.setLocation(point.getY(), -point.getX());
        }

        draw();
    }

    @Override
    public void draw() {
        for(Point point: circleOffsets){
            Circle c = new Circle();
            c.setCenterX(location.getX() + point.getX() * RADIUS * 2);
            c.setCenterY(location.getY() + point.getY() * RADIUS * 2);
            c.setRadius(RADIUS);
            circles.add(c);
            root.getChildren().add(c);

            c.setOnMouseDragged(new DragHandler());
            c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    rotate();
                }
            });
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
    public void onDrag() {

    }

    @Override
    public void onClick() {

    }

    @Override
    public void flip() {

    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    private class DragHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            boolean hasNotValid = false;
            for (Circle c : circles) {
                c.setCenterX(c.getCenterX() + event.getX() - location.getX());
                c.setCenterY(c.getCenterY() + event.getY() - location.getY());
                int x = (int) Math.floor((c.getCenterX() - RADIUS - minX) / Ground.EDGE_LENGTH);
                int y = (int) Math.floor((c.getCenterY() - RADIUS -  minY) / Ground.EDGE_LENGTH);

                if( y < 0 || x < 0 || y >= 16 || x >= 16 || level.isOccupied(y,x)){
                    hasNotValid = true;
                }
            }
            location = new Point((int) event.getX(), (int) event.getY());
            if(!hasNotValid) {
                System.out.println("found");
            }
        }
    }

    public static class PieceBuilder{

        private Group root;
        private ArrayList<Point> circleOffsets;

        public PieceBuilder(Group root){
            this.root = root;
            circleOffsets = new ArrayList<>();
        }

        public PieceBuilder addOffset(int x, int y){
            circleOffsets.add(new Point(x, y));
            return this;
        }

        public Piece build(){
            return new Piece(this);
        }
    }
}
