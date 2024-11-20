package com.main;

import java.util.*;

import com.admin.Admin;
import com.customer.Customer;

public class FoodOrderingSystem {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println(
                    "==========================================================================================================");
            System.out.println(
                    "                                        FOOD ORDERING SYSTEM                                              ");
            System.out.println(
                    "==========================================================================================================");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.println();
            choice = scanner.nextInt();
            boolean b = true;
            while (b) {
                switch (choice) {
                    case 1:
                        Admin.adminMenu();
                        break;
                    case 2:
                        Customer.customerMenu();
                        Customer.customerOptions();
                        break;
                    case 3:
                        System.out.println("Thank you for using the Food Ordering System.");
                        break;
                }
            }

        } while (choice != 3);
    }
}
