package com.offer.linkedlist;

/**
 * 两个链表相交的一系列问题
 * 给定两个可能有环也可能无环的单链表头节点head1和head2，请实现一个函数，
 * 如果两个链表相交，请返回相交的第一个节点，如果不相交，返回null
 * 要求如果两个链表长度之和为N,时间复杂度请达到O(N) ，额外空间复杂度O(1)
 * @author wzy
 * @since 2024/6/18
 */
public class ListCross {

    public static void main(String[] args) {
        Node node = new Node(4);
        Node head = new Node(1, new Node(2, new Node(3, node)));
        Node node1 = new Node(7);
        Node node2 = new Node(5, new Node(6, node1));
        node.next = node2;
        node1.next = node;
        Node loop = getLoopNode(head);
        System.out.println(loop.val);
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        if(head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if(loop1 == null && loop2 == null) {
            // 1 判断两个无环链表是否相交
            return noLoop(head1, head2);
        }
        // 2 一个有环，一个无环的两个链表不可能相交!

        // 3 判断两个有环链表是否相交
        if(loop1 != null && loop2 != null) {
            return bothLoop(head1,loop1,head2,loop2);
        }
        return null;
    }


    /**
     * 判断单链表是否有环，并返回第一个入环节点
     * 使用快慢指针，快指针一次两步，慢指针一次一步
     * @param head
     * @return 返回null说明无环
     */
    public static Node getLoopNode(Node head) {
        if(head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while(slow != fast) {
            if(fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        // 相遇时，将快指针指向头节点
        fast = head;
        while(slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        // 再相遇时，相遇的点就是第一个入环节点
        return fast;
    }

    // 如果两个链表都无环，返回第一个相交点，如果不相交，返回null
    public static Node noLoop(Node head1, Node head2) {
        if(head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while(cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while(cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if(cur2 != cur1) {
            return null;
        }
        if(n > 0) {
            // 说明head1长
            cur1 = head1;
            cur2 = head2;
        } else {
            n = -n;
            cur1 = head2;
            cur2 = head1;
        }
        while(n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 两个有环链表，返回第一个相交节点，如果不相交返回null
     * 两个有环链表有2种情况：
     *  1 两个链表不相交
     *  2 两个链表共用环(交点在环外或交点在环上)
     * @param head1
     * @param loop1
     * @param head2
     * @param loop2
     * @return
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        if(loop1 == loop2) {
            // 交点在环外，找交点类似无环链表
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while(cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while(cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            if(cur2 != cur1) {
                return null;
            }
            if(n > 0) {
                // 说明head1长
                cur1 = head1;
                cur2 = head2;
            } else {
                n = -n;
                cur1 = head2;
                cur2 = head1;
            }
            while(n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }else {
            cur1 = loop1.next;
            // 在head1环上转一圈看是否有和loop2的交点
            while (cur1 != loop1) {
                if(cur1 == loop2) {
                    return loop1;// 或者return loop2
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

}
