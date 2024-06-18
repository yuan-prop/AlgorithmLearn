package com.offer.linkedlist;

import java.util.HashMap;

/**
 * 复制含有随机指针节点的链表
 * 给定一个由Node节点类型组成的无环单链表的头节点head,
 * 请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点
 * 要求时间复杂度O(N) 额外空间复杂度O(1)
 * @author wzy
 * @since 2024/6/18
 */
public class LinkedListCopy {

    public static void main(String[] args) {
        Node n1 = new Node(3);
        Node n2 = new Node(6);
        Node n3 = new Node(7);
        Node n4 = new Node(2);
        Node n5 = new Node(4);
        n1.next = n2;n1.rand = n3;
        n2.next = n3;n2.rand = n1;
        n3.next = n4;
        n4.next = n5;
        System.out.println(n1);
        Node node = copyList2(n1);
        System.out.println(node);
    }

    static class Node {
        int value;
        Node next;
        // rand是单链表节点结构中新增的指针，rand可能指向链表中任意一个节点，也可能指向null
        Node rand;
        Node (int val) {
            this.value = val;
        }

        @Override
        public String toString() {
            if (rand != null) {
                return value + "^" + rand.value + "->" + next;
            } else {
                return value + "->" + next;
            }
        }
    }

    // 简单方法：时间复杂度O(N) 额外空间复杂度O(N)
    public static Node copyList(Node head) {
        if(head == null) {
            return null;
        }
        // map key: 旧节点，value: 由旧节点复制的新节点
        HashMap<Node, Node> map = new HashMap<>();
        Node curr = head;
        while(curr != null) {
            map.put(curr, new Node(curr.value));
            curr = curr.next;
        }
        curr = head;
        while(curr != null) {
            map.get(curr).next = map.get(curr.next);
            map.get(curr).rand = map.get(curr.rand);
            curr = curr.next;
        }
        return map.get(head);
    }

    // 时间复杂度O(N) 额外空间复杂度O(1)
    public static Node copyList2(Node head) {
        if(head == null) {
            return null;
        }
        Node curr = head;
        while(curr != null) {
            // 在原链表中将新节点加在旧节点之后
            // 1 -> 2 -> 3 变成 1 -> 1` -> 2 -> 2` -> 3 -> 3`
            Node next = curr.next;
            Node node = new Node(curr.value);
            curr.next = node;
            node.next = next;
            curr = next;
        }
        curr = head;
        Node copyCurr;
        while(curr != null) {
            copyCurr = curr.next;
            if(curr.rand != null) {
                copyCurr.rand = curr.rand.next;
            }
            curr = copyCurr.next;
        }
        // 将新节点从链表中分离
        curr = head;
        Node head2 = head.next;
        while(curr != null) {
            copyCurr  = curr.next;
            curr.next = copyCurr.next;
            if(copyCurr.next != null) {
                copyCurr.next = copyCurr.next.next;
            }
            curr = curr.next;
        }
        return head2;
    }

}
