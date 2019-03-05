package sample;

import java.awt.*;
import java.util.ArrayList;

public class Piece extends Drawable {

    private ArrayList<Point> circleOffsets;

    public ArrayList<Point> getCircleOffsets() {
        return circleOffsets;
    }

    public void setCircleOffsets(ArrayList<Point> circleOffsets) {
        this.circleOffsets = circleOffsets;
    }
}
