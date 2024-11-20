package com.customer;

public class MenuItem {
    int itemId;
    String itemName;
    int quantity;
    double price;
    int rating;

    public MenuItem(int itemId, String itemName, int quantity, double price, int rating) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.rating = rating;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-30s %-10.2f %-6d", itemId, itemName, price, rating);
    }
}
