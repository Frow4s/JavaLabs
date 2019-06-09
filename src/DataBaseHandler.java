import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


//Collable statement узнать!!!!

public class DataBaseHandler extends Configs {
    Connection dbconnection;

    public Connection getDbconnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        dbconnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return dbconnection;
    }

    public void signUpUser(User user){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + "," +
                Const.USERS_Email + ")" +
                "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(insert);
            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getEmail());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser (User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, user.getUsername());
            prSt.setString(2, user.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet isUsername (String user_name){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, user_name);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet isUserEmail(String email){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_Email + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, email);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void add_gryadka(String name, String count, String user_login){
        String insert = "INSERT INTO " + Const.GRYADKI_TABLE + "(" +
                Const.GRYADKI_CREATOR + "," +
                Const.GRYADKI_NAME + "," +
                Const.GRYADKI_COUNT + ")" +
                "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(insert);
            prSt.setString(1, user_login);
            prSt.setString(2, name);
            prSt.setString(3, count);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getGryadka(String user_login){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.GRYADKI_TABLE + " WHERE " +
                Const.GRYADKI_CREATOR + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, user_login);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet already_have(String gryadka, String count) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.GRYADKI_TABLE + " WHERE " +
                Const.GRYADKI_NAME + "=? AND " + Const.GRYADKI_COUNT + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, gryadka);
            prSt.setString(2, count);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
    public void remove(String name,String count,String user_login){
        String remove = "DELETE FROM " + Const.GRYADKI_TABLE +" WHERE "+Const.GRYADKI_NAME + "=? AND " + Const.GRYADKI_COUNT + "=? "+ "AND " + Const.GRYADKI_CREATOR + "=?";
        System.out.println(remove);
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(remove);
            prSt.setString(1, name);
            prSt.setString(2, count);
            prSt.setString(3, user_login);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
