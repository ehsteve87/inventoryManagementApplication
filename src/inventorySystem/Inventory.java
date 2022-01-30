/**
 * @author Steve Decker
 */

package inventorySystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * This class stores and modifies the inventory of Parts and Products
 */
public class Inventory {
    /**
     * A static variable for autoincrementing Part IDs
     */
    private static int globalPartId = 1;

    /**
     * @return the global part ID
     */
    public static int getGlobalPartId() {return globalPartId;}

    /**
     * Increments and returns the global part ID
     * @return the incremented global part ID
     */
    public static int getAndIncrementGlobalPartId(){
        return globalPartId++;
    }

    /**
     * List of all the parts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List of all the products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part
     * @param newPart the part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Adds a new product
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Looks up a part by Part ID
     * @param partId the ID to look up
     * @return
     */
    public static Part lookupPart(int partId){
        for (Part part : allParts){
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up a product by ID
     * @param productId the ID to look up
     * @return
     */
    public static Product lookupProduct(int productId){
        for (Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     * Filters the list of parts based on a lookup string
     * @param partName the lookup string
     * @return the list of parts that match the lookup string
     */
    public static ObservableList<Part> lookupPart(String partName){
        return new FilteredList<>(allParts, p -> p.getName().toLowerCase().contains(partName.toLowerCase()));
    }

    /**
     * Filters the list of products based on a lookup string
     * @param productName the lookup string
     * @return the list of products that match the lookup string
     */
    public static ObservableList<Product> lookupProduct(String productName){
        return new FilteredList<>(allProducts, p -> p.getName().toLowerCase().contains(productName.toLowerCase()));
    }

    /**
     * Updates a Part
     * @param index the index of the part to update
     * @param selectedPart the updated part
     */
    public static void updatePart(int index, Part selectedPart){
        Part partToUpdate = allParts.get(index);
        partToUpdate.setId(selectedPart.getId());
        partToUpdate.setName(selectedPart.getName());
        partToUpdate.setPrice(selectedPart.getPrice());
        partToUpdate.setStock(selectedPart.getStock());
        partToUpdate.setMin(selectedPart.getMin());
        partToUpdate.setMax(selectedPart.getMax());

    }

    /**
     * Updates a product
     * @param index the index of the product to update
     * @param newProduct the updated product
     */
    public static void updateProduct(int index, Product newProduct){
        Product productToUpdate = allProducts.get(index);
        productToUpdate.setId(newProduct.getId());
        productToUpdate.setName(newProduct.getName());
        productToUpdate.setPrice(newProduct.getPrice());
        productToUpdate.setStock(newProduct.getStock());
        productToUpdate.setMin(newProduct.getMin());
        productToUpdate.setMax(newProduct.getMax());
    }

    /**
     * Deletes a part
     * @param selectedPart the part to delete
     * @return true if a part is deleted, otherwise false
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a product
     * @param selectedProduct the product to delete
     * @return true if a product is deleted, otherwise false
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return the list of parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @return the list of products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
