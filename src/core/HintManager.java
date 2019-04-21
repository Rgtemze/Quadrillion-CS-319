package core;

import javafx.scene.control.Alert;
import javafx.scene.shape.Circle;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;
import java.util.ArrayList;

import static core.Piece.RADIUS;


public class HintManager {

    // All states of all pieces
    private PieceStates[] states;
    private int available;
    private int[][] combination;

    void printl( ArrayList<Point> circleOffsets ) {

        for (int j = 0; j < circleOffsets.size(); j++)
            System.out.printf( "(%d, %d) ", circleOffsets.get(j).x, circleOffsets.get(j).y );
        System.out.println( );
    }

    public class PieceStates {

        // All states of a particular piece
        private ArrayList< ArrayList<Point> > s;

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ArrayList<ArrayList<Point>> getS() {
            return s;
        }

        PieceStates() {
            s = new ArrayList<>();
        }

        private void align(ArrayList<Point> circleOffsets) {
            Collections.sort( circleOffsets, new SortbyCoordinate() );
            int x = circleOffsets.get(0).x;
            int y = circleOffsets.get(0).y;

            for( Point p: circleOffsets ) {
                p.setLocation( p.x - x, p.y - y );
            }
        }

        public void addit(ArrayList<Point> circleOffsets) {
            // ArrayList uses pass by reference, we need to make a copy
            ArrayList<Point> copy = new ArrayList<>();

            for(Point p: circleOffsets)
                copy.add( new Point( p.x, p.y ) );

            align( copy );

            s.add( copy );
        }

        public void print() {
            for(int i=0;i<s.size();i++) {
                for (int j = 0; j < s.get(i).size(); j++)
                    System.out.printf( "(%d, %d) ", s.get(i).get(j).x, s.get(i).get(j).y );
                System.out.println( );
            }
        }

        public void unique() {
            for(int i=0;i<s.size();i++) {
                boolean isUniqueElement = true;
                for(int j=0;j<i;j++)
                    if( s.get(i).equals( s.get( j ) ) ) {
                        isUniqueElement = false;
                        break;
                    }
                if( !isUniqueElement ) {
                    s.remove( i );
                    i--;
                }
            }
        }

        public int getSize() {
            return s.size();
        }

        public void sayDebug() {
            System.out.println( "Congragulations you reach PieceStates" );
        }

        public class SortbyCoordinate implements Comparator <Point> {
            public int compare(Point a, Point b)
            {
                if( a.x == b.x ) return a.y - b.y;
                return a.x - b.x;
            }
        }
    }

    private static HintManager instance = new HintManager();

    HintManager() {
        combination = new int[16][16];
        states = new PieceStates[12];

        for(int i=0;i<12;i++)
            states[i] = new PieceStates();
        available = ((1<<12)-1)*4;
    }

    public static HintManager getInstance(){
        return instance;
    }


    public PieceStates rand = new PieceStates();

    public void createPieceStates( Piece[] pieces ) {

        System.out.println( "In createPieceStates with Pieces: " + pieces.length + " " + states.length );

        for(int num=0;num<12;num++) {

            states[num].setId( pieces[num].getId() );

            //circleOffsets of a piece.
            ArrayList <Point> circleOffsets = new ArrayList<>();
            ArrayList <Point> piece = pieces[num].getInitialCircleOffsets();

            for(Point p: piece)
                circleOffsets.add( new Point( p.x, p.y ) );

            for(int i=0;i<4;i++) {
                rotate( circleOffsets );
                states[num].addit( circleOffsets );
            }

            flip( circleOffsets );

            for(int i=0;i<4;i++) {
                rotate( circleOffsets );
                states[num].addit( circleOffsets );
            }

            states[num].unique();

            //states[num].print();
            System.out.printf( "Piece Number %d is completed with size: %d\n", num, states[num].getSize() );
        }
    }

    private void flip(ArrayList<Point> circleOffsets) {
        for(Point point: circleOffsets){
            point.setLocation(-point.getX(), point.getY());
        }
    }

    private void rotate(ArrayList<Point> circleOffsets) {
        for(Point point: circleOffsets){
            point.setLocation(point.getY(), -point.getX());
        }
    }



    // ================================================ REAL HINT PART STARTS BELOW THERE

    public boolean showHint( int[][] board, Piece[] pieces ) {

        available = ((1<<12)-1)*4;
        System.out.println( "In ShowHint with Board: " );
        for(int i=0;i<16;i++) {
            for (int j = 0; j < 16; j++) {
                combination[i][j] = board[i][j];

                if( board[i][j] > 1 )
                    available &= ~(1<<board[i][j]);

                System.out.print(board[i][j] + " ");
            }
            System.out.println(  );
        }

        System.out.println("FULLY BRUTE FORCE HINT BEGIN, available: " + available );
        boolean sol = f( 0, 0 );
        System.out.println("FULLY BRUTE FORCE HINT END, Sol: " + sol);

        if( sol ) {
            int pieceNo = -1, I=0, J=0;
            for(int i=0;i<16;i++) {
                for (int j = 0; j < 16; j++) {
                    if( combination[i][j] != 0 && board[i][j] == 0 ) {
                        pieceNo = combination[i][j]-2;
                        I=i;
                        J=j;
                        break;
                    }
                }
                if( pieceNo != -1 ) break;
            }
            Piece p = pieces[pieceNo];
            System.out.printf("OK, pieceNO = %d\n",pieceNo);

            ArrayList<Point> newOffsets = new ArrayList<>();
            for(int i=0;i<16;i++) {
                for (int j = 0; j < 16; j++) {
                    if( combination[i][j] == pieceNo+2 ) {
                        newOffsets.add(new Point(j - J, i - I));
                        System.out.printf("WOW new Point of %d === (%d, %d)\n", pieceNo, i, j);
                        board[i][j] = combination[i][j];
                    }
                }
            }
            p.setCircleOffsets( newOffsets );
            p.setLocation( new Point(   J * Ground.EDGE_LENGTH + p.getLevel().getMinX() + Ground.EDGE_LENGTH / 2,
                                        I * Ground.EDGE_LENGTH + p.getLevel().getMinY() + Ground.EDGE_LENGTH / 2) );
            p.recalculatePoints();

        } else {
            Alert valid = new Alert(Alert.AlertType.INFORMATION);
            valid.setTitle("No Solution");
            valid.setHeaderText("Unfortunately there is no solution in that state!");
            valid.setContentText("Plesase remove some pieces and try again.");
            valid.showAndWait();
        }

        return sol;
    }

    boolean f( int X, int Y ) {
        if( Y == 16 ) {
            if( X == 15 ) return true;
            X++; Y = 0;
        }
        if( available == 0 ) {
            System.out.println( "=======================================================================================" );
            for(int i=0;i<16;i++) {
                for (int j = 0; j < 16; j++) {
                    System.out.print(combination[i][j] + " ");
                }
                System.out.println(  );
            }
            return true;
        }
        if( combination[X][Y] != 0 ) return f( X, Y+1 );
        for(int i=0;i<12;i++)
            if( ((1<<(i+2))&available) > 0 ) {
                ArrayList< ArrayList<Point> > s = states[i].getS();
                int x=0, y=0;
                for(ArrayList<Point> state: s) {
                    boolean ok = true;
                    for(Point p: state) {
                        x = X+p.x;
                        y = Y+p.y;
                        if( x < 0 || y < 0 || x >= 16 || y >= 16 || combination[x][y] != 0 ) {
                            ok = false;
                            break;
                        }
                    }
                    if( ok ) {
                        for(Point p: state) {
                            x = X+p.x;
                            y = Y+p.y;
                            combination[x][y] = i+2;
                        }
                        available ^= (1<<(i+2));
                        if( f( X, Y+1 ) ) return true;
                        available ^= (1<<(i+2));
                        for(Point p: state) {
                            x = X+p.x;
                            y = Y+p.y;
                            combination[x][y] = 0;
                        }
                    }
                }
            }

        return false;
    }


}
