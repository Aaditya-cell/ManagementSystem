package model;

public class Medicine {
    private String id;
    private String name;
    private String category; // Make sure this exists
    private int quantity;
    private double price;

    // Fixed Constructor to match the GUI
    public Medicine(String id, String name, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    
    public double calculateTotalValue() { return quantity * price; }
    public boolean isLowStock() { return quantity < 10; }

    public int getMedicineID() {
        try {
            return Integer.parseInt(id.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) { return 0; }
    }
}