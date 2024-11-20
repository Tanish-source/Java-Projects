package com.student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.jdbc.DatabaseConnection;

public class StudentHandler {
    static int id;

    public static void handleStudentOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Sign Up (New User)");
        System.out.println("2. Log In (Already have an account)");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                signUp();
                break;
            case 2:
                logIn();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email: ");
        String email = sc.nextLine();

        try {
            Connection c = DatabaseConnection.getConnection();

            String sql1 = "SELECT StudentID, Email FROM Student WHERE Email = ?";
            PreparedStatement pst1 = c.prepareStatement(sql1);
            pst1.setString(1, email);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                String defaultEmail = rs.getString("Email");
                if (defaultEmail.equals(email)) {
                    // int id = rs.getInt("StudentID");
                    String password = "";
                    while (true) {
                        System.out.println("Create a password (at least 8 characters and 1 special character): ");
                        password = sc.nextLine();
                        if (isValidPassword(password)) {
                            break;
                        } else {
                            System.out.println("Password does not meet the criteria. Please try again.");
                        }
                    }

                    String confirmPassword = "";
                    while (true) {
                        System.out.println("Confirm your password: ");
                        confirmPassword = sc.nextLine();
                        if (confirmPassword.equals(password)) {
                            break;
                        } else {
                            System.out.println("Passwords do not match. Please try again.");
                        }
                    }

                    String hashedPassword = hashPassword(password);

                    String sql2 = "INSERT INTO UserCredentials (email, password_hash) VALUES (?, ?)";
                    PreparedStatement pst2 = c.prepareStatement(sql2);
                    pst2.setString(1, email);
                    pst2.setString(2, hashedPassword);
                    pst2.executeUpdate();

                    System.out.println("Sign-up successful. Proceeding to student functionalities...");
                    studentMenu();

                }
            } else {
                System.out.println("Invalid email. This email is not associated with any student.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email: ");
        String email = sc.nextLine();

        try {
            Connection c = DatabaseConnection.getConnection();
            String checkEmailSQL = "SELECT StudentID, Email FROM Student WHERE Email = ?";
            PreparedStatement checkEmailStmt = c.prepareStatement(checkEmailSQL);
            checkEmailStmt.setString(1, email);
            ResultSet rsEmailCheck = checkEmailStmt.executeQuery();

            if (rsEmailCheck.next()) {
                String defaultEmail = rsEmailCheck.getString("Email");
                if (defaultEmail.equals(email)) {
                    id = rsEmailCheck.getInt("StudentID");
                    System.out.println("Enter your password: ");
                    String password = sc.nextLine();

                    String sql = "SELECT * FROM UserCredentials WHERE email = ? AND password_hash = ?";
                    PreparedStatement pstmt = c.prepareStatement(sql);
                    pstmt.setString(1, email);
                    pstmt.setString(2, hashPassword(password));
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("Login successful.");
                        updateLastLogin(c, email);
                        studentMenu();
                    } else {
                        System.out.println("Invalid email or password.");
                        handleLoginFailure(sc, c, email);
                    }
                }
            } else {
                System.out.println("Invalid email or password.");
                handleLoginFailure(sc, c, email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void handleLoginFailure(Scanner sc, Connection c, String email) {
        int attempts = 1;
        while (attempts < 3) {
            System.out.println("Attempt " + (attempts + 1) + " of 3. Try again.");
            System.out.println("Enter your password: ");
            String password = sc.nextLine();

            try {
                String sql = "SELECT * FROM UserCredentials WHERE email = ? AND password_hash = ?";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, hashPassword(password));
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Login successful.");
                    updateLastLogin(c, email);
                    return;
                } else {
                    attempts++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Login failed after 3 attempts. Do you want to reset your password? (yes/no)");
        String resetChoice = sc.nextLine();
        if (resetChoice.equalsIgnoreCase("yes")) {
            resetPassword(sc, c, email);
        }
    }

    public static void resetPassword(Scanner sc, Connection c, String email) {
        System.out.println("Enter your new password: ");
        String newPassword = sc.nextLine();
        while (!isValidPassword(newPassword)) {
            System.out.println("Password does not meet the criteria. Please try again.");
            newPassword = sc.nextLine();
        }

        String confirmPassword = "";
        while (true) {
            System.out.println("Confirm your new password: ");
            confirmPassword = sc.nextLine();
            if (confirmPassword.equals(newPassword)) {
                break;
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        }

        try {
            String sql1 = "UPDATE UserCredentials SET password_hash = ? WHERE email = ?";
            PreparedStatement pst1 = c.prepareStatement(sql1);
            pst1.setString(1, hashPassword(newPassword));
            pst1.setString(2, email);
            pst1.executeUpdate();
            System.out.println("Password reset successful. Please log in with your new password.");
            logIn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLastLogin(Connection c, String email) throws SQLException {
        String sql = "UPDATE UserCredentials SET last_login = CURRENT_TIMESTAMP WHERE email = ?";
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, email);
        pst.executeUpdate();
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".[!@#$%^&].");
    }

    public static String hashPassword(String password) {
        return password;
    }

    public static void studentMenu() {
        Scanner sc = new Scanner(System.in);
        boolean a = true;
        while (true) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1. See Profile");
            System.out.println("2. See Course Details");
            System.out.println("3. See Marksheet");
            System.out.println("4. See Attendance");
            System.out.println("5. See Fee Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    seeProfile();
                    break;
                case 2:
                    seeCourseDetails();
                    break;
                case 3:
                    seeMarksheet();
                    break;
                case 4:
                    seeAttendance();
                    break;
                case 5:
                    seeFeeDetails();
                    break;
                case 6:
                   return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void seeProfile() {
        try {
            Connection c = DatabaseConnection.getConnection();
            String sql = "{CALL GetStudentProfile(?)}";
            CallableStatement cst = c.prepareCall(sql);
            cst.setInt(1, id);
            ResultSet rs = cst.executeQuery();

            if (rs.next()) {
                System.out.println("\n=== Student Profile ===");
                System.out.printf("Full Name: %s %s %s\n", rs.getString("FirstName"), rs.getString("MiddleName"),
                        rs.getString("LastName"));
                System.out.printf("Date of Birth: %s\n", rs.getDate("DateOfBirth"));
                System.out.printf("Gender: %s\n", rs.getString("Gender"));
                System.out.printf("Address: %s\n", rs.getString("Address"));
                System.out.printf("Phone Number: %s\n", rs.getString("PhoneNumber"));
                System.out.printf("Email: %s\n", rs.getString("Email"));
                System.out.printf("Enrollment No: %d\n", rs.getInt("EnrollmentNo"));
                System.out.printf("Enrollment Date: %s\n", rs.getDate("EnrollmentDate"));
                System.out.printf("Overall Attendance: %.2f%%\n", rs.getBigDecimal("OverallAttendance"));
                System.out.printf("Total Marks: %.2f\n", rs.getBigDecimal("TotalMarks"));
                System.out.printf("SPI: %.2f\n", rs.getBigDecimal("SPI"));
            } else {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void seeCourseDetails() {
        try {
            Connection c = DatabaseConnection.getConnection();
            String sql1 = "SELECT c.CourseName, c.CourseDuration, c.CourseFee, s.SubjectName, s.Credits " +
                    "FROM Course c " +
                    "JOIN Subject s ON c.CourseID = s.CourseID " +
                    "JOIN Student st ON c.CourseID = st.CourseID " +
                    "WHERE st.StudentID = ?";
            PreparedStatement pst1 = c.prepareStatement(sql1);
            pst1.setInt(1, id);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                System.out.println("\n=== Course Details ===");
                System.out.printf("Course Name: %s\n", rs.getString("CourseName"));
                System.out.printf("Duration: %s\n", rs.getString("CourseDuration"));
                System.out.printf("Fee: %.2f\n", rs.getBigDecimal("CourseFee"));
                System.out.println("Subjects and Credits:");
                do {
                    System.out.printf("  Subject: %-20s | Credits: %d\n", rs.getString("SubjectName"),
                            rs.getInt("Credits"));
                } while (rs.next());
            } else {
                System.out.println("No course found for this student.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void seeMarksheet() {

        try {
            Connection c = DatabaseConnection.getConnection();
            String sql1 = "SELECT * FROM Student WHERE StudentID = ?";
            PreparedStatement pst1 = c.prepareStatement(sql1);
            pst1.setInt(1, id);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {

                System.out.println("\n=== Marksheet ===");
                System.out.printf("Full Name: %s %s %s\n", rs.getString("FirstName"),
                        rs.getString("MiddleName"), rs.getString("LastName"));
                System.out.printf("Enrollment No: %d\n", rs.getInt("EnrollmentNo"));

                String marksSQL = "SELECT s.SubjectName, m.SubjectMarks, m.Grade, m.GradePoint " +
                        "FROM Mark m " +
                        "JOIN Subject s ON m.SubjectID = s.SubjectID " +
                        "WHERE m.StudentID = ?";
                PreparedStatement pst2 = c.prepareStatement(marksSQL);
                pst2.setInt(1, id);
                ResultSet rsMarks = pst2.executeQuery();

                System.out.println("Subject Marks:");
                while (rsMarks.next()) {
                    System.out.printf("  Subject: %-20s | Marks: %.2f | Grade: %s | Grade Point: %d\n",
                            rsMarks.getString("SubjectName"),
                            rsMarks.getBigDecimal("SubjectMarks"),
                            rsMarks.getString("Grade"),
                            rsMarks.getInt("GradePoint"));
                }
            } else {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void seeAttendance() {
        if (id <= 0) {
            System.out.println("Invalid student ID.");
            return;
        }

        try {
            Connection c = DatabaseConnection.getConnection();
            String sql1 = "SELECT s.SubjectName, a.AttendancePercentage " +
                    "FROM Attendance a " +
                    "JOIN Subject s ON a.SubjectID = s.SubjectID " +
                    "WHERE a.StudentID = ?";
            PreparedStatement pst1 = c.prepareStatement(sql1);
            pst1.setInt(1, id);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                System.out.println("\n=== Attendance Details ===");
                do {
                    System.out.printf("Subject: %-20s | Attendance: %.2f%%\n",
                            rs.getString("SubjectName"),
                            rs.getBigDecimal("AttendancePercentage"));
                } while (rs.next());
            } else {
                System.out.println("No attendance records found for this student.");
            }

            String overallAttendanceSQL = "SELECT (SUM(a.AttendedLectures) / SUM(a.TotalLectures)) * 100 AS OverallAttendance "
                    +
                    "FROM Attendance a " +
                    "WHERE a.StudentID = ?";
            pst1 = c.prepareStatement(overallAttendanceSQL);
            pst1.setInt(1, id);
            rs = pst1.executeQuery();

            if (rs.next()) {
                System.out.printf("\nOverall Attendance: %.2f%%\n", rs.getBigDecimal("OverallAttendance"));
            } else {
                System.out.println("Unable to retrieve overall attendance.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seeFeeDetails() {
        Scanner sc = new Scanner(System.in);
        try (Connection c = DatabaseConnection.getConnection()) {
            String feeDetailsSQL = "SELECT * FROM Fees WHERE StudentID = ?";
            PreparedStatement pst1 = c.prepareStatement(feeDetailsSQL);
            pst1.setInt(1, id);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {
                System.out.println("\n=== Fee Details ===");
                System.out.printf("Total Course Fee: %.2f\n", rs.getDouble("TotalCourseFee"));
                System.out.printf("Fee Amount Paid: %.2f\n", rs.getDouble("AmountPaid"));
                System.out.printf("Remaining Amount: %.2f\n", rs.getDouble("RemainingAmount"));
                System.out.printf("Payment Date: %s\n", rs.getDate("PaymentDate"));
                System.out.printf("Payment Method: %s\n", rs.getString("PaymentMethod"));

                while (true) {
                    System.out.println("\nDo you want to pay fees? (yes/no)");
                    String choice = sc.nextLine();
                    if (choice.equalsIgnoreCase("yes")) {
                        System.out.println("Enter the amount you want to pay: ");
                        double paymentAmount = sc.nextDouble();
                        sc.nextLine();

                        System.out.println("Enter the payment method (e.g., Credit Card, Cash, Bank Transfer): ");
                        String paymentMethod = sc.nextLine();
                        String updateFeeSQL = "UPDATE Fees SET AmountPaid = AmountPaid + ?, RemainingAmount = TotalCourseFee - AmountPaid, PaymentDate = CURRENT_TIMESTAMP, PaymentMethod = ? WHERE StudentID = ?";
                        PreparedStatement pst2 = c.prepareStatement(updateFeeSQL);
                        pst2.setDouble(1, paymentAmount);
                        pst2.setString(2, paymentMethod);
                        pst2.setInt(3, id);
                        int rowsUpdated = pst2.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Fee payment successful. Your updated fee details are as follows:");
                            rs = pst1.executeQuery();
                            if (rs.next()) {
                                System.out.printf("Total Course Fee: %.2f\n", rs.getDouble("TotalCourseFee"));
                                System.out.printf("Fee Amount Paid: %.2f\n", rs.getDouble("AmountPaid"));
                                System.out.printf("Remaining Amount: %.2f\n", rs.getDouble("RemainingAmount"));
                                System.out.printf("Payment Date: %s\n", rs.getDate("PaymentDate"));
                                System.out.printf("Payment Method: %s\n", rs.getString("PaymentMethod"));
                            }
                        } else {
                            System.out.println("Error updating fee details. Please try again.");
                        }
                        break;
                    } else if (choice.equalsIgnoreCase("no")) {
                        System.out.println("Fee details:");
                        System.out.printf("Total Course Fee: %.2f\n", rs.getDouble("TotalCourseFee"));
                        System.out.printf("Fee Amount Paid: %.2f\n", rs.getDouble("AmountPaid"));
                        System.out.printf("Remaining Amount: %.2f\n", rs.getDouble("RemainingAmount"));
                        System.out.printf("Payment Date: %s\n", rs.getDate("PaymentDate"));
                        System.out.printf("Payment Method: %s\n", rs.getString("PaymentMethod"));
                        break;
                    } else {
                        System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                    }
                }
            } else {
                System.out.println("No fee details found for this student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}