package database;

import data.GroundData;
import data.Record;
import data.User;
import ui.MainMenu;
import ui.Screen;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private String getSHA(String input)
    {

        try {

            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);

            return null;
        }
    }


    public boolean signIn(String userID, String pass){

        try {
            Statement st = null;
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM users WHERE NICKNAME = '%s' AND PASS = '%s'", userID, getSHA(pass)));
            boolean result;
            if(result = rs.next()){
                String nickName = rs.getString("NICKNAME");
                int hint = rs.getInt("HINT");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(String userID, String pass){
        try {
            Statement st = null;
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM users WHERE NICKNAME = '%s'", userID));
            if(rs.next()){
                return false;
            }
            st.execute(String.format("INSERT INTO users (NICKNAME,PASS,HINT) VALUES ('%s','%s',%d)", userID, getSHA(pass), 0));
        } catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public void updateHint(User user){
        executeSQL(String.format("UPDATE users " +
                "SET HINT = '%d' " +
                "WHERE NICKNAME = '%s'; ", user.getHint(), user.getNickName()));
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String dbUrl = "jdbc:mysql://localhost:8889/quadrillion?useLegacyDatetimeCode=false&serverTimezone=Turkey";
            conn = DriverManager.getConnection(dbUrl, "root", "root");
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

            if(rs.next()) {
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
                rec.setUserID(rs.getString("USER_NICK"));
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

    public int getLevelCount() {
        Statement st = null;
        try {
            st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS total FROM level");
            if(rs.next()){
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
