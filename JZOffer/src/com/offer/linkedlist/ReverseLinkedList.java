package com.offer.linkedlist;


/**
 * 给定一个单链表的头结点pHead，长度为n，反转该链表后，返回新链表的表头。
 *
 * 数据范围： n ≤ 1000
 * 要求：空间复杂度 O(1) ，时间复杂度 O(n)。
 *
 * 如当输入链表{1,2,3}时，
 * 经反转后，原链表变为{3,2,1}，所以对应的输出为{3,2,1}。
 *
 * 核心思想：头尾指针的应用
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(3);
        Node n3 = new Node(5);
        Node n4 = new Node(9);
        Node n5 = new Node(8);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        System.out.println(n1);
        System.out.println(deleteNode(n1, n3));

        System.out.println("---------------------------");

        Node head = new Node(10, new Node(5, new Node(3, new Node(2, new Node(5, new Node(8, new Node(9, new Node(7))))))));
        System.out.println(head);
        // 反转
        Node node = reverseList(head);
        System.out.println(node);
        // 再反转
        System.out.println(process(node));
    }

    public static Node process(Node head){
        if(head == null) {
            return null;
        }
        return reverseRecursive(null, head);
    }

    // 递归反转链表
    public static Node reverseRecursive(Node pre, Node head){
        if(head != null) {
            Node next = head.next;
            head.next = pre;
            pre = head;
            return reverseRecursive(pre, next);
        }
        return pre;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public static Node reverseList(Node head) {
        Node pre = null;
        Node next;
        //当前节点是head，pre为当前节点的前一节点，next为当前节点的下一节点
        //需要pre和next的目的是让当前节点从pre->head->next1->next2变成pre<-head next1->next2
        //即pre让节点可以反转所指方向，但反转之后如果不用next节点保存next1节点的话，此单链表就此断开了
        //所以需要用到pre和next两个节点
        //1->2->3->4->5
        //1<-2<-3 4->5
        while(head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    //todo 双向链表的反转，类似单链表的方法

    // 删除链表中指定的节点, 返回删除后的链表 O(N)
    public static Node deleteNode(Node head, Node delete){
        if(head == null || delete == null) {
            return head;
        }
        if(head == delete) {
            return head.next;
        }
        Node cur = head;
        while(cur.next != null) {
            if(cur.next == delete) {
                cur.next = cur.next.next;
                break;
            }
            cur = cur.next;
        }
        return head;
    }

    // O(1)时间删除链表中指定的节点（李代桃僵）
    public static Node deleteNode2(Node head, Node delete){
        if(head == null || delete == null) {
            return head;
        }
        if(head == delete) {
            return head.next;
        }
        if(delete.next != null) {
            // 将待删除节点的val值设置成其后一个节点的值
            delete.val = delete.next.val;
            delete.next = delete.next.next;
        } else {
            Node cur = head;
            while(cur.next != null) {
                if(cur.next == delete) {
                    cur.next = cur.next.next;
                    break;
                }
                cur = cur.next;
            }
        }
        return head;
    }

}
