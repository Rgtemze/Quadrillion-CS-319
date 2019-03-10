package sample;

import java.sql.*;
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

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM combinations");



            while(rs.next()){


                System.out.println((rs.getString("ROTATIONS")));


            }

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

    public ResultSet executeSQL(String sql){

        ResultSet rs = null;


        return null;
    }
}
