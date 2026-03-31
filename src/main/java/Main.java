import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    private static Main instance;
    private Stage stage;
    private ResourceBundle bundle;

    public static Main getInstance() { return instance; }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.stage = primaryStage;
        reloadView(new Locale("en", "US"));
        stage.show();
    }

    public void reloadView(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle("MessagesBundle", locale);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene.fxml"), bundle);
            Parent root = loader.load();
            stage.setTitle("Su Wai Phyoe - Shopping Cart");
            stage.setScene(new Scene(root, 400, 500));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResourceBundle getBundle() { return bundle; }
    public static void main(String[] args) { launch(args); }
}