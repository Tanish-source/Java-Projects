package com.student;

public class Student {
    int id;
    String firstName;
    String lastName;
    double spi;
    double overallAttendance;

    public Student(int id, String firstName, String lastName, double spi, double overallAttendance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.spi = spi;
        this.overallAttendance = overallAttendance;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getSpi() {
        return spi;
    }

    public double getOverallAttendance() {
        return overallAttendance;
    }
}