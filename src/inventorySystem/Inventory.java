/**
 * @author Steve Decker
 */

package inventorySystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class Inventory {
    private static int globalPartId = 1;
    public static int getGlobalPartId() {return globalPartId;}
    public static int getAndIncrementGlobalPartId(){
        return globalPartId++;
    }

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId){
        for (Part part : allParts){
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId){
        for (Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName){
        return new FilteredList<>(allParts, p -> p.getName().toLowerCase().contains(partName.toLowerCase()));
    }

    public static ObservableList<Product> lookupProduct(String productName){
        return new FilteredList<>(allProducts, p -> p.getName().toLowerCase().contains(productName.toLowerCase()));
    }

    public static void updatePart(int index, Part selectedPart){
        Part partToUpdate = allParts.get(index);
        partToUpdate.setId(selectedPart.getId());
        partToUpdate.setName(selectedPart.getName());
        partToUpdate.setPrice(selectedPart.getPrice());
        partToUpdate.setStock(selectedPart.getStock());
        partToUpdate.setMin(selectedPart.getMin());
        partToUpdate.setMax(selectedPart.getMax());

    }

    public static void updateProduct(int index, Product newProduct){
        Product productToUpdate = allProducts.get(index);
        productToUpdate.setId(newProduct.getId());
        productToUpdate.setName(newProduct.getName());
        productToUpdate.setPrice(newProduct.getPrice());
        productToUpdate.setStock(newProduct.getStock());
        productToUpdate.setMin(newProduct.getMin());
        productToUpdate.setMax(newProduct.getMax());
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
