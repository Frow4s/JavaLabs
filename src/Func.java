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
            transport.connect("СЮДА СВОЮ ПОЧТУ","СЮДА СВОЙ ПАРОЛЬ ОТ ПОЧТЫ");
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

}
