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

public class ControllerProductForm {
    @FXML
    public void initialize() {
        searchPartsFieldProductForm.setText("");
        if(ControllerMain.getWhichForm().equals("modify")) {
            Product productToModify = ControllerMain.getProductToModify();
            idProductForm.setText(String.valueOf(productToModify.getId()));
            nameProductForm.setText(productToModify.getName());
            invProductForm.setText(String.valueOf(productToModify.getStock()));
            priceProductForm.setText(String.valueOf(productToModify.getPrice()));
            maxProductForm.setText(String.valueOf(productToModify.getMax()));
            minProductForm.setText(String.valueOf(productToModify.getMin()));
            populateAssociatedPartTable(ControllerMain.getProductToModify().getAssociatedParts());
        }
        if(ControllerMain.getWhichForm().equals("add")){
            topLabel.setText("Add Product");
            idProductForm.setText(((Integer) Product.getGlobalId()).toString());
            populateAssociatedPartTable(associatedPartsForNewProduct);
        }

        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    private TextField searchPartsFieldProductForm;

    @FXML
    private TableView partTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInventory;
    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TableView associatedPartTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartId;
    @FXML
    private TableColumn<Part, String> associatedPartName;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventory;
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;

    @FXML
    private Label topLabel;
    @FXML
    private TextField idProductForm;
    @FXML
    private TextField nameProductForm;
    @FXML
    private TextField invProductForm;
    @FXML
    private TextField priceProductForm;
    @FXML
    private TextField maxProductForm;
    @FXML
    private TextField minProductForm;

    @FXML
    private ObservableList<Part> associatedPartsForNewProduct = FXCollections.observableArrayList();

    private void populateAssociatedPartTable(ObservableList partList){
        associatedPartTable.setItems(partList);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void searchPartsProductForm() throws NumberFormatException{
        String searchText = searchPartsFieldProductForm.getText();
        try {
            int id = Integer.parseInt(searchText);
            ObservableList<Part> foundPart = FXCollections.observableArrayList();
            foundPart.add(Inventory.lookupPart(id));
            partTable.setItems(foundPart);
            System.out.println(foundPart.get(0).getName()); //If there are no results, this will throw a NullPointerException
        } catch (NumberFormatException e) {
            partTable.setItems(Inventory.lookupPart(searchText));
        } catch (NullPointerException f){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Parts Found");
            alert.showAndWait();
            initialize();
        }
        if(partTable.getItems().size() == 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Parts Found");
            alert.showAndWait();
            initialize();
        }
    }

    public void addAssociatedPartButton(){
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();
        if(topLabel.getText().contains("Modify Product")) {
            ControllerMain.getProductToModify().addAssociatedPart(selectedPart);
        }
        else {
            associatedPartsForNewProduct.add(selectedPart);
        }
    }

    public void removeAssociatedPartButton(){
        Part selectedPart = (Part) associatedPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No associated part selected");
            alert.showAndWait();
        }
        else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setHeaderText("Remove " + selectedPart.getName() + "?");
            Optional<ButtonType> answer = confirmation.showAndWait();
            if(answer.get() == ButtonType.OK){
                if(topLabel.getText().contains("Modify Product")){
                    ControllerMain.getProductToModify().deleteAssociatedPart(selectedPart);
                }
                else {
                    associatedPartsForNewProduct.remove(selectedPart);
                }
            }
        }
    }

    public void backToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void saveButtonProductForm (ActionEvent e) throws IOException {
        String errorText = "";

        String name = nameProductForm.getText();
        int stock = 0;
        double price = 0;
        int max = 0;
        int min = 0;

        if(nameProductForm.getText().length() == 0
                || invProductForm.getText().length() == 0
                || priceProductForm.getText().length() == 0
                || maxProductForm.getText().length() == 0
                || minProductForm.getText().length() == 0){
            errorText += "Blank fields are not permitted. \n";
        }

        try{
            stock = Integer.parseInt(invProductForm.getText());
        } catch(NumberFormatException invEx){
            if(invProductForm.getText().length() != 0)
                errorText += "Inventory field must be an integer.\n";
        }

        try{
            price = Double.parseDouble(priceProductForm.getText());
        } catch (NumberFormatException priceEx){
            if(priceProductForm.getText().length() != 0)
                errorText += "Price field must be a valid number.\n";
        }

        try{
            max = Integer.parseInt(maxProductForm.getText());
        } catch (NumberFormatException maxEx){
            if(maxProductForm.getText().length() != 0)
                errorText += "Max field must be an integer.\n";
        }

        try{
            min = Integer.parseInt(minProductForm.getText());
        } catch (NumberFormatException minEx){
            if(minProductForm.getText().length() != 0)
                errorText += "Min field must be an integer.\n";
        }

        try{
            if (min > max){
                errorText += "Max must be greater than or equal to Min.\n";
            } else if (stock < min && !errorText.contains("Inventory field must be an integer")){
                errorText += "Inventory cannot be lower than Min.\n";
            } else if (stock > max && !errorText.contains("Inventory field must be an integer")){
                errorText+= "Inventory cannot be Higher than Max.\n";
            }
        } catch (Exception minMaxEx) {
            System.out.println("Min Max error");
        }

        if(stock < 0 || price < 0 || min < 0 || max < 0){
            errorText += "All numbers must be 0 or greater.\n";
        }

        if (!errorText.equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Data Error");
            errorAlert.setHeaderText("The following problems were encountered:");
            errorAlert.setContentText(errorText);
            errorAlert.showAndWait();
            return;
        }

        if(topLabel.getText().contains("Modify Product")) {
            Product productToModify = Inventory.lookupProduct(Integer.parseInt(idProductForm.getText()));

            productToModify.setName(name);
            productToModify.setStock(stock);
            productToModify.setPrice(price);
            productToModify.setMax(max);
            productToModify.setMin(min);
        }

        if(topLabel.getText().equals("Add Product")){
            Product newProduct = new Product(name, price, stock, min, max);
            associatedPartsForNewProduct.forEach(newProduct::addAssociatedPart);

        }

        backToMainMenu(e);
    }

}
