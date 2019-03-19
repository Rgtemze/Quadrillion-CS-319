package data;

import java.awt.*;

public class GroundData {
    public int rotation;
    public boolean isFront;
    public Point location;

    public GroundData() {
        rotation = 0;
        isFront = true;
        location = new Point();
    }

    @Override
    public String toString() {
        return "GroundData{" +
                "rotation=" + rotation +
                "\n, isFront=" + isFront +
                "\n, location=" + location +
                "\n}";
    }
}
