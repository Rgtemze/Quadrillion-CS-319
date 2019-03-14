package core;

import database.DatabaseConnection;
import javafx.scene.Group;

public class FetchLevel {
    private int levelNo;
    private GameComponent gameComp;
    public FetchLevel( Group root, int levelNo ) {
        this.levelNo = levelNo;
        gameComp = new GameComponent(root);
    }

    public Level createLevel( ) {
        /*DatabaseConnection conn = DatabaseConnection.getInstance();

        String columns = conn.executeSQL("SELECT * FROM combinations WHERE ID = '" + levelNo + "'", "ROTATIONS");
        String locations = conn.executeSQL("SELECT * FROM combinations WHERE ID = '" + levelNo + "'", "LOCATIONS");

        System.out.println(columns);
        System.out.println(locations);
        */
        gameComp.init();
        Level v = new Level( gameComp.getGrounds(), gameComp.getPieces() );
        return v;
    }

}
