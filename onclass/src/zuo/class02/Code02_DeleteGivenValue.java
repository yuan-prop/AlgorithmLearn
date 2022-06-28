package zuo.class02;

/**
 * @Author: zhenyuan
 * @Date: 2022/6/21
 */
public class Code02_DeleteGivenValue {

    public static class Node{
        public int value;
        public Node next;

        public Node(int v){
            this.value = v;
        }
    }

    /**
     * 删除链表中值为num的节点
     * @param head
     * @param num
     * @return
     */
    public static Node removeValue(Node head, int num){
        // 边界条件，head来到第一个不需要删的位置
        while(head != null){
            if(head.value != num){
                break;
            }
            head = head.next;
        }
        // 此时 head.value != num || head == null
        Node pre = head;
        Node cur = head;
        while (cur != null){
            if(cur.value == num){
                pre.next = cur.next;
            }else{
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(2);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;

        Node node = removeValue(n1, 2);
        while(node != null){
            System.out.println(node.value);
            node = node.next;
        }
    }

}
