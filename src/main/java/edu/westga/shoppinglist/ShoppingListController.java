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
    private TextField itemQuantity;

    @FXML
    private Button addItem;

    @FXML
    private Button removeItem;

    @FXML
    private Button updateQuantity;

    @FXML
    private Label errorMessage;

    private Map<String, Integer> shoppingList;

    @FXML
    public void initialize() {
        this.addItem.setOnAction(e -> this.addItemToList());
        this.removeItem.setOnAction(e -> this.removeItemFromList());
        this.updateQuantity.setOnAction(e -> this.updateItemQuantity());
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

    public void removeItemFromList() {
        String selectedItem = this.shoppingListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            // Display error message
            this.errorMessage.setText("An item must be selected.");
        } else {
            // Remove item from the list
            String itemName = selectedItem.split(" - Quantity: ")[0];
            this.shoppingList.remove(itemName);
            this.shoppingListView.getItems().remove(selectedItem);
            // Clear error message
            this.errorMessage.setText("");
        }
    }

    public void updateItemQuantity() {
        String selectedItem = this.shoppingListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            // Display error message
            this.errorMessage.setText("An item must be selected.");
        } else {
            try {
                int quantity = Integer.parseInt(this.itemQuantity.getText().trim());
                if (quantity <= 0) {
                    // Display error message
                    this.errorMessage.setText("A positive quantity must be provided.");
                } else {
                    // Update item quantity in the list
                    String itemName = selectedItem.split(" - Quantity: ")[0];
                    this.shoppingList.put(itemName, quantity);
                    this.shoppingListView.getItems().set(this.shoppingListView.getItems().indexOf(selectedItem), itemName + " - Quantity: " + quantity);
                    // Clear error message
                    this.errorMessage.setText("");
                }
            } catch (NumberFormatException e) {
                // Display error message
                this.errorMessage.setText("A positive quantity must be provided.");
            }
        }
    }

    public ListView<String> getShoppingListView() {
        return this.shoppingListView;
    }

    public TextField getItemName() {
        return this.itemName;
    }

    public TextField getItemQuantity() {
        return this.itemQuantity;
    }

    public Label getErrorMessage() {
        return this.errorMessage;
    }
}