import javax.security.auth.login.ConfigurationSpi;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DataBaseHandler  {
    protected String dbUser = "postgres";
    protected String dbPassword = "123";
    protected String dbUrl = "jdbc:postgresql://localhost:5432/tests";
    Connection dbconnection;

    public static final String USER_TABLE = "users";
    public static final String USERS_ID = "id_users";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_Email = "email";

    public Connection getDbconnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        dbconnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        return dbconnection;
    }
    public void signUpUser(User user){
        String insert = "INSERT INTO " + USER_TABLE + "(" +
                USERS_USERNAME + "," +
                USERS_PASSWORD + "," +
                USERS_Email + ")" +
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

        String select = "SELECT * FROM " + USER_TABLE + " WHERE " +
                USERS_USERNAME + "=? AND " + USERS_PASSWORD + "=?";
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

}
