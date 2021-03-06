import Objects.Gryadka;

import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentLinkedDeque;

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

    public void add_gryadka(String name, String count, String user_login, OffsetDateTime time){
        String insert = "INSERT INTO " + Const.GRYADKI_TABLE + "(" +
                Const.GRYADKI_CREATOR + "," +
                Const.GRYADKI_NAME + "," +
                "date"+","+
                Const.GRYADKI_COUNT + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(insert);
            prSt.setString(1, user_login);
            prSt.setString(2, name);
            prSt.setString(4, count);
            prSt.setString(3,time.toString());
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

    public ResultSet isObjectUser (String object, String count,String user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.GRYADKI_TABLE + " WHERE " +
                Const.GRYADKI_CREATOR + "=? AND " + Const.GRYADKI_NAME + "=? AND " + Const.GRYADKI_COUNT + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, user);
            prSt.setString(2, object);
            prSt.setString(3, count);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet isConsist (String object, String count) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.GRYADKI_TABLE + " WHERE " +
                Const.GRYADKI_NAME + "=? AND " + Const.GRYADKI_COUNT + "=?";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);
            prSt.setString(1, object);
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
        String remove = "DELETE FROM " + Const.GRYADKI_TABLE + " WHERE "+Const.GRYADKI_NAME + "=? AND " + Const.GRYADKI_COUNT + "=? "+ "AND " + Const.GRYADKI_CREATOR + "=?";
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

    public String show(String user_login){
        ZoneId zoneId=ZoneId.systemDefault();
        if(LocaleManager.getCurrentLocale().equals(LocaleManager.EN_LOCALE)){
            zoneId=ZoneId.of("Australia/Sydney");
        }
        if(LocaleManager.getCurrentLocale().equals(LocaleManager.RU_LOCALE)){
            zoneId=ZoneId.of("Europe/Moscow");
        }
        if(LocaleManager.getCurrentLocale().equals(LocaleManager.SV_LOCALE)){
            zoneId=ZoneId.of("Europe/Stockholm");
        }
        if(LocaleManager.getCurrentLocale().equals(LocaleManager.NL_LOCALE)){
            zoneId=ZoneId.of("Europe/Amsterdam");
        }
        String collection="";
        String show="SELECT * FROM " + Const.GRYADKI_TABLE;
        try {
            PreparedStatement prSt=getDbconnection().prepareStatement(show);
            ResultSet res = prSt.executeQuery();
            while (res.next()){
                if(!res.isLast()) {
                    String str = res.getString("gryadka") + " " + res.getString("number")+" "+OffsetDateTime.parse(res.getString("date")).toZonedDateTime().withZoneSameInstant(zoneId).toLocalDateTime();
                    collection += str + "\n";
                } else{
                    collection+=res.getString("gryadka") + " " + res.getString("number")+" "+OffsetDateTime.parse(res.getString("date")).toZonedDateTime().withZoneSameInstant(zoneId).toLocalDateTime();
                }
            }

       }catch (Exception e){
            return ("Ошибка вывода коллекции"+" "+e);
        }
        if(collection.equals("")){
            return("Коллекция пуста");
        }else{
            return collection;
        }
    }
    public String clear(String user_login){
        String clear=" DELETE FROM "+Const.GRYADKI_TABLE+" WHERE "+Const.GRYADKI_CREATOR+"=?";
        try{
            PreparedStatement prSt=getDbconnection().prepareStatement(clear);
            prSt.setString(1,user_login);
            prSt.executeUpdate();
        }catch (Exception e){
            return("Ошибка удаления коллекции");
        }
     return("Коллекция очищена");
    }

    public String remove_lower(String count,String user_login){
        String remove_lower="DELETE FROM " + Const.GRYADKI_TABLE+" WHERE ("+Const.GRYADKI_COUNT+"<?) AND ("+Const.GRYADKI_CREATOR+"=?)";
        try{
            PreparedStatement prSt=getDbconnection().prepareStatement(remove_lower);
            prSt.setString(1,count);
            prSt.setString(2,user_login);
            System.out.println(prSt.toString());
            prSt.executeUpdate();
        } catch (Exception e){
            return("*Ошибка удаления*"+"\n"+e);
        }
        return("*Элементы удалены*");
    }

    public ConcurrentLinkedDeque gryadki(String user_login){
        ConcurrentLinkedDeque<Gryadka> gryadkas = new ConcurrentLinkedDeque<>();
        String collection="";
        String show="SELECT * FROM " + Const.GRYADKI_TABLE+" WHERE "+Const.GRYADKI_CREATOR+"=?";
        try{
            PreparedStatement prSt=getDbconnection().prepareStatement(show);
            prSt.setString(1,user_login);
            ResultSet res=prSt.executeQuery();
            while(res.next()){
                gryadkas.addLast(new Gryadka(Integer.parseInt(res.getString("number")),res.getString("gryadka")));
            }
        } catch (Exception e){
            System.out.println("*Ошибка грядок*  "+e);
        }
        return gryadkas;
    }
}
