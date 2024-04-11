package edu.westga.shoppinglist;

import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import javafx.scene.control.TextField;
import javafx.embed.swing.JFXPanel;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.testfx.util.WaitForAsyncUtils;
import org.junit.jupiter.api.BeforeAll;

public class ShoppingListControllerTest extends ApplicationTest {
    private ShoppingListController controller;

    @BeforeAll
    public static void setupClass() throws Exception {
        // This step will initialize the JavaFX toolkit
        JFXPanel jfxPanel = new JFXPanel();
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testGetItemName() {
        WaitForAsyncUtils.waitForFxEvents();
        TextField itemNameField = this.controller.getItemName();
        assertNotNull(itemNameField, "Item name field should not be null");
    }


    @Test
    public void testAddItemToListWithEmptyName() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#itemName").write("");
        clickOn("#addItem");
        assertTrue(this.controller.getShoppingListView().getItems().isEmpty());
        assertEquals("Item name must be provided.", this.controller.getErrorMessage().getText());
    }

    @Test
    public void testAddItemToListWithNonEmptyName() {
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#itemName").write("Milk");
        clickOn("#addItem");
        assertEquals(1, this.controller.getShoppingListView().getItems().size());
        assertEquals("Milk - Quantity: 0", this.controller.getShoppingListView().getItems().get(0));
        assertEquals("", this.controller.getErrorMessage().getText());
    }
    @Test
    public void testRemoveItemFromListWithItemSelected() {
        WaitForAsyncUtils.waitForFxEvents();
        // Add an item to the list first
        clickOn("#itemName").write("Milk");
        clickOn("#addItem");
        // Select the item in the list
        clickOn("Milk - Quantity: 0");
        // Remove the item from the list
        clickOn("#removeItem");
        assertTrue(this.controller.getShoppingListView().getItems().isEmpty());
        assertEquals("", this.controller.getErrorMessage().getText());
    }
    
    @Test
    public void testRemoveItemFromListWithoutItemSelected() {
        WaitForAsyncUtils.waitForFxEvents();
        // Try to remove an item from the list without selecting an item
        clickOn("#removeItem");
        assertTrue(this.controller.getShoppingListView().getItems().isEmpty());
        assertEquals("An item must be selected.", this.controller.getErrorMessage().getText());
    }

    @Test
    public void testUpdateItemQuantityWithItemSelected() {
        WaitForAsyncUtils.waitForFxEvents();
        // Add an item to the list first
        clickOn("#itemName").write("Milk");
        clickOn("#addItem");
        // Select the item in the list
        clickOn("Milk - Quantity: 0");
        // Update the quantity of the item
        clickOn("#itemQuantity").write("2");
        clickOn("#updateQuantity");
        assertEquals(1, this.controller.getShoppingListView().getItems().size());
        assertEquals("Milk - Quantity: 2", this.controller.getShoppingListView().getItems().get(0));
        assertEquals("", this.controller.getErrorMessage().getText());
    }
    
    @Test
    public void testUpdateItemQuantityWithoutItemSelected() {
        WaitForAsyncUtils.waitForFxEvents();
        // Try to update the quantity of an item without selecting an item
        clickOn("#itemQuantity").write("2");
        clickOn("#updateQuantity");
        assertTrue(this.controller.getShoppingListView().getItems().isEmpty());
        assertEquals("An item must be selected.", this.controller.getErrorMessage().getText());
    }
    
    @Test
    public void testUpdateItemQuantityWithInvalidQuantity() {
        WaitForAsyncUtils.waitForFxEvents();
        // Add an item to the list first
        clickOn("#itemName").write("Milk");
        clickOn("#addItem");
        // Select the item in the list
        clickOn("Milk - Quantity: 0");
        // Try to update the quantity of the item with an invalid quantity
        clickOn("#itemQuantity").write("-1");
        clickOn("#updateQuantity");
        assertEquals(1, this.controller.getShoppingListView().getItems().size());
        assertEquals("Milk - Quantity: 0", this.controller.getShoppingListView().getItems().get(0));
        assertEquals("A positive quantity must be provided.", this.controller.getErrorMessage().getText());
    }
}