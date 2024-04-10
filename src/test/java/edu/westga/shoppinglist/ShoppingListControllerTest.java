package edu.westga.shoppinglist;

import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.junit.jupiter.api.Test;
import javafx.scene.control.TextField;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ShoppingListControllerTest extends ApplicationTest {
    private ShoppingListController controller;

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testGetItemName() {
        TextField itemNameField = this.controller.getItemName();
        assertNotNull(itemNameField, "Item name field should not be null");
    }

    @Test
    public void testAddItemToListWithEmptyName() {
        clickOn("#itemName").write("");
        clickOn("#addItem");
        assertTrue(this.controller.getShoppingListView().getItems().isEmpty());
        assertEquals("Item name must be provided.", this.controller.getErrorMessage().getText());
    }

    @Test
    public void testAddItemToListWithNonEmptyName() {
        clickOn("#itemName").write("Milk");
        clickOn("#addItem");
        assertEquals(1, this.controller.getShoppingListView().getItems().size());
        assertEquals("Milk - Quantity: 0", this.controller.getShoppingListView().getItems().get(0));
        assertEquals("", this.controller.getErrorMessage().getText());
    }
}