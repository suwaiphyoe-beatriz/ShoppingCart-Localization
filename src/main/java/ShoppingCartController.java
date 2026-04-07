import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.*;

public class ShoppingCartController {

    @FXML private ComboBox<String> languageSelector;
    @FXML private VBox step1Box, step2Box;
    @FXML private TextField numItemsField, priceField, quantityField;
    @FXML private Label currentItemLabel, overallTotalLabel,selectLangLabel, enterItemsLabel, priceLabel, quantityLabel;
    @FXML private Button nextButton, confirmLangButton, startButton;

    private Map<String, String> strings;

    private int totalItemsToInput = 0;
    private int currentItemIndex = 1;
    private double runningTotal = 0;

    private List<CartItem> cartItems = new ArrayList<>();
    private String selectedLanguage = "en";

    public void setStrings(Map<String, String> strings) {
        this.strings = strings;
    }

    public void setSelectedLanguage(String lang) {
        this.selectedLanguage = lang;

        switch (lang) {
            case "fi" -> languageSelector.setValue("Finnish");
            case "sv" -> languageSelector.setValue("Swedish");
            case "ja" -> languageSelector.setValue("Japanese");
            case "ar" -> languageSelector.setValue("Arabic");
            default -> languageSelector.setValue("English");
        }
    }

    public void applyLanguage() {
        if (strings == null) return;

        // Labels
        selectLangLabel.setText(strings.getOrDefault("select_language", "Select Language"));
        enterItemsLabel.setText(strings.getOrDefault("enter_num_items", "Enter number of items"));
        priceLabel.setText(strings.getOrDefault("price", "Enter price"));
        quantityLabel.setText(strings.getOrDefault("quantity", "Enter quantity"));
        overallTotalLabel.setText(strings.getOrDefault("total_cost", "Total cost"));

        // Buttons
        confirmLangButton.setText(strings.getOrDefault("confirm_language", "Confirm Language"));
        startButton.setText(strings.getOrDefault("start", "Start"));

        // Logic-dependent button text
        if (currentItemIndex >= totalItemsToInput && totalItemsToInput > 0) {
            nextButton.setText(strings.getOrDefault("calculate", "Calculate"));
        } else {
            nextButton.setText(strings.getOrDefault("next_item", "Next Item"));
        }
    }

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll(
                "English", "Finnish", "Swedish", "Japanese", "Arabic"
        );
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
                cartItems.clear();
                updateItemLabel();

                if (totalItemsToInput == 1) {
                    nextButton.setText(strings.get("calculate"));
                } else {
                    nextButton.setText(strings.get("next_item"));
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

            double subtotal = price * qty;
            runningTotal += subtotal;

            cartItems.add(new CartItem(currentItemIndex, price, qty, subtotal));

            if (currentItemIndex < totalItemsToInput) {
                currentItemIndex++;
                updateItemLabel();

                priceField.clear();
                quantityField.clear();

                if (currentItemIndex == totalItemsToInput) {
                    nextButton.setText(strings.get("calculate"));
                }

            } else {
                String label = strings.get("total_cost");
                overallTotalLabel.setText(label + " " + String.format("%.2f", runningTotal));

                CartService cartService = new CartService();

                int cartId = cartService.saveCart(totalItemsToInput, runningTotal, selectedLanguage);

                for (CartItem item : cartItems) {
                    cartService.saveCartItem(
                            cartId,
                            item.getItemNumber(),
                            item.getPrice(),
                            item.getQuantity(),
                            item.getSubtotal()
                    );
                }

                nextButton.setDisable(true);
            }

        } catch (Exception e) {
            priceField.setText("Error");
        }
    }

    private void updateItemLabel() {
        String itemWord = strings.getOrDefault("item", "Item");
        currentItemLabel.setText(itemWord + " " + currentItemIndex + " / " + totalItemsToInput);
    }

    @FXML
    private void handleReset() {
        runningTotal = 0;
        currentItemIndex = 1;
        totalItemsToInput = 0;

        cartItems.clear();

        numItemsField.clear();
        priceField.clear();
        quantityField.clear();

        overallTotalLabel.setText(strings.get("total_cost"));

        nextButton.setDisable(false);
        nextButton.setText(strings.get("next_item"));

        step1Box.setVisible(true);
        step1Box.setManaged(true);

        step2Box.setVisible(false);
        step2Box.setManaged(false);
    }
}