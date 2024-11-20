package com.DS;

import com.customer.CartItem;

public class CartLinkedList {
    public static Node head;

    public static class Node {
        public CartItem data;
        public Node next;

        Node(CartItem data) {
            this.data = data;
            this.next = null;
        }
    }

    public static void add(CartItem item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void remove(CartItem item) {
        if (head == null)
            return;

        if (head.data.getItemId() == item.getItemId()) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.getItemId() == item.getItemId()) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    public CartItem find(int itemId) {
        Node current = head;
        while (current != null) {
            if (current.data.getItemId() == itemId) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void printCartItems() {
        Node current = head;
        while (current != null) {
            CartItem item = current.data;
            System.out.printf("%-10d %-30s %-20s %-10d%n",
            item.getItemId(),
            item.getItemName(),
            item.getRestaurantName(),
            item.getQuantity());
            current = current.next;
        }
    }

    public CartItem get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        Node current = head;
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                return current.data;
            }
            current = current.next;
            currentIndex++;
        }
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    public int size() {
        int size = 0;
        Node current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    public void clear() {
        head = null;
    }
}
