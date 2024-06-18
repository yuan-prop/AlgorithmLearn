package com.offer.linkedlist;

/**
 * 对链表按照值大小分区
 * @author wzy
 * @since 2024/6/18
 */
public class ListPartition {

    public static void main(String[] args) {
        Node list1 = new Node(10, new Node(5, new Node(3, new Node(2, new Node(5, new Node(8, new Node(9, new Node(7))))))));
        System.out.println(list1);
        Node list2 = partitionLinkedList(list1, 7);
        System.out.println(list2);
    }

    // 对链表分区，小于的放左边，等于放中间，大于放右边
    public static Node partitionLinkedList(Node head, int pivot) {
        // 小于区的头尾节点
        Node sh = null;
        Node st = null;
        // 等于区的头尾节点
        Node eh = null;
        Node et = null;
        // 大于区的头尾节点
        Node bh = null;
        Node bt = null;
        Node next;
        while(head != null) {
            next = head.next;
            // 断开，去除旧指针影响导致的问题（如形成环）
            head.next = null;
            if(head.val < pivot) {
                if(sh == null) {
                    sh = head;
                } else {
                    st.next = head;
                }
                st = head;
            } else if(head.val > pivot) {
                if(bh == null) {
                    bh = head;
                } else {
                    bt.next = head;
                }
                bt = head;
            } else {
                if(eh == null) {
                    eh = head;
                }else {
                    et.next = head;
                }
                et = head;
            }
            head = next;
        }
        // 判空处理
        if(st == null) {
            if(et != null) {
                et.next = bh;
                return eh;
            } else {
                return bh;
            }
        } else {
            if(et == null) {
                st.next = bh;
            } else {
                st.next = eh;
                et.next = bh;
            }
            return sh;
        }
    }
}
