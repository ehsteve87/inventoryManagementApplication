package inventorySystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class controls the Add and Modify Parts forms
 */
public class ControllerPartForm {
    /**
     * If this is the modify form, this method adds the information from the part
     * to modify into the text fields. If it's the add form, it changes the
     * title to Add Part.
     */
    @FXML
    public void initialize() {
        if(ControllerMain.getWhichForm().equals("modify")) {
            Part partToModify = ControllerMain.getPartToModify();
            idPartForm.setText(String.valueOf(partToModify.getId()));
            namePartForm.setText(partToModify.getName());
            invPartForm.setText(String.valueOf(partToModify.getStock()));
            pricePartForm.setText(String.valueOf(partToModify.getPrice()));
            maxPartForm.setText(String.valueOf(partToModify.getMax()));
            minPartForm.setText(String.valueOf(partToModify.getMin()));
            if (partToModify instanceof Outsourced) {
                outsourcedButton.setSelected(true);
                manufactureIdPartForm.setText(((Outsourced) partToModify).getMachineId());
                manufactureIDLabel.setText("Company Name");
            }
            if (partToModify instanceof InHouse) {
                manufactureIdPartForm.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
            }
        }
        if(ControllerMain.getWhichForm().equals("add")){
            idPartForm.setText(((Integer) Inventory.getGlobalPartId()).toString());
            topLabel.setText("Add Part");
        }


    }

    /**
     * The label at the top of the window
     */
    @FXML
    private Label topLabel;
    /**
     * The In House radio button
     */
    @FXML
    private RadioButton inHouseButton;
    /**
     * The Outsourced radio button
     */
    @FXML
    private RadioButton outsourcedButton;
    /**
     * The label for machine ID or Company Name
     */
    @FXML
    private Label manufactureIDLabel;

    /**
     * Sets manufactureIDLabel to Machine ID or Company Name, depending
     * on which radio button is selected
     * @param e the event of clicking a radio button
     */
    @FXML
    public void radioButtonsClicked(ActionEvent e) {
        if (e.getSource().equals(inHouseButton)) {
            manufactureIDLabel.setText("Machine ID");
        }
        if (e.getSource().equals(outsourcedButton)) {
            manufactureIDLabel.setText("Company Name");
        }
    }

    /**
     * The Part ID field
     */
    @FXML
    private TextField idPartForm;
    /**
     * The Name field
     */
    @FXML
    private TextField namePartForm;
    /**
     * The Inventory field
     */
    @FXML
    private TextField invPartForm;
    /**
     * The Price field
     */
    @FXML
    private TextField pricePartForm;
    /**
     * The Max field
     */
    @FXML
    private TextField maxPartForm;
    /**
     * The Min field
     */
    @FXML
    private TextField minPartForm;
    /**
     * The Company Name / Machine ID field
     */
    @FXML
    private TextField manufactureIdPartForm;

    /**
     * This method returns to the main menu
     * @param e The event of clicking a button
     * @throws IOException
     */
    public void backToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method controls the action of the Save button
     * @param e The event of clicking the Save button
     * @throws IOException
     */
    public void saveButtonPartForm(ActionEvent e) throws IOException {

        String errorText = "";

        String name = namePartForm.getText();
        int stock = 0;
        double price = 0;
        int max = 0;
        int min = 0;

        if(namePartForm.getText().length() == 0
            || invPartForm.getText().length() == 0
            || pricePartForm.getText().length() == 0
            || maxPartForm.getText().length() == 0
            || minPartForm.getText().length() == 0
            || manufactureIdPartForm.getText().length() == 0){
            errorText += "Blank fields are not permitted. \n";
        }

        try{
            stock = Integer.parseInt(invPartForm.getText());
        } catch(NumberFormatException invEx){
            if(invPartForm.getText().length() != 0)
                errorText += "Inventory field must be an integer.\n";
        }

        try{
            price = Double.parseDouble(pricePartForm.getText());
        } catch (NumberFormatException priceEx){
            if(pricePartForm.getText().length() != 0)
                errorText += "Price field must be a valid number.\n";
        }

        try{
            max = Integer.parseInt(maxPartForm.getText());
        } catch (NumberFormatException maxEx){
            if(maxPartForm.getText().length() != 0)
                errorText += "Max field must be an integer.\n";
        }

        try{
            min = Integer.parseInt(minPartForm.getText());
        } catch (NumberFormatException minEx){
            if(minPartForm.getText().length() != 0)
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

        if(inHouseButton.isSelected()){
            try{
                if(Integer.parseInt(manufactureIdPartForm.getText()) < 0) {
                    errorText += "All numbers must be 0 or greater.\n";
                }
            } catch (NumberFormatException manEx){
                if(manufactureIdPartForm.getText().length() != 0) {
                    errorText += "Machine ID must be an integer.\n";
                }
            }
        }

        if((stock < 0 || price < 0 || min < 0 || max < 0) && !errorText.contains("0 or greater")){
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


        if(topLabel.getText().contains("Modify Part")) {
            Part partToModify = Inventory.lookupPart(Integer.parseInt(idPartForm.getText()));
            if (partToModify instanceof Outsourced && inHouseButton.isSelected()){
                int index = Inventory.getAllParts().indexOf(partToModify);
                InHouse nowAnInHouse = new InHouse(Integer.parseInt(idPartForm.getText()), name, price, stock, max, min, Integer.parseInt(manufactureIdPartForm.getText()));
                Inventory.getAllParts().set(index, nowAnInHouse);
                Inventory.getAllParts().remove(Inventory.getAllParts().size() - 1);
            }
            else if (partToModify instanceof InHouse && outsourcedButton.isSelected()){
                int index = Inventory.getAllParts().indexOf(partToModify);
                Outsourced nowAnOutsourced = new Outsourced(Integer.parseInt(idPartForm.getText()), name, price, stock, max, min, manufactureIdPartForm.getText());
                Inventory.getAllParts().set(index, nowAnOutsourced);
                Inventory.getAllParts().remove(Inventory.getAllParts().size() - 1);
            }
            else {
                partToModify.setName(name);
                partToModify.setStock(stock);
                partToModify.setPrice(price);
                partToModify.setMax(max);
                partToModify.setMin(min);
                if (partToModify instanceof Outsourced && outsourcedButton.isSelected()) {
                    ((Outsourced) partToModify).setMachineId(manufactureIdPartForm.getText());
                }
                if (partToModify instanceof InHouse && inHouseButton.isSelected()) {
                    ((InHouse) partToModify).setMachineId(Integer.parseInt(manufactureIdPartForm.getText()));
                }
            }

        }

        if(topLabel.getText().equals("Add Part")){
            if(inHouseButton.isSelected()){
                InHouse newPart = new InHouse(Inventory.getAndIncrementGlobalPartId(),name, price, stock, min, max, Integer.parseInt(manufactureIdPartForm.getText()));
            } else {
                Outsourced newPart = new Outsourced(Inventory.getAndIncrementGlobalPartId(), name, price, stock, min, max, manufactureIdPartForm.getText());
            }
        }

        backToMainMenu(e);
    }

    /**
     * This method holds the dialog boxes for this form.
     * @param type The type of dialog box
     */
    private void showDialogPartForm(int type) {

        Alert warning = new Alert(Alert.AlertType.WARNING);
        Alert error = new Alert(Alert.AlertType.ERROR);

        switch (type) {
            case 1:
                error.setTitle("Empty Field");
                warning.setHeaderText("Blank fields are not permitted.");
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
        }
    }
}


