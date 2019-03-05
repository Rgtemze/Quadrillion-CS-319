package sample;

public class FetchLevel {
    private int levelNo;
    private GameComponent gameComp;

    public FetchLevel( int levelNo ) {
        this.levelNo = levelNo;
    }

    public Level createLevel( ) {
        Level v = new Level( gameComp.getGrounds(), gameComp.getPieces() );
        return v;
    }

}
