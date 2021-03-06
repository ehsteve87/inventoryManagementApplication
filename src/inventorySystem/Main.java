package inventorySystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class provides a starting point for the application.
 *
 * FUTURE ENHANCEMENT
 * A useful feature for a future version of this software would be to add
 * controls so users can easily increment and decrement the inventory levels
 * of Parts and Products.
 *
 */


public class Main extends Application{

    /**
     * Creates FXML stage and loads the main form as a scene.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }

    /**
     *Creates starting data and launches the application.
     *
     * @param args
     */
    //JavaDoc folder is located in the root directory of the application (Software I/javadoc)
    public static void main(String[] args) {

        Outsourced crown = new Outsourced(Inventory.getAndIncrementGlobalPartId(),"Crown", 2.98, 10, 1, 20, "CostumeCo");
        Outsourced wand = new Outsourced(Inventory.getAndIncrementGlobalPartId(), "Wand", 0.45, 12, 1, 100, "CostumeCo");
        Outsourced butterflyWings = new Outsourced(Inventory.getAndIncrementGlobalPartId(), "Butterfly Wings", 1.00, 23,1,100,"CostumeCo");
        Outsourced glitteryShoes = new Outsourced(Inventory.getAndIncrementGlobalPartId(), "Glittery Shoes", 3.00, 25, 1, 100, "CostumeCo");
        InHouse blueDress = new InHouse(Inventory.getAndIncrementGlobalPartId(), "Blue Dress", 8.50, 17, 1, 100, 246);
        InHouse greenDress = new InHouse(Inventory.getAndIncrementGlobalPartId(), "Green Dress", 8.80, 12, 1, 100, 246);
        Product fairyCostume = new Product("Fairy Costume", 22.50, 4, 1, 10);
        Product princessCostume = new Product("Princess Costume", 25.50, 12, 1, 20);
        fairyCostume.addAssociatedPart(wand, butterflyWings);
        princessCostume.addAssociatedPart(crown, blueDress, glitteryShoes);


        launch(args);

    }

}
