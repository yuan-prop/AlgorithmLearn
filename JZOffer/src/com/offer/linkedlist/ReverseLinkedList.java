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
    private boolean first = true;
    ListNode last = null;

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(3);
        ListNode list3 = new ListNode(5);
        list1.next=list2;list2.next=list3;
        System.out.println(list1);
//        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
//        ListNode reverse = reverseLinkedList.ReverseList(list1);
//        System.out.println(reverse);
        d(list1);
    }

    public static void d(ListNode head){
        if(head.next == null) {
            return;
        }
        ListNode node = new ListNode(0);
        recurseList(null, head, node);
        System.out.println(node.next);
    }

    // 递归反转链表
    public static void recurseList(ListNode pre, ListNode cur, ListNode n){
        if(cur.next != null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            recurseList(pre, next, n);
        }else{
            cur.next = pre;
            n.next = cur;
        }
    }

    public ListNode ReverseList(ListNode head) {
        if(head == null) return null;
        ListNode cur = head;
        ListNode pre =null;
        ListNode next=null;
        //当前节点是cur，pre为当前节点的前一节点，next为当前节点的下一节点
        //需要pre和next的目的是让当前节点从pre->cur->next1->next2变成pre<-cur next1->next2
        //即pre让节点可以反转所指方向，但反转之后如果不用next节点保存next1节点的话，此单链表就此断开了
        //所以需要用到pre和next两个节点
        //1->2->3->4->5
        //1<-2<-3 4->5
        while(cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    //todo 双向链表的反转，类似单链表的方法

    //todo 删除链表中指定的节点 num
}
