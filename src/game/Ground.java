package game;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Ground {

    public int ground[][];
    public final static int EDGE_LENGTH = 2 * Piece.RADIUS;
    private final static int NUMBER_OF_EDGE = 4;
    private final static int WIDTH = EDGE_LENGTH * NUMBER_OF_EDGE;
    private final static int HEIGHT = EDGE_LENGTH * NUMBER_OF_EDGE;
    public Point location;
    public Ground(int x, int y){
        ground = new int[4][4];
        location = new Point(x, y);
    }

    public void drawGround(Group root){

        Rectangle rect = new Rectangle();
        rect.setX(location.getX());
        rect.setY(location.getY());
        rect.setWidth(WIDTH);
        rect.setHeight(HEIGHT);
        rect.setStroke(Paint.valueOf("beige"));
        //rect.setFill(Paint.valueOf("transparent"));
        /*
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double bottom = location.getY() + HEIGHT;
                double top = location.getY();
                double left = location.getX();
                double right = location.getX() + WIDTH;
                if(event.getY() <= bottom && event.getY() >= bottom - EDGE_LENGTH){
                    location = location.add(0, EDGE_LENGTH);
                }
                if(event.getY() >= top && event.getY() <= top + EDGE_LENGTH){
                    location = location.add(0, -EDGE_LENGTH);
                }
                if(event.getX() >= left && event.getX() <= left + EDGE_LENGTH){
                    location = location.add(-EDGE_LENGTH, 0);
                }
                if(event.getX() <= right && event.getX() >= right - EDGE_LENGTH){
                    location = location.add(EDGE_LENGTH, 0);
                }
                rect.setX(location.getX());
                rect.setY(location.getY());
            }
        });
        */
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
                    ground[i][j] = 1;
                }
                root.getChildren().add(c);
            }
        }


    }
}
