package sample;

import javafx.scene.Group;

public class FetchLevel {
    private int levelNo;
    private GameComponent gameComp;
    public FetchLevel( Group root, int levelNo ) {
        this.levelNo = levelNo;
        gameComp = new GameComponent(root);
    }

    public Level createLevel( ) {
        Level v = new Level( gameComp.getGrounds(), gameComp.getPieces() );
        return v;
    }

}
