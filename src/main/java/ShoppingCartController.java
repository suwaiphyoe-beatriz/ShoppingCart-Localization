import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.*;

public class ShoppingCartController {
    @FXML private ComboBox<String> languageSelector;
    @FXML private VBox step1Box, step2Box;
    @FXML private TextField numItemsField, priceField, quantityField;
    @FXML private Label currentItemLabel, overallTotalLabel;
    @FXML private Button nextButton;

    private int totalItemsToInput = 0;
    private int currentItemIndex = 1;
    private double runningTotal = 0;

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("English", "Finnish", "Swedish", "Japanese", "Arabic");

        String currentLang = Main.getInstance().getBundle().getLocale().getDisplayLanguage(Locale.ENGLISH);
        if (languageSelector.getItems().contains(currentLang)) {
            languageSelector.setValue(currentLang);
        } else {
            languageSelector.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleLanguageChange() {
        String selected = languageSelector.getValue();
        if (selected == null) return;

        Locale locale = switch (selected) {
            case "Finnish" -> new Locale("fi", "FI");
            case "Swedish" -> new Locale("sv", "SE");
            case "Japanese" -> new Locale("ja", "JP");
            case "Arabic" -> new Locale("ar", "AR");
            default -> new Locale("en", "US");
        };
        Main.getInstance().reloadView(locale);
    }

    @FXML
    private void handleStartInput() {
        try {
            totalItemsToInput = Integer.parseInt(numItemsField.getText());
            if (totalItemsToInput > 0) {
                step1Box.setVisible(false);
                step1Box.setManaged(false);
                step2Box.setVisible(true);
                step2Box.setManaged(true);

                currentItemIndex = 1;
                runningTotal = 0;
                updateItemLabel();

                if (totalItemsToInput == 1) {
                    nextButton.setText(Main.getInstance().getBundle().getString("calculate"));
                } else {
                    nextButton.setText("Next Item");
                }
            }
        } catch (Exception e) {
            numItemsField.setText("Error");
        }
    }

    @FXML
    private void handleNextItem() {
        try {
            double price = Double.parseDouble(priceField.getText());
            int qty = Integer.parseInt(quantityField.getText());
            runningTotal += (price * qty);

            if (currentItemIndex < totalItemsToInput) {
                currentItemIndex++;
                updateItemLabel();
                priceField.clear();
                quantityField.clear();

                if (currentItemIndex == totalItemsToInput) {
                    nextButton.setText(Main.getInstance().getBundle().getString("calculate"));
                }
            } else {
                String label = Main.getInstance().getBundle().getString("total_cost");
                overallTotalLabel.setText(label + " " + String.format("%.2f", runningTotal));
                nextButton.setDisable(true);
            }
        } catch (Exception e) {
            priceField.setText("Error");
        }
    }

    private void updateItemLabel() {
        currentItemLabel.setText("Item " + currentItemIndex + " / " + totalItemsToInput);
    }

    @FXML
    private void handleReset() {
        runningTotal = 0;
        currentItemIndex = 1;
        totalItemsToInput = 0;
        numItemsField.clear();
        priceField.clear();
        quantityField.clear();
        overallTotalLabel.setText(Main.getInstance().getBundle().getString("total_cost"));
        nextButton.setDisable(false);
        nextButton.setText("Next Item");
        step1Box.setVisible(true);
        step1Box.setManaged(true);
        step2Box.setVisible(false);
        step2Box.setManaged(false);
    }
}