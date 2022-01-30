package inventorySystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

/**
 * This class models products
 */
public class Product {
    /**
     * This variable stores the id of the next product to be made
     */
    private static int globalId = 1;
    /**
     * A product's associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * The product's ID
     */
    private int id;
    /**
     * The product's name
     */
    private String name;
    /**
     * The product's price
     */
    private double price;
    /**
     * The number of products in stock
     */
    private int stock;
    /**
     * The minimum stock allowed
     */
    private int min;
    /**
     * The maximum stock allowed
     */
    private int max;

    /**
     * Constructor for a new product
     * @param name the name
     * @param price the price
     * @param stock number in stock
     * @param min minimum stock
     * @param max maximum stock
     */
    public Product(String name, double price, int stock, int min, int max) {
        this.id = globalId++;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        Inventory.addProduct(this);
    }

    /**
     *
     * @return the global ID for products
     */
    public static int getGlobalId(){ return globalId; }

    /**
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the Min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the Max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds one or more parts to a product
     * @param parts the part or parts to add
     */
    public void addAssociatedPart(Part... parts){
        this.associatedParts.addAll(Arrays.asList(parts));
    }

    /**
     * Deletes an associated part
     * @param selectedAssociatedPart the part to delete
     * @return true if an associated part was deleted, otherwise false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return the list of associated parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

}
