package com.offer.linkedlist;

public class Node {
    int val;
    Node next;

    Node(int val) {
        this.val = val;
    }

    Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return val + "->" + next;
    }
}
