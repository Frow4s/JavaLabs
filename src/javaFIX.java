
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class javaFIX extends Application  {
    private Stage primaryStage;
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        LocaleManager.setCurrentLocale(LocaleManager.EN_LOCALE);
        createGUI(LocaleManager.getCurrentLocale());
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
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setResources(ResourceBundle.getBundle("Bundle.Locale",locale));
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        Parent root= fxmlLoader.load();
        primaryStage.getIcons().add(new Image("icon.jpg"));
        primaryStage.setTitle("Программа маленьких садоводов");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }

}
