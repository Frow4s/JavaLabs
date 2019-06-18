import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Controller implements Initializable {
    String enter_user;

    public String getLogin (){
        return enter_user;
    }

    @FXML
    private Label Hello;

    @FXML
    private Label Authorization;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField Login_field;

    @FXML
    private PasswordField Password_field;

    @FXML
    private Button SignInButton;


    @FXML
    private Button EN_button;

    @FXML
    private Button RU_button;

    @FXML
    private Button SV_button;

    @FXML
    private Button NL_button;

    private ResourceBundle resourceBundle;


    @FXML
    public void initialize (URL location,ResourceBundle resource) {
        this.resourceBundle=resource;
//        SignUpButton.getScene().getWindow().hide();
//        FXMLLoader loader=new FXMLLoader();
//
//        loader.setLocation(getClass().getResource("Register.fxml"));
//        loader.setResources(ResourceBundle.getBundle("Bundle.Locale",new Locale("en")));
//        try {
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Parent root=loader.getRoot();
//        Stage stage=new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();

        RU_button.setOnAction(event -> {
            RU_button.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("sample.fxml"));
        loader.setResources(ResourceBundle.getBundle("Bundle.Locale",LocaleManager.RU_LOCALE));
        LocaleManager.setCurrentLocale(LocaleManager.RU_LOCALE);
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

        SignInButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            String loginS = Login_field.getText().trim();
            String password = Password_field.getText().trim();
            String log_in = "login " + loginS + " " + password;
            if (loginS.equals("")||password.equals("")) {
                alert.setContentText("Введите логин и пароль");
                alert.showAndWait();
            } else {

            try {
                String result=login(log_in);
                if(result.equals("*Вход прошел успешно*")){
                    System.out.println(loginS);
                    FileWriter fileWriter=new FileWriter("login.xml");
                    fileWriter.write(loginS);
                    fileWriter.close();
                    SignInButton.getScene().getWindow().hide();
                    SignUpButton.getScene().getWindow().hide();
                    FXMLLoader loader=new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getResource("CommandsController.fxml"));
                        loader.setResources(ResourceBundle.getBundle("Bundle.Locale",new Locale("en")));
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root=loader.getRoot();
                    Stage stage=new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }else {
                    alert.setContentText(result);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Ошибка авторизации");
                alert.showAndWait();
                }
            }

        });
        SignUpButton.setOnAction(event -> {
            SignUpButton.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();

            loader.setLocation(getClass().getResource("Register.fxml"));
            loader.setResources(ResourceBundle.getBundle("Bundle.Locale",new Locale("en")));
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
            return flag;
        } else {
            return "*Неправильный логин или пароль*";
        }


    }
}
