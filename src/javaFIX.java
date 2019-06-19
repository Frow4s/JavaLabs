import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Observer;
import java.util.ResourceBundle;

public class javaFIX extends Application implements Observer {
    private Stage primaryStage;
    private Controller mainController;
    private FXMLLoader fxmlLoader;

    private AnchorPane currentRoot;
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        LocaleManager.setCurrentLocale(LocaleManager.EN_LOCALE);
        createGUI(LocaleManager.EN_LOCALE);
    }
    public static void javaFIX (String[]args)  {
        try {
            File file = new File("login.xml");
            file.delete();
        } catch (Exception e){

        }
        launch(args);
    }
    public void createGUI(Locale locale) throws Exception{
        currentRoot = loadFXML(locale);
        primaryStage.setScene(new Scene(currentRoot,600,400));
        primaryStage.show();
    }




    // загружает дерево компонентов и возвращает в виде VBox (корневой элемент в FXML)
    private AnchorPane loadFXML(Locale locale) {
        fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("Bundle.Locale",locale));

        AnchorPane node = null;

        try {
            node = fxmlLoader.load();

            mainController = fxmlLoader.getController();
            mainController.addObserver(this);
            primaryStage.getIcons().add(new Image("icon.jpg"));
            primaryStage.setTitle("Программа маленьких садоводов");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return node;
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        Locale locale=(Locale) arg;
        AnchorPane newNode = loadFXML(locale); // получить новое дерево компонетов с нужной локалью
        currentRoot.getChildren().setAll(newNode.getChildren());// заменить старые дочерник компонента на новые - с другой локалью
    }
}