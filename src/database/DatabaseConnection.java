package database;

import data.GroundData;
import data.Record;
import data.User;

import java.sql.*;
import java.util.Arrays;

public class DatabaseConnection {
    private static DatabaseConnection instance = new DatabaseConnection();
    private  Connection conn = null;
    private DatabaseConnection(){
        connectDatabase();
    }

    public static DatabaseConnection getInstance(){

        return instance;
    }
    public boolean signIn(String userID, String pass) throws SQLException {

        Statement st = null;
        st = conn.createStatement();

        return true;
    }

    public boolean createUser(String userID, String pass) throws SQLException {
        try {
            Statement st = null;
            st = conn.createStatement();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbUrl = "jdbc:mysql://localhost/quadrillion?useLegacyDatetimeCode=false&serverTimezone=Turkey";
            conn = DriverManager.getConnection(dbUrl, "root", "12345678q");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GroundData[] getLevel(int id){
        GroundData[] groundDatas = new GroundData[4];

        try {
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(String.format("SELECT * FROM level WHERE ID = '%d'", id));

            if(rs.next()){
                String[] rotations = rs.getString("ROTATIONS").split(";");
                String[] locations = rs.getString("LOCATIONS").split(";");
                String[] isfronts = rs.getString("ISFRONT").split(";");
                System.out.println(Arrays.toString(locations));
                for(int i = 0; i < 4; i++){
                    String[] loc = locations[i].split(",");
                    groundDatas[i] = new GroundData();
                    GroundData gd = groundDatas[i];
                    gd.location.x = Integer.parseInt(loc[0]);
                    gd.location.y = Integer.parseInt(loc[1]);
                    gd.rotation = Integer.parseInt(rotations[i]);
                    gd.isFront = isfronts[i].equals("1");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groundDatas;
    }

    public String executeSQL(String sql){
        try {
            Statement st = conn.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if(rs.next()){
                return rs.getString("ROTATIONS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        return null;
    }

    public Record[] getLeaderboard(int levelID){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM leaderboards " +
                    "WHERE LEVEL_ID = %d ORDER BY TOTAL_SCORE DESC", levelID));

            rs.last();
            int noOfRows = rs.getRow();
            rs.beforeFirst();
            Record[] lboard = new Record[noOfRows];
            int i = 0;
            while(rs.next()){
                Record rec = new Record();
                rec.setUserID(User.getInstance().getNickName());
                rec.setLevelID(levelID);
                rec.setMoves(rs.getInt("MOVES"));
                rec.setTimes(rs.getInt("TIME_ELAPSED"));
                rec.setScore(rs.getInt("TOTAL_SCORE"));
                lboard[i++] = rec;
            }
            return lboard;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
