import Objects.Gryadka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CommandsController implements Initializable {


    @FXML
    private Button PlayButton;

    @FXML
    private Button InfoButton;

    @FXML
    private Button ClearButton;

    @FXML
    private Button ShowButton;

    @FXML
    private Button DeleteLowerButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button AddButton;

    @FXML
    private Label Count3;

    @FXML
    private Label Name1;

    @FXML
    private Label Count1;

    @FXML
    private Label Name2;

    @FXML
    private Label Count2;

    @FXML
    private TextField AddNameText;

    @FXML
    private TextField AddCountText;

    @FXML
    private TextField DeleteNameText;

    @FXML
    private TextField DeleteCountText;

    @FXML
    private TextField DeleteLowerText;

    @FXML
    private ResourceBundle resourceBundle;

    @FXML
    public void initialize (URL location, ResourceBundle resource) {
        this.resourceBundle=resource;

        try {
            setUser();
        } catch (Exception e){
            System.out.println("Error with login");
        }
        PlayButton.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Cюжет");
            try {
                alert.setContentText(play(getUser()));
                alert.showAndWait();
            } catch (Exception e){
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Ошибка вывода сценария");
                alert.showAndWait();
            }
        });
        InfoButton.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Информация");
            alert.setContentText("Тип:ArrayDequeue"+"\n"+"Пользователь:"+getUser());
            alert.showAndWait();
        });
        ShowButton.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Состав колеекции");
            alert.setHeaderText(null);
            alert.setContentText(show(getUser()));
            alert.showAndWait();
        });
        ClearButton.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Удаление");
            alert.setContentText(clear(getUser()));
            alert.showAndWait();
        });
        AddButton.setOnAction(event -> {
            String name=AddNameText.getText().trim();
            String count=AddCountText.getText().trim();
            String addS="add "+name+" "+count;
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Добавление");
            alert.setContentText(add(addS,getUser()));
            AddNameText.clear();
            AddCountText.clear();
            alert.showAndWait();
        });
        DeleteButton.setOnAction(event -> {
            String name=DeleteNameText.getText().trim();
            String count=DeleteCountText.getText().trim();
            String delete="remove "+name+" "+count;
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Удаление");
            alert.setContentText(remove(delete,getUser()));
            DeleteNameText.clear();
            DeleteCountText.clear();
            alert.showAndWait();
        });
        DeleteLowerButton.setOnAction(event -> {
            String deleteLowerText="remove_lower "+DeleteLowerText.getText().trim();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Удаление");
            alert.setContentText(remove_lower(deleteLowerText,getUser()));
            DeleteLowerText.clear();
            alert.showAndWait();

        });
    }
    static String user;
    private void setUser() throws Exception{
        File file=new File("login.xml");
        Scanner scanner=new Scanner(file);
        this.user=scanner.nextLine();
        scanner.close();
    }
    private String getUser(){
        return this.user;
    }
    private static ConcurrentLinkedDeque<Gryadka> gryadkas = new ConcurrentLinkedDeque<>(); //Создаём потокобезопасную очередь

    public static String  play(String user) throws Exception {
        DataBaseHandler db=new DataBaseHandler();
        Output story = new Output();
        return(story.tellTheStory(db.gryadki(user)));

    }
    public String show(String user){
        String flag;
        DataBaseHandler dataBaseHandler= new DataBaseHandler();
        try{
            flag=dataBaseHandler.show(user);
        }
        catch (Exception e){
            flag="*Ошибка*";
        }
        return flag;
    }

    public String clear(String user){
        DataBaseHandler dataBaseHandler=new DataBaseHandler();
        try{
            return(dataBaseHandler.clear(user));
        }catch(Exception e){
            return("Error");
        }
    }

    public String add(String text, String user) {
        String[] arraytext = text.split(" ");
        String flag = "*Неправильный ввод*";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        if (arraytext.length == 3){
            String name = arraytext[1] + " " + arraytext[2];
            try {
                if (user == null) {
                    flag = "*Вы не вошли в систему*";
                    System.out.println(flag);
                    return flag;
                } else {
                    dataBaseHandler.add_gryadka(arraytext[1], arraytext[2], user);
                    flag = "*Грядка добавлена*";
                }
            } catch (Exception ex) {
                System.err.println("Неправильный ввод");
            }
        }
        else{
            flag = "*Неправильный ввод*";

        }
        return flag;
    }
    public String remove(String text,String user){
        String[] arraytext = text.split(" ");
        String flag = "*Грядка удалена*";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        try {
            ResultSet resultSet = dataBaseHandler.isConsist(arraytext[1],arraytext[2]);
            ResultSet resultSet2 = dataBaseHandler.isObjectUser(arraytext[1],arraytext[2], user);
            int row_counter = 0;
            int row_counter2 = 0;
            try {
                while (resultSet.next()) {
                    row_counter++;
                }
                while (resultSet2.next()) {
                    row_counter2++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (row_counter == 0){
                flag = "*Такого объекта не существует*";
            } else if (row_counter2 == 0){
                flag = "*Вы не имеете права изменять чужие объекты*";
            } else
                dataBaseHandler.remove(arraytext[1],arraytext[2],user);
        } catch (Exception e){
            flag="*Ошибка удаления грядки*";
        }

        return flag;
    }
    public String remove_lower(String line,String enter_user){
        String [] arraytext =line.split(" ");
        DataBaseHandler dataBaseHandler=new DataBaseHandler();
        try{
            return(dataBaseHandler.remove_lower(arraytext[1],enter_user));
        } catch (Exception e){
            return("Error");
        }
    }
}