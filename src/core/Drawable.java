package core;

import javafx.scene.Group;

import java.awt.*;

public abstract class Drawable {

    protected Point location;
    protected Group root;

    public Drawable(){
        location = new Point();
        root = new Group();
    }

    /*
       Rotates its components
    */
    public abstract void rotate();

    /*
        Draws its components
     */
    public abstract void draw();

    /*
        Removes components that are drawn
     */
    public abstract void remove();

    public abstract void flip();
}
