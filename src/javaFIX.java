
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class javaFIX extends Application {
    public void start(Stage primaryStage) throws Exception{
        Parent root= FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.getIcons().add(new Image("icon.jpg"));
        primaryStage.setTitle("Программа маленьких садоводов");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }
    public static void javaFIX (String[]args)  {
        try {
            File file = new File("login.xml");
            file.delete();
        } catch (Exception e){

        }
        launch(args);
    }
}
