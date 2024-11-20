package com.admin;

import com.DS.*;
import com.jdbc.DatabaseConnection;
import com.student.*;
import java.sql.*;
import java.util.*;

public class AdminHandler {
    static Scanner scanner = new Scanner(System.in);

    public static void handleAdminOptions() {
        int attempts = 0;
        boolean success = false;

        while (attempts < 3 && !success) {
            System.out.println("Enter your UserName: ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = validatePassword();

            try {
                Connection c = DatabaseConnection.getConnection();
                String sql = "SELECT * FROM adminCredentials WHERE UserName = ? AND Pass = ?";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Admin login successful.");
                    success = true;
                    adminMenu();
                } else {
                    System.out.println("Incorrect UserName or password.");
                    attempts++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!success) {
            System.out.println("Login failed after 3 attempts. Program will terminate.");
            System.exit(0);
        }
    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean c = true;
        while (c) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Update Student Details");
            System.out.println("4. Add Course");
            System.out.println("5. Remove Course");
            System.out.println("6. Update Course Details");
            System.out.println("7. Add Subject in Course");
            System.out.println("8. Remove Subject in Course");
            System.out.println("9. Update Subject Credit");
            System.out.println("10. Generate Marksheet");
            System.out.println("11. Make Attendance");
            System.out.println("12. display students");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();// trigger : CheckDuplicatePhoneNumberBeforeInsert
                    break;
                case 2:
                    removeStudent();// trigger : Backup_Student
                    break;
                case 3:
                    updateStudentDetails();// trigger : CheckDuplicatePhoneNumberBeforeUpdate
                    break;
                case 4:
                    addCourse();// procedure : AddCourse
                    break;
                case 5:
                    removeCourse();// trigger : Backup_Course
                    break;
                case 6:
                    updateCourseDetails(); // trigger : DuplicateCourseName
                    break;
                case 7:
                    addSubjectInCourse();// procedure : AddSubject
                    break;
                case 8:
                    removeSubjectInCourse();// trigger : Backup_Subject
                    break;
                case 9:
                    updateSubjectDetails();
                    break;
                case 10:
                    generateMarksheet();
                    break;
                case 11:
                    makeAttendance();
                    break;
                case 12:
                    displayStudent();
                    break;
                case 0:
                    c = false;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter First Name: ");
            String firstName = validateName();
            System.out.println("Enter Middle Name: ");
            String middleName = validateName();
            System.out.println("Enter Last Name: ");
            String lastName = validateName();
            String fullName = firstName + " " + middleName + " " + lastName;
            System.out.println("Enter Date of Birth (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            System.out.println("Enter Gender (M/F): ");
            String gender = scanner.nextLine();
            System.out.println("Enter Address: ");
            String address = scanner.nextLine();
            System.out.println("Enter Phone Number: ");
            String phoneNumber = validatePhone();
            System.out.println("Enter Alternate Phone Number: ");
            String altPhoneNumber = validatePhone();
            System.out.println("Enter Email: ");
            String email = validateEmail();

            String insertStudentSQL = "INSERT INTO Student (FirstName, MiddleName, LastName, FullName, DateOfBirth, Gender, Address, PhoneNumber, AlternatePhoneNumber, Email, EnrollmentDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = c.prepareStatement(insertStudentSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, firstName);
            pstmt.setString(2, middleName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, fullName);
            pstmt.setString(5, dob);
            pstmt.setString(6, gender);
            pstmt.setString(7, address);
            pstmt.setString(8, phoneNumber);
            pstmt.setString(9, altPhoneNumber);
            pstmt.setString(10, email);
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(11, currentTimestamp);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int studentId = 0;
            if (rs.next()) {
                studentId = rs.getInt(1);
            }

            printCourses();
            System.out.println("Enter Course ID: ");
            int courseId = scanner.nextInt();

            String departmentIdSQL = "SELECT DepartmentID, CourseFee FROM Course WHERE CourseID = ?";
            pstmt = c.prepareStatement(departmentIdSQL);
            pstmt.setInt(1, courseId);
            ResultSet deptRs = pstmt.executeQuery();
            int departmentId = 0;
            double courseFee = 0;
            if (deptRs.next()) {
                departmentId = deptRs.getInt("DepartmentID");
                courseFee = deptRs.getDouble("CourseFee");
            } else {
                System.out.println("Invalid Course ID.");
                return;
            }

            String updateStudentSQL = "UPDATE Student SET CourseID = ?, DepartmentID = ? WHERE StudentID = ?";
            pstmt = c.prepareStatement(updateStudentSQL);
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, departmentId);
            pstmt.setInt(3, studentId);
            pstmt.executeUpdate();

            String enrollmentNo = generateEnrollmentNo(c, studentId);
            int no = Integer.parseInt(enrollmentNo);

            String updateEnrollmentSQL = "UPDATE Student SET EnrollmentNo = ? WHERE StudentID = ?";
            pstmt = c.prepareStatement(updateEnrollmentSQL);
            pstmt.setInt(1, no);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();

            double amountPaid = 20000;
            double remainingAmount = courseFee - amountPaid;

            scanner.nextLine();
            System.out.println("Enter Payment Date (YYYY-MM-DD): ");
            String paymentDate = scanner.nextLine();
            System.out.println("Enter Payment Method: ");
            String paymentMethod = scanner.nextLine();

            String insertFeesSQL = "INSERT INTO Fees (StudentID, CourseID, TotalCourseFee, AmountPaid, RemainingAmount, PaymentDate, PaymentMethod) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = c.prepareStatement(insertFeesSQL);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setDouble(3, courseFee);
            pstmt.setDouble(4, amountPaid);
            pstmt.setDouble(5, remainingAmount);
            pstmt.setDate(6, java.sql.Date.valueOf(paymentDate));
            pstmt.setString(7, paymentMethod);
            pstmt.executeUpdate();

            String fetchSubjectSQL = "SELECT SubjectID, TotalLecture FROM Subject WHERE CourseID = ?";
            pstmt = c.prepareStatement(fetchSubjectSQL);
            pstmt.setInt(1, courseId);
            ResultSet subjectRs = pstmt.executeQuery();

            while (subjectRs.next()) {
                int subjectId = subjectRs.getInt("SubjectID");
                int totalLectures = subjectRs.getInt("TotalLecture");

                String insertAttendanceSQL = "INSERT INTO attendance (StudentID, SubjectID, TotalLectures) VALUES (?, ?, ?)";
                PreparedStatement attendanceStmt = c.prepareStatement(insertAttendanceSQL);
                attendanceStmt.setInt(1, studentId);
                attendanceStmt.setInt(2, subjectId);
                attendanceStmt.setInt(3, totalLectures);
                attendanceStmt.executeUpdate();
            }

            System.out.println("Student added successfully with Enrollment No: " + enrollmentNo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeStudent() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter EnrollmentNo to remove: ");
            int no = scanner.nextInt();

            String deleteStudentSQL = "DELETE FROM Student WHERE EnrollmentNo = ?";
            PreparedStatement pstmt = c.prepareStatement(deleteStudentSQL);
            pstmt.setInt(1, no);
            int response = pstmt.executeUpdate();
            if (response > 0) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudentDetails() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter EnrollmentNo to update: ");
            int no = scanner.nextInt();
            scanner.nextLine();

            String checkStudentSQL = "SELECT * FROM Student WHERE EnrollmentNo = ?";
            PreparedStatement pstmt = c.prepareStatement(checkStudentSQL);
            pstmt.setInt(1, no);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Student not found in the database.");
                return;
            }

            System.out.println("1. Update Date of Birth");
            System.out.println("2. Update Gender");
            System.out.println("3. Update Address");
            System.out.println("4. Update Phone Number");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter new Date of Birth (YYYY-MM-DD): ");
                    String newDOB = scanner.nextLine();
                    updateStudentField(c, no, "DateOfBirth", newDOB);
                    break;
                case 2:
                    System.out.println("Enter new Gender (M/F): ");
                    String newGender = scanner.nextLine();
                    updateStudentField(c, no, "Gender", newGender);
                    break;
                case 3:
                    System.out.println("Enter new Address: ");
                    String newAddress = scanner.nextLine();
                    updateStudentField(c, no, "Address", newAddress);
                    break;
                case 4:
                    System.out.println("Enter new Phone Number: ");
                    String newPhoneNumber = scanner.nextLine();
                    updateStudentField(c, no, "PhoneNumber", newPhoneNumber);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println("Student details updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayStudent() {
        Scanner scanner = new Scanner(System.in);

        CustomLinkedList studentList = new CustomLinkedList();
        try {
            Connection c = DatabaseConnection.getConnection();
            String sql = "SELECT StudentID, FirstName, LastName, SPI, OverallAttendance FROM Student";
            PreparedStatement pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("StudentID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                double spi = rs.getDouble("SPI");
                double overallAttendance = rs.getDouble("OverallAttendance");

                Student student = new Student(id, firstName, lastName, spi, overallAttendance);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("\nSelect an option:");
            System.out.println("1. SPI Wise");
            System.out.println("2. Overall Attendance");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1. High to Low");
                    System.out.println("2. Low to High");
                    System.out.println("3. exit");
                    System.out.print("Enter your choice: ");
                    int spiChoice = scanner.nextInt();

                    switch (spiChoice) {
                        case 1:
                            studentList.sortBySpiDescending();
                            System.out.println("\n=== Students Sorted by SPI (High to Low) ===");
                            studentList.printList();
                            break;
                        case 2:
                            studentList.sortBySpiAscending();
                            System.out.println("\n=== Students Sorted by SPI (Low to High) ===");
                            studentList.printList();
                            break;
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("1. High to Low");
                    System.out.println("2. Low to High");
                    System.out.println("3. Overall Attendance below 75%");
                    System.out.print("Enter your choice: ");
                    int attendanceChoice = scanner.nextInt();

                    switch (attendanceChoice) {
                        case 1:
                            studentList.sortByOverallAttendanceDescending();
                            System.out.println("\n=== Students Sorted by Overall Attendance (High to Low) ===");
                            studentList.printList();
                            break;
                        case 2:
                            studentList.sortByOverallAttendanceAscending();
                            System.out.println("\n=== Students Sorted by Overall Attendance (Low to High) ===");
                            studentList.printList();
                            break;
                        case 3:
                            System.out.println("\n=== Students with Overall Attendance Below 75% ===");
                            studentList.printBelow75Attendance();
                            break;
                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    break;

                case 3:
                    continueRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

        scanner.close();
    }

    public static void updateStudentField(Connection c, int no, String fieldName, String newValue)
            throws SQLException {
        String updateStudentSQL = "UPDATE Student SET " + fieldName + " = ? WHERE EnrollmentNo = ?";
        PreparedStatement pstmt = c.prepareStatement(updateStudentSQL);
        pstmt.setString(1, newValue);
        pstmt.setInt(2, no);
        pstmt.executeUpdate();
    }

    public static void addCourse() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            printAllDepartments(c);

            System.out.println("Enter Course Name: ");
            String courseName = scanner.nextLine();
            System.out.println("Enter Course Duration: ");
            String courseDuration = scanner.nextLine();
            System.out.println("Enter Course Fee: ");
            double courseFee = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter Department ID: ");
            int departmentId = scanner.nextInt();
            scanner.nextLine();

            String callAddCourse = "{CALL AddCourse(?, ?, ?, ?, ?)}";
            CallableStatement cstmt = c.prepareCall(callAddCourse);
            cstmt.setString(1, courseName);
            cstmt.setString(2, courseDuration);
            cstmt.setDouble(3, courseFee);
            cstmt.setInt(4, departmentId);
            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
            cstmt.execute();

            int courseId = cstmt.getInt(5);
            System.out.println("Course added successfully with Course ID: " + courseId);

            System.out.println("How many subjects in this course? ");
            int numSubjects = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numSubjects; i++) {
                addSubject(courseId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCourse() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter Course ID to remove: ");
            int courseId = scanner.nextInt();

            String deleteCourseSQL = "DELETE FROM Course WHERE CourseID = ?";
            PreparedStatement pstmt = c.prepareStatement(deleteCourseSQL);
            pstmt.setInt(1, courseId);
            int response = pstmt.executeUpdate();
            if (response > 0) {
                System.out.println("Course removed successfully.");
            } else {
                System.out.println("course not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCourseDetails() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter Course ID to update: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            if (!courseExists(c, courseId)) {
                System.out.println("Course ID does not exist.");
                return;
            }

            System.out.println("1. Update Course Name");
            System.out.println("2. Update Course Duration");
            System.out.println("3. Update Course Fee");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter new Course Name: ");
                    String newCourseName = scanner.nextLine();
                    updateCourseField(c, courseId, "CourseName", newCourseName);
                    break;
                case 2:
                    System.out.println("Enter new Course Duration: ");
                    String newCourseDuration = scanner.nextLine();
                    updateCourseField(c, courseId, "CourseDuration", newCourseDuration);
                    break;
                case 3:
                    System.out.println("Enter new Course Fee: ");
                    double newCourseFee = scanner.nextDouble();
                    updateCourseField(c, courseId, "CourseFee", String.valueOf(newCourseFee));
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean courseExists(Connection c, int courseId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Course WHERE CourseID = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setInt(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateCourseField(Connection c, int courseId, String fieldName, String newValue)
            throws SQLException {
        String updateSQL = "UPDATE Course SET " + fieldName + " = ? WHERE CourseID = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(updateSQL);
            pstmt.setString(1, newValue);
            pstmt.setInt(2, courseId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course " + fieldName + " updated successfully.");
            } else {
                System.out.println("Failed to update course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSubjectInCourse() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter Course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("How many subjects do you want to add?");
            int numberOfSubjects = scanner.nextInt();
            scanner.nextLine();

            for (int i = 1; i <= numberOfSubjects; i++) {
                System.out.println("Adding subject " + i + " of " + numberOfSubjects + ":");
                addSubject(courseId);
            }

            System.out.println("All subjects added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSubject(int courseId) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Subject Name: ");
        String subjectName = scanner.nextLine();
        System.out.println("Enter Subject Credit: ");
        int subjectCredit = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Total Lectures: ");
        int totalLectures = scanner.nextInt();
        scanner.nextLine();

        try {
            Connection c = DatabaseConnection.getConnection();
            String callAddSubject = "{CALL AddSubject(?, ?, ?, ?)}";
            CallableStatement cstmt = c.prepareCall(callAddSubject);

            cstmt.setString(1, subjectName);
            cstmt.setInt(2, subjectCredit);
            cstmt.setInt(3, courseId);
            cstmt.setInt(4, totalLectures);

            cstmt.execute();

            System.out.println("Subject added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void removeSubjectInCourse() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter Subject ID to remove: ");
            int subjectId = scanner.nextInt();

            String deleteSubjectSQL = "DELETE FROM Subject WHERE SubjectID = ?";
            PreparedStatement pstmt = c.prepareStatement(deleteSubjectSQL);
            pstmt.setInt(1, subjectId);
            int response = pstmt.executeUpdate();
            if (response > 0) {
                System.out.println("Subject removed successfully.");
            } else {
                System.out.println("Subject not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSubjectDetails() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter Subject ID to update: ");
            int subjectId = scanner.nextInt();

            System.out.println("Enter new Subject Credit: ");
            int newSubjectCredit = scanner.nextInt();

            System.out.println("Enter new Total Lecture: ");
            int newTotalLecture = scanner.nextInt();

            String updateSubjectSQL = "UPDATE Subject SET Credits = ?, TotalLecture = ? WHERE SubjectID = ?";
            PreparedStatement pstmt = c.prepareStatement(updateSubjectSQL);
            pstmt.setInt(1, newSubjectCredit);
            pstmt.setInt(2, newTotalLecture);
            pstmt.setInt(3, subjectId);
            int response = pstmt.executeUpdate();
            if (response > 0) {
                System.out.println("Subject credit and total lectures updated successfully.");
            } else {
                System.out.println("failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateMarksheet() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter Enrollment No to generate marksheet: ");
            String enrollmentNo = scanner.nextLine();

            String studentQuery = "SELECT StudentID, CourseID FROM Student WHERE EnrollmentNo = ?";
            PreparedStatement studentStmt = c.prepareStatement(studentQuery);
            studentStmt.setString(1, enrollmentNo);
            ResultSet rsStudent = studentStmt.executeQuery();
            int studentId = 0;
            int courseId = 0;
            if (rsStudent.next()) {
                studentId = rsStudent.getInt("StudentID");
                courseId = rsStudent.getInt("CourseID");
            } else {
                System.out.println("Invalid Enrollment No. No student found.");
                return;
            }

            String subjectQuery = "SELECT SubjectID, SubjectName FROM Subject WHERE CourseID = ?";
            PreparedStatement subjectStmt = c.prepareStatement(subjectQuery);
            subjectStmt.setInt(1, courseId);
            ResultSet rsSubjects = subjectStmt.executeQuery();

            double totalMarks = 0;
            int totalGradePoints = 0;
            int subjectCount = 0;

            while (rsSubjects.next()) {
                int subjectId = rsSubjects.getInt("SubjectID");
                String subjectName = rsSubjects.getString("SubjectName");

                System.out.println("Enter marks for " + subjectName + " (out of 100): ");
                double marksObtained = scanner.nextDouble();
                scanner.nextLine(); // consume newline

                String grade;
                int gradePoint;

                if (marksObtained >= 90) {
                    grade = "O";
                    gradePoint = 12;
                } else if (marksObtained >= 80) {
                    grade = "A";
                    gradePoint = 10;
                } else if (marksObtained >= 70) {
                    grade = "B";
                    gradePoint = 8;
                } else if (marksObtained >= 60) {
                    grade = "C";
                    gradePoint = 6;
                } else if (marksObtained >= 50) {
                    grade = "D";
                    gradePoint = 4;
                } else if (marksObtained >= 35) {
                    grade = "E";
                    gradePoint = 2;
                } else {
                    grade = "F";
                    gradePoint = 0;
                }

                String insertMark = "INSERT INTO Mark (StudentID, SubjectID, SubjectMarks, Grade, GradePoint) VALUES (?, ?, ?, ?, ?)";
                try {
                    PreparedStatement insertStmt = c.prepareStatement(insertMark);
                    insertStmt.setInt(1, studentId);
                    insertStmt.setInt(2, subjectId);
                    insertStmt.setDouble(3, marksObtained);
                    insertStmt.setString(4, grade);
                    insertStmt.setInt(5, gradePoint);
                    insertStmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                totalMarks += marksObtained;
                totalGradePoints += gradePoint;
                subjectCount++;
            }

            double SPI;
            if (subjectCount > 0) {
                SPI = (double) totalGradePoints / subjectCount;
            } else {
                SPI = 0;
            }

            String updateStudent = "UPDATE Student SET TotalMarks = ?, SPI = ? WHERE StudentID = ?";
            try {
                PreparedStatement updateStudentStmt = c.prepareStatement(updateStudent);
                updateStudentStmt.setDouble(1, totalMarks);
                updateStudentStmt.setDouble(2, SPI);
                updateStudentStmt.setInt(3, studentId);
                updateStudentStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (SPI <= 6.0) {
                System.out.println("failed with SPI : " + SPI);
            } else {
                System.out.println("\nMarksheet generated successfully for Enrollment No: " + enrollmentNo);
                System.out.println("Total Marks: " + totalMarks + ", SPI: " + SPI);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void makeAttendance() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection c = DatabaseConnection.getConnection();
            System.out.println("Enter Enrollment No: ");
            String enrollmentNo = scanner.nextLine();

            String studentQuery = "SELECT StudentID, CourseID FROM Student WHERE EnrollmentNo = ?";
            PreparedStatement studentStmt = c.prepareStatement(studentQuery);
            studentStmt.setString(1, enrollmentNo);
            ResultSet rsStudent = studentStmt.executeQuery();

            int studentId = 0;
            int courseId = 0;
            if (rsStudent.next()) {
                studentId = rsStudent.getInt("StudentID");
                courseId = rsStudent.getInt("CourseID");
            } else {
                System.out.println("Invalid Enrollment No. No student found.");
                return;
            }

            String subjectQuery = "SELECT SubjectID, SubjectName, TotalLecture FROM Subject WHERE CourseID = ?";
            PreparedStatement subjectStmt = c.prepareStatement(subjectQuery);
            subjectStmt.setInt(1, courseId);
            ResultSet rsSubjects = subjectStmt.executeQuery();

            int totalLecturesAttended = 0;
            int totalLecturesPossible = 0;

            while (rsSubjects.next()) {
                int subjectId = rsSubjects.getInt("SubjectID");
                String subjectName = rsSubjects.getString("SubjectName");
                int totalLectures = rsSubjects.getInt("TotalLecture");

                int attendedLectures;
                do {
                    System.out.println(
                            "Enter attended lectures for " + subjectName + " (out of " + totalLectures + "): ");
                    attendedLectures = scanner.nextInt();
                    scanner.nextLine();

                    if (attendedLectures > totalLectures) {
                        System.out.println(
                                "Attended lectures cannot be greater than total lectures. Please enter a valid number.");
                    }
                } while (attendedLectures > totalLectures);

                double attendancePercentage = (attendedLectures * 100.0) / totalLectures;

                String upsertAttendance = "INSERT INTO Attendance (StudentID, SubjectID, TotalLectures, AttendedLectures, AttendancePercentage) "
                        + "VALUES (?, ?, ?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE TotalLectures = VALUES(TotalLectures), AttendedLectures = VALUES(AttendedLectures), AttendancePercentage = VALUES(AttendancePercentage)";
                try {
                    PreparedStatement insertStmt = c.prepareStatement(upsertAttendance);
                    insertStmt.setInt(1, studentId);
                    insertStmt.setInt(2, subjectId);
                    insertStmt.setInt(3, totalLectures);
                    insertStmt.setInt(4, attendedLectures);
                    insertStmt.setDouble(5, attendancePercentage);
                    insertStmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                totalLecturesAttended += attendedLectures;
                totalLecturesPossible += totalLectures;
            }

            double overallAttendance;
            if (totalLecturesPossible > 0) {
                overallAttendance = (totalLecturesAttended * 100.0) / totalLecturesPossible;
            } else {
                overallAttendance = 0;
            }

            String updateStudent = "UPDATE Student SET OverallAttendance = ? WHERE StudentID = ?";
            try {
                PreparedStatement updateStmt = c.prepareStatement(updateStudent);
                updateStmt.setDouble(1, overallAttendance);
                updateStmt.setInt(2, studentId);
                updateStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("\nAttendance recorded successfully for Enrollment No: " + enrollmentNo);
            System.out.println("Overall Attendance: " + overallAttendance + "%");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printCourses() {
        try {
            Connection c = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Course";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Available Courses:");
            while (rs.next()) {
                System.out.println(
                        "Course ID: " + rs.getInt("CourseID") + ", Course Name: " + rs.getString("CourseName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllDepartments(Connection c) {
        String query = "SELECT DepartmentID, DepartmentName FROM Department";
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Departments:");
            while (rs.next()) {
                int departmentId = rs.getInt("DepartmentID");
                String departmentName = rs.getString("DepartmentName");
                System.out.println("ID: " + departmentId + ", Name: " + departmentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String generateEnrollmentNo(Connection c, int studentId) {
        String currentYear = String.valueOf(java.time.Year.now());
        String paddedStudentId = String.format("%04d", studentId);
        String enrollmentNo = currentYear + paddedStudentId;
        return enrollmentNo;
    }

    public static String validateEmail() {
        while (true) {
            System.out.println("Enter Email (Minimum length 15 and ends with '@gmail.com'): ");
            String email = scanner.next();
            if (email.length() >= 15 && email.endsWith("@gmail.com")) {
                return email;
            } else {
                System.out
                        .println("Invalid email. Email must be at least 15 characters long and end with '@gmail.com'.");
            }
        }
    }

    public static String validatePassword() {
        while (true) {
            System.out.println("Enter Password (Length must be 8 and contain at least 1 special character): ");
            String password = scanner.next();
            if (password.length() == 8 && containsSpecialCharacter(password)) {
                return password;
            } else {
                System.out.println(
                        "Invalid password. Password must be exactly 8 characters long and contain at least 1 special character.");
            }
        }
    }

    public static String validatePhone() {
        while (true) {
            System.out.println("Enter Phone (Length must be 10 and contain only digits): ");
            String phone = scanner.next();
            if (phone.length() == 10 && isNumeric(phone)) {
                return phone;
            } else {
                System.out.println("Invalid phone number. Phone number must be exactly 10 digits long.");
            }
        }
    }

    public static String validateName() {
        while (true) {
            System.out.println("Enter Name (Minimum length 3 and contains only letters and spaces): ");
            String name = scanner.next();
            if (name.length() >= 3 && isAlphaWithSpaces(name)) {
                return name;
            } else {
                System.out.println(
                        "Invalid name. Name must be at least 3 characters long and contain only letters and spaces.");
            }
        }
    }

    public static boolean isAlphaWithSpaces(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlpha(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsSpecialCharacter(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }
}