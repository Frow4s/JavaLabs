import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class Register implements Initializable {

    @FXML
    private Button RegisterBackButton;

    @FXML
    private TextField RegisterLoginText;

    @FXML
    private TextField RegisterMailText;

    @FXML
    private Button RegisterButtom;

    private ResourceBundle resourceBundle;

    @FXML
    public void initialize (URL location, ResourceBundle resource) {
        this.resourceBundle=resource;

        RegisterBackButton.setOnAction(event -> {
            RegisterBackButton.getScene().getWindow().hide();
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("sample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root=loader.getRoot();
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        RegisterButtom.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            String login=RegisterLoginText.getText().trim();
            String mail=RegisterMailText.getText().trim();
            String LoginMail="login "+login+" "+mail;
            if(LoginMail.trim().equals("login")){
                alert.setContentText("Введите логин и почту");
                alert.showAndWait();
            } else {
                try {
                    alert.setContentText(sign_up(LoginMail));
                    alert.showAndWait();
                    RegisterLoginText.clear();
                    RegisterMailText.clear();
                } catch (Exception e){
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Error with registration  "+e);
                    alert.showAndWait();
                    RegisterLoginText.clear();
                    RegisterMailText.clear();
                }
            }
        });
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
            message.setFrom(new InternetAddress("olicras254@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));
            message.setSubject("Your new password");
            message.setText(Password);

            Transport transport = mailSession.getTransport();
            transport.connect("olicras254@gmail.com","5zcjrjggu33Jkv5");
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
            flag = "*Вы уже зарегистрированы, проверьте свою почту*";
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

