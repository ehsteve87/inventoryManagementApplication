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

public class ControllerMain {
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


    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInventory;
    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableView productTable;
    @FXML
    private TableColumn productId;
    @FXML
    private TableColumn productName;
    @FXML
    private TableColumn productLevel;
    @FXML
    private TableColumn productPrice;


    @FXML
    TextField searchProductsField;
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

    @FXML
    TextField searchPartsField;
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
    private static String whichForm;
    public static String getWhichForm(){return whichForm;}
    private static Part partToModify;
    public static Part getPartToModify(){return partToModify;}
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



    @FXML
    public void addPartButtonMainForm(ActionEvent e) throws IOException {
        whichForm = "add";
        Parent root = FXMLLoader.load(getClass().getResource("partForm.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    private static Product productToModify;
    public static Product getProductToModify(){return productToModify;}
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
    @FXML
    public void addProductButtonMainForm(ActionEvent e) throws IOException {
        whichForm = "add";
        Parent root = FXMLLoader.load(getClass().getResource("productForm.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

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
                Inventory.getAllParts().remove(partToDelete);
            }
        }
    }

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
                Inventory.getAllProducts().remove(productToDelete);
            }
        }
        else {
            showDialogMainForm(1);
        }
    }

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

    private void showDialogMainForm(int type) {

        Alert warning = new Alert(Alert.AlertType.WARNING);
        Alert error = new Alert(Alert.AlertType.ERROR);

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
