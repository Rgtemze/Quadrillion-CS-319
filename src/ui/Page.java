package ui;

import javafx.scene.Group;

public abstract class Page {
    protected Group root;

    public Page(){
        root = new Group();
        prepareDesign();
    }

    public Group getRoot(){
        return root;
    }

    public abstract void prepareDesign();

}
