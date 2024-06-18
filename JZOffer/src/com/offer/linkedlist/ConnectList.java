package com.offer.linkedlist;

/**
 * 合并两个有序的链表
 *
 */
public class ConnectList {

    public static void main(String[] args) {

        Node list1 = new Node(1);
        Node list2 = new Node(3);
        Node list3 = new Node(5);

        Node list4 = new Node(2);
        Node list5 = new Node(4);
        Node list6 = new Node(6);

        list1.next=list2;list2.next=list3;
        list4.next=list5;list5.next=list6;
        Node merge = Merge(list1, list4);
        System.out.println(merge);
    }

    public static Node Merge(Node list1, Node list2) {
        Node node = null;
        Node n = null;
        while(list1 != null || list2 != null){
            Node tmp = null;
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
