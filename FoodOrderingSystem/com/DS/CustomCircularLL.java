package com.DS;

import com.customer.*;

public class CustomCircularLL {
    Node head = null;
    Node tail = null;

    class Node {
        Menu data;
        Node next;

        public Node(Menu data) {
            this.data = data;
        }
    }

    public void add(Menu item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
            tail.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
    }

    public void display() {
        if (head == null)
            return;
        Node current = head;
        do {
            current.data.display();
            current = current.next;
        } while (current != head);
    }

    public void clear() {
        head = null;
        tail = null;
    }

    public void print() {
        if (head == null)
            return;

        Node current = head;
        do {
            System.out.printf("%-10d %-30s %-10.2f %-10d %-10d%n", current.data.getItemId(), current.data.getItemName(),
                    current.data.getPrice(), current.data.getRating(), current.data.getQuantity());
            current = current.next;
        } while (current != head);
    }

    public void sortByPriceAsc() {
        if (head == null)
            return;

        Node current = head;
        Node index = null;
        Menu temp;

        do {
            index = current.next;
            while (index != head) {
                if (current.data.getPrice() > index.data.getPrice()) {
                    temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        } while (current != head);
    }

    public void sortByPriceDesc() {
        if (head == null)
            return;

        Node current = head;
        Node index = null;
        Menu temp;

        do {
            index = current.next;
            while (index != head) {
                if (current.data.getPrice() < index.data.getPrice()) {
                    temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        } while (current != head);
    }

    public void sortByRatingAsc() {
        if (head == null)
            return;

        Node current = head;
        Node index = null;
        Menu temp;

        do {
            index = current.next;
            while (index != head) {
                if (current.data.getRating() > index.data.getRating()) {
                    temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        } while (current != head);
    }

    public void sortByRatingDesc() {
        if (head == null)
            return;

        Node current = head;
        Node index = null;
        Menu temp;

        do {
            index = current.next;
            while (index != head) {
                if (current.data.getRating() < index.data.getRating()) {
                    temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        } while (current != head);
    }
}
