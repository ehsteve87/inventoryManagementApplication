package inventorySystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 *This class controls the main screen of the application.
 *
 * RUNTIME ERROR
 * In its first iteration, a NumberFormatException occurred if
 * the user searched for a Part or Product by name instead of by
 * ID number. The corrections for these errors
 * can be found in the searchProducts() and searchParts() methods
 * of this class.
 */


public class ControllerMain {
    /**
     * Loads parts and products into their respective tables
     */
    public void initialize(){
        searchPartsField.setText("");
        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        searchProductsField.setText("");
        productTable.setItems(Inventory.getAllProducts());
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Table that shows the parts
     */
    @FXML
    private TableView<Part> partTable;
    /**
     * Column for part IDs
     */
    @FXML
    private TableColumn<Part, Integer> partId;
    /**
     * Column for part names
     */
    @FXML
    private TableColumn<Part, String> partName;
    /**
     * Column for part inventory levels
     */
    @FXML
    private TableColumn<Part, Integer> partInventory;
    /**
     * Column for part prices
     */
    @FXML
    private TableColumn<Part, Double> partPrice;

    /**
     * Table that shows the products
     */
    @FXML
    private TableView productTable;
    /**
     * Column for product IDs
     */
    @FXML
    private TableColumn productId;
    /**
     * Column for product names
     */
    @FXML
    private TableColumn productName;
    /**
     * Column for product inventory levels
     */
    @FXML
    private TableColumn productLevel;
    /**
     * Column for product prices
     */
    @FXML
    private TableColumn productPrice;

    /**
     * Search field for products table
     */
    @FXML
    TextField searchProductsField;

    /**
     * Updates Products table based on text typed into search field
     *
     * @throws NumberFormatException This exception occurs when a user types anything that isn't an integer
     */
    @FXML
    public void searchProducts() throws NumberFormatException{
        String searchText = searchProductsField.getText();
        try {
            int id = Integer.parseInt(searchText);
            ObservableList<Product> foundProduct = FXCollections.observableArrayList();
            foundProduct.add(Inventory.lookupProduct(id));
            productTable.setItems(foundProduct);
            System.out.println(foundProduct.get(0).getName()); //If there are no results, this will throw a NullPointerException
        } catch (NumberFormatException e) {
            productTable.setItems(Inventory.lookupProduct(searchText));
        } catch (NullPointerException f){
            showDialogMainForm(5);
            initialize();
        }
        if(productTable.getItems().size() == 0){
            showDialogMainForm(5);
            initialize();
        }
    }

    /**
     * Search field for Parts table
     */
    @FXML
    TextField searchPartsField;

    /**
     * Updates Parts table based on text typed into search field
     *
     * @throws NumberFormatException This exception occurs when a user types anything that isn't an integer
     */
    @FXML
    public void searchParts() throws NumberFormatException{
        String searchText = searchPartsField.getText();
        try {
            int id = Integer.parseInt(searchText);
            ObservableList<Part> foundPart = FXCollections.observableArrayList();
            foundPart.add(Inventory.lookupPart(id));
            partTable.setItems(foundPart);
            System.out.println(foundPart.get(0).getName()); //If there are no results, this will throw a NullPointerException
        } catch (NumberFormatException e) {
            partTable.setItems(Inventory.lookupPart(searchText));
        } catch (NullPointerException f){
            showDialogMainForm(4);
            initialize();
        }
        if(partTable.getItems().size() == 0){
            showDialogMainForm(4);
            initialize();
        }
    }

    /**
     * Static variable that keeps track of which form the user wants to access
     */
    private static String whichForm;

    /**
     * Getter for whichForm variable
     * @return the form the user wants to access
     */
    public static String getWhichForm(){return whichForm;}

    /**
     * Static variable that keeps track of which part the user wants to modify
     */
    private static Part partToModify;

    /**
     * Getter for partToModify variable
     * @return the part the user wants to modify
     */
    public static Part getPartToModify(){return partToModify;}

    /**
     * Controls the action of the Modify Part button
     * @param e event where button is clicked
     * @throws IOException
     */
    @FXML
    public void modifyPartButtonMainForm(ActionEvent e) throws IOException {
        partToModify = partTable.getSelectionModel().getSelectedItem();
        if(partToModify == null){
            showDialogMainForm(2);
        }
        else {
            whichForm = "modify";
            Parent root = FXMLLoader.load(getClass().getResource("partForm.fxml"));
            Scene scene = new Scene(root, 1000, 500);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


    /**
     * Controls the action of the Add Part button
     * @param e event where button is clicked
     * @throws IOException
     */
    @FXML
    public void addPartButtonMainForm(ActionEvent e) throws IOException {
        whichForm = "add";
        Parent root = FXMLLoader.load(getClass().getResource("partForm.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Static variable that keeps track of which product the user wants to modify
     */
    private static Product productToModify;

    /**
     * Getter for productToModify variable
     * @return the product to modify
     */
    public static Product getProductToModify(){return productToModify;}

    /**
     * Controls the action of the Modify Product button
     * @param e the event where the button is clicked
     * @throws IOException
     */
    @FXML
    public void modifyProductButtonMainForm(ActionEvent e) throws IOException {
        productToModify = (Product) productTable.getSelectionModel().getSelectedItem();
        if(productToModify == null){
            showDialogMainForm(2);
        }
        else {
            whichForm = "modify";
            Parent root = FXMLLoader.load(getClass().getResource("productForm.fxml"));
            Scene scene = new Scene(root, 1000, 500);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Controls the action of the Add Product button
     * @param e the event where the button is clicked
     * @throws IOException
     */
    @FXML
    public void addProductButtonMainForm(ActionEvent e) throws IOException {
        whichForm = "add";
        Parent root = FXMLLoader.load(getClass().getResource("productForm.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Controls the action of the Delete Part button
     */
    @FXML
    public void deletePart(){
        Part partToDelete = partTable.getSelectionModel().getSelectedItem();
        if(partToDelete == null){
            showDialogMainForm(3);
        } else {Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete Part");
            confirmation.setHeaderText("Are you sure you want to delete " + partToDelete.getName() + "?");
            Optional<ButtonType> answer = confirmation.showAndWait();
            if(answer.get() == ButtonType.OK) {
                boolean wasDeleted = Inventory.getAllParts().remove(partToDelete);
                if(!wasDeleted){
                    Alert notDeleted = new Alert(Alert.AlertType.WARNING);
                    notDeleted.setTitle("Delete Part");
                    notDeleted.setHeaderText("Part was not deleted");
                    notDeleted.showAndWait();
                }
            }
        }
    }

    /**
     * Controls the action of the Delete Product button
     */
    @FXML
    public void deleteProduct(){
        Product productToDelete = (Product) productTable.getSelectionModel().getSelectedItem();
        if(productToDelete == null){
            showDialogMainForm(2);
        }
        else if(productToDelete.getAssociatedParts().isEmpty()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete Product");
            confirmation.setHeaderText("Are you sure you want to delete " + productToDelete.getName() + "?");
            Optional<ButtonType> answer = confirmation.showAndWait();
            if(answer.get() == ButtonType.OK) {
                boolean wasDeleted = Inventory.getAllProducts().remove(productToDelete);
                if(!wasDeleted) {
                    Alert notDeleted = new Alert(Alert.AlertType.WARNING);
                    notDeleted.setTitle("Delete Product");
                    notDeleted.setHeaderText("Product was not deleted");
                    notDeleted.showAndWait();
                }
            }
        }
        else {
            showDialogMainForm(1);
        }
    }

    /**
     * Controls the action of the Exit button
     */
    public void exit(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Exit Application");
        confirmation.setHeaderText("Are you sure you want to exit the application?");
        Optional<ButtonType> answer = confirmation.showAndWait();
        if(answer.get() == ButtonType.OK) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }

    /**
     * Creates the various dialog boxes that can be shown from this form
     * @param type determines which dialog box is shown
     */
    private void showDialogMainForm(int type) {

        Alert warning = new Alert(Alert.AlertType.WARNING);

        switch (type) {
            case 1:
                warning.setTitle("Cannot Delete");
                warning.setHeaderText("Associated Parts Found");
                warning.setContentText("Products cannot be deleted until all associated parts have been removed.");
                warning.showAndWait();
                break;

            case 2:
                warning.setHeaderText("No Product Selected");
                warning.showAndWait();
                break;

            case 3:
                warning.setHeaderText("No Part Selected");
                warning.showAndWait();
                break;

            case 4:
                warning.setHeaderText("No Parts Found");
                warning.showAndWait();
                break;

            case 5:
                warning.setHeaderText("No Products Found");
                warning.showAndWait();
                break;
        }
    }

}
