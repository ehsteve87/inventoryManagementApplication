package inventorySystem;

/**
 * This class models outsourced parts
 */
public class Outsourced extends Part {
    /**
     * The company that made the part
     */
    private String companyName;

    /**
     * Constructor for Outsourced parts
     * @param id the id
     * @param name the name
     * @param price the price
     * @param stock number in stock
     * @param min minimum stock
     * @param max maximum stock
     * @param companyName the company that made the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
        Inventory.addPart(this);
    }

    /**
     * @return the company name
     */
    public String getMachineId() {
        return companyName;
    }

    /**
     * @param companyName the company name to set
     */
    public void setMachineId(String companyName) {
        this.companyName = companyName;
    }
}
