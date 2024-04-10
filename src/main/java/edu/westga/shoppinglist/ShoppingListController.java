package edu.westga.shoppinglist;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ShoppingListController {
    @FXML
    private ListView<String> shoppingListView;

    @FXML
    private TextField itemName;

    @FXML
    private Button addItem;

    @FXML
    private Label errorMessage;

    private Map<String, Integer> shoppingList;

    @FXML
    public void initialize() {
        this.addItem.setOnAction(e -> this.addItemToList());
        this.shoppingList = new HashMap<>();
    }

    public void addItemToList() {
        String itemName = this.itemName.getText().trim();
        if (itemName.isEmpty()) {
            // Display error message
            this.errorMessage.setText("Item name must be provided.");
        } else {
            // Add item to the list
            this.shoppingList.putIfAbsent(itemName, 0);
            this.shoppingListView.getItems().add(itemName + " - Quantity: " + this.shoppingList.get(itemName));
            // Clear the text field
            this.itemName.clear();
            // Clear error message
            this.errorMessage.setText("");
        }
    }

    public ListView<String> getShoppingListView() {
        return this.shoppingListView;
    }

    public TextField getItemName() {
        return this.itemName;
    }

    public Label getErrorMessage() {
        return this.errorMessage;
    }
}