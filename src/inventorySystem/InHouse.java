package inventorySystem;

public class InHouse extends Part {
    private int machineId;

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
