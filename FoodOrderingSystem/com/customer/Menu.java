package com.customer;

public class Menu {
    int itemId;
    String itemName;
    double price;
    int rating;
    int quantity;

    public Menu(int itemId, String itemName, double price, int rating, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.rating = rating;
        this.quantity = quantity;
    }

    public int getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public double getPrice() { return price; }
    public int getRating() { return rating; }
    public int getQuantity() { return quantity; }

    public void display() {
        System.out.printf("| %-10d | %-25s | %-10.2f | %-8d | %-8d |\n", itemId, itemName, price, rating, quantity);
    }
}
