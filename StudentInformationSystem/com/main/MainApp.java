package com.main;

import com.student.StudentHandler;
import com.admin.AdminHandler;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a Student or Admin?");
        System.out.println("1. Student");
        System.out.println("2. Admin");
        System.out.println("2. exit");

        int choice = scanner.nextInt();
        scanner.nextLine();
        boolean b = true;
        while (b) {
            switch (choice) {
                case 1:
                    StudentHandler.handleStudentOptions();
                    break;
                case 2:
                    AdminHandler.handleAdminOptions();
                    break;
                case 3:
                    b = false;
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
                    break;
            }
        }
    }
}
