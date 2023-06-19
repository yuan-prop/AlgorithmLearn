package com.offer.linkedlist;

/**
 * 合并两个有序的链表
 *
 */
public class ConnectList {

    public static void main(String[] args) {

        ListNode list1 = new ListNode(1);
        ListNode list2 = new ListNode(3);
        ListNode list3 = new ListNode(5);

        ListNode list4 = new ListNode(2);
        ListNode list5 = new ListNode(4);
        ListNode list6 = new ListNode(6);

        list1.next=list2;list2.next=list3;
        list4.next=list5;list5.next=list6;
        ListNode merge = Merge(list1, list4);
        System.out.println(merge);
    }

    public static ListNode Merge(ListNode list1,ListNode list2) {
        ListNode node = null;
        ListNode n = null;
        while(list1 != null || list2 != null){
            ListNode tmp = null;
            if(list1 != null && list2 != null){
                if(list1.val <= list2.val){
                    tmp = list1;
                    list1 = list1.next;
                }else{
                    tmp = list2;
                    list2 = list2.next;
                }
            }else if(list1 != null){
                tmp = list1;
                list1 = list1.next;
            } else if(list2 != null){
                tmp = list2;
                list2 = list2.next;
            }
            if(node == null){
                n = tmp;
                node = n;
            }else {
                n.next = tmp;
                n = n.next;
            }
        }
        return node;
    }
}
