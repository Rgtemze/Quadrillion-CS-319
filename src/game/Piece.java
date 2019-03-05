package game;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class Piece {

    private Point2D location;
    private ArrayList<Point> circleOffsets;
    private ArrayList<Circle> circles;
    static int RADIUS = 30;
    private int minX;
    private int minY;
    private Group root;
    Piece(Group root, int minX, int minY){
        circleOffsets = new ArrayList<>();
        circles = new ArrayList<>();
        this.root = root;

        this.minX = minX;
        this.minY = minY;

        Point a = new Point(0, 0);
        Point b = new Point(1, 0);
        Point c = new Point(0, 1);
        Point d = new Point(0, 2);

        circleOffsets.add(a);
        circleOffsets.add(b);
        circleOffsets.add(c);
        circleOffsets.add(d);
        location = new Point2D(50, 90);
    }

    void drawPiece(){
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
    private void removePiece(){
        for(Circle c: circles){
            root.getChildren().remove(c);
        }
        circles.clear();
    }
    private void rotate(){
        removePiece();

        for(Point point: circleOffsets){
            point.setLocation(point.getY(), -point.getX());
        }

        drawPiece();
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

                if( y < 0 || x < 0 || y >= 16 || x >= 16 || Level.board[y][x] == 0){
                    hasNotValid = true;
                }
            }
            location = new Point2D(event.getX(), event.getY());
            if(!hasNotValid) {
                System.out.println("found");
            }
        }
    }
}
