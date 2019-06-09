import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Properties;

public class Func {
    String enter_user;


    public String getLogin (){
        return enter_user;
    }

    public String login (String text) {
        String[] arraytext = text.split(" ");
        String flag = "*Вход прошел успешно*";
        enter_user = arraytext[1];
        String Password = arraytext[2];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User user = new User();
        user.setUsername(enter_user);
        user.setPassword(Password);
        ResultSet result = dataBaseHandler.getUser(user);

        int counter = 0;

        try {
            while (result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (counter >= 1) {
            System.out.println(enter_user);
            return flag;
        } else {
            return "*Неправильный логин или пароль*";
        }

    }


    public String sign_up (String text) throws IOException, MessagingException {
        String[] arraytext = text.split(" ");
        String flag = "*Вы успешно зарегистрировались! Пароль отправлен вам на почту*";
        String Login = arraytext[1];
        String Password = getSaltString();
        String Email = arraytext[2];
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultname = dataBaseHandler.isUsername(Login);
        ResultSet resultemail = dataBaseHandler.isUserEmail(Email);
        int countername = 0;
        int counteremail = 0;
        try {
            while (resultname.next()) {
                countername++;
            }
            while (resultemail.next()) {
                counteremail++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (countername == 0 & counteremail == 0) {
            final Properties properties = new Properties();
            properties.load(new FileInputStream("src/mail.properties"));
            Session mailSession = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("nikisim2000@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));
            message.setSubject("Your new password");
            message.setText(Password);

            Transport transport = mailSession.getTransport();
            transport.connect("nikisim2000@gmail.com","ТВОЙ ПАРОЛЬ");
            try {
                transport.sendMessage(message,message.getAllRecipients());
                transport.close();
                User user = new User(Login, Password, Email);
                dataBaseHandler.signUpUser(user);
            } catch (MessagingException e) {
                flag = "*Неправильный формат почты*";
            }
        }
        else if (countername == 1 & counteremail == 0){
            flag = "*Пользователь с таким именем уже существует*";
        }
        else if ((countername == 1 || countername == 0) & counteremail == 1){
            flag = "*Вы уже зарегистрированы, проверьте свою почту, письмо с паролем могло попасть в спам*";
        }

        System.out.println(flag);
        return flag;
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public String add(String text, String user) {
        String[] arraytext = text.split(" ");
        String flag = "*Неправильный ввод*";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        if (arraytext.length == 3){
            String name = arraytext[1] + " " + arraytext[2];
            try {
            System.out.println(user);
                if (user == null) {
                    flag = "*Вы не вошли в систему*";
                    System.out.println(flag);
                    return flag;
                } else {
                    ResultSet resultSet = dataBaseHandler.getGryadka(user);
                    ResultSet resultSet2 = dataBaseHandler.already_have(arraytext[1], arraytext[2]);
                    int row_counter = 0;
                    int row_counter_ah = 0;
                    try {
                        while (resultSet.next()) {
                            row_counter++;
                        }
                        while (resultSet2.next()) {
                            row_counter_ah++;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println(row_counter);
                    System.out.println(row_counter_ah);
                    if (row_counter_ah == 0) {
                        dataBaseHandler.add_gryadka(arraytext[1], arraytext[2], user);
                        flag = "*Грядка добавлена*";
                    }
                    else if (row_counter_ah == 1){
                        flag = "*Такая грядка уже существует*";
                    }
                }
            } catch (Exception ex) {
                System.err.println("Неправильный ввод");
            }
        }
        else{
            flag = "*Неправильный ввод*";

        }
        System.out.println(flag);
        return flag;
    }

}
