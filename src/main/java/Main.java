import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/login.fxml");
        if (fxmlLocation == null) {
            throw new RuntimeException("No se encontró el archivo /login.fxml en resources");
        }

        Parent root = FXMLLoader.load(fxmlLocation);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Aplicación Punto Limpio - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}