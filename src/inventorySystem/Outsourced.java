package inventorySystem;

public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
        Inventory.addPart(this);
    }

    public String getMachineId() {
        return companyName;
    }

    public void setMachineId(String companyName) {
        this.companyName = companyName;
    }
}
