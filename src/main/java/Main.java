import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Map;

public class Main extends Application {

    private static Main instance;
    private Stage stage;
    private Map<String, String> strings;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.stage = primaryStage;

        reloadView(new Locale("en", "US"));
        stage.show();
    }

    public void reloadView(Locale locale) {
        try {
            LocalizationService localizationService = new LocalizationService();
            String language = locale.getLanguage();

            strings = localizationService.getStrings(language);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene.fxml"));
            Parent root = loader.load();

            // RTL support
            if ("ar".equals(language)) {
                root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            } else {
                root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            }

            ShoppingCartController controller = loader.getController();

            controller.setStrings(strings);
            controller.setSelectedLanguage(language);   // ✅ IMPORTANT FIX
            controller.applyLanguage();                 // ✅ refresh labels

            stage.setTitle("Shopping Cart");
            stage.setScene(new Scene(root, 400, 500));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getStrings() {
        return strings;
    }

    public static void main(String[] args) {
        launch(args);
    }
}