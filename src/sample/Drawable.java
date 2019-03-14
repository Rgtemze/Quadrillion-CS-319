package sample;

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

    /*
        Executes actions when object is dragged
     */
    public abstract  void onDrag();


    /*
        Executes actions when object is clicked
     */
    public abstract void onClick();

    public abstract void flip();
}
