package com.customer;

public class CartItem {
     int itemId;
     int restaurantId;
     String itemName;
     String restaurantName;
     double price;
     int quantity;

    public CartItem(int itemId, int restaurantId, String itemName, String restaurantName, double price,
            int quantity) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int updatedQuantity) {
        quantity = updatedQuantity;
    }
}