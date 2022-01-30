package inventorySystem;

/**
 * This class models InHouse parts
 */
public class InHouse extends Part {
    /**
     * The ID of the machine that made the part
     */
    private int machineId;

    /**
     * Constructor for new InHouse parts
     * @param id the ID
     * @param name the name
     * @param price the price
     * @param stock the number in stock
     * @param min the minimum stock
     * @param max the maximum stock
     * @param machineId the machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
        Inventory.addPart(this);
    }

    /**
     * @return the Machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the Machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
