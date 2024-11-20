package com.DS;

import com.student.*;

class Node {
    Student student;
    Node next;

    Node(Student student) {
        this.student = student;
        this.next = null;
    }
}

public class CustomLinkedList {
    Node head;

    public CustomLinkedList() {
        this.head = null;
    }

    public void add(Student student) {
        if (head == null) {
            head = new Node(student);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(student);
        }
    }

    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.printf("Student ID: %d, Name: %s %s, SPI: %.2f, Overall Attendance: %.2f%%\n",
                    current.student.getId(), current.student.getFirstName(), current.student.getLastName(),
                    current.student.getSpi(), current.student.getOverallAttendance());
            current = current.next;
        }
    }

    public void sortBySpiAscending() {
        if (head == null || head.next == null)
            return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.student.getSpi() > current.next.student.getSpi()) {
                    Student temp = current.student;
                    current.student = current.next.student;
                    current.next.student = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public void sortBySpiDescending() {
        if (head == null || head.next == null)
            return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.student.getSpi() < current.next.student.getSpi()) {
                    Student temp = current.student;
                    current.student = current.next.student;
                    current.next.student = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public void sortByOverallAttendanceAscending() {
        if (head == null || head.next == null)
            return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.student.getOverallAttendance() > current.next.student.getOverallAttendance()) {
                    Student temp = current.student;
                    current.student = current.next.student;
                    current.next.student = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public void sortByOverallAttendanceDescending() {
        if (head == null || head.next == null)
            return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.student.getOverallAttendance() < current.next.student.getOverallAttendance()) {
                    Student temp = current.student;
                    current.student = current.next.student;
                    current.next.student = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public void printBelow75Attendance() {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.student.getOverallAttendance() < 75) {
                System.out.printf("Student ID: %d, Name: %s %s, SPI: %.2f, Overall Attendance: %.2f%%\n",
                        current.student.getId(), current.student.getFirstName(), current.student.getLastName(),
                        current.student.getSpi(), current.student.getOverallAttendance());
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No students have attendance below 75%.");
        }
    }

    public void clear() {
        head = null;
    }
}
