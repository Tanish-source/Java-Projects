package com.customer;

public class Restaurant {
    int restaurantId;
    String name;
    String address;
    String phoneNo;
    int rating;

    public Restaurant(int restaurantId, String name, String address, String phoneNo, int rating) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.rating = rating;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Restaurant ID: " + restaurantId + ", Name: " + name + ", Address: " + address + ", Phone No: " + phoneNo
                + ", Rating: " + rating;
    }
}
