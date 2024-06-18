package com.offer.linkedlist;

import java.util.Stack;

/**
 * 链表value是否为回文结构
 * @author wzy
 * @since 2024/6/18
 */
public class PalindromeList {

    public static void main(String[] args) {
        Node list1 = new Node(101, new Node(0, new Node(201, new Node(10, new Node(10, new Node(201, new Node(0, new Node(101))))))));
        System.out.println(list1);
        System.out.println(isPalindrome2(new Node(0, new Node(1, new Node(0)))));
    }

    // 判断一个链表是否为回文结构 时间复杂度达到O(N) 额外空间复杂度达到O(N)
    public static boolean isPalindrome(Node head) {
        // 用栈实现逆序对比
        Stack<Node> stack = new Stack<>();
        Node curr = head;
        while(curr != null) {
            stack.push(curr);
            curr = curr.next;
        }
        while(head != null) {
            if(head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // 面试：如果链表长度为N, 时间复杂度达到O(N) 额外空间复杂度达到O(1)
    public static boolean isPalindrome2(Node head) {
        if(head == null) {
            return true;
        }
        // 快、慢指针
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        boolean ret = true;
        if(fast.next == null) {
            // 链表有奇数个节点
        } else {
            // 链表有偶数个节点
        }
        // slow下一个作为新链表头节点
        Node h2 = slow.next;
        slow.next = null;
        // 先反转链表后半段，再与前半段比较
        h2 = ReverseLinkedList.reverseList(h2);
        Node temp = h2;
        // 后半段链表h2长度 小于等于 前半段链表head长度
        while (h2 != null) {
            if(head.val != h2.val) {
                ret = false;
                break;
            }
            head = head.next;
            h2 = h2.next;
        }
        // 恢复原链表
        h2 = ReverseLinkedList.reverseList(temp);
        slow.next = h2;
        return ret;
    }

}
