package zuo.class08_12;

import java.util.LinkedList;

/**
 * 判断二叉树是否为完全二叉树
 */
public class Code01_IsCBT {

    public class Node{
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * 遍历法（用队列结构）
     * 判断是否为完成二叉树(CBT)逻辑：
     * 1 有一个节点存在右孩子，无左孩子就不是CBT
     * 2 从左右无双全的节点，之后的节点必须是叶节点，否则就不是完全二叉树
     * @param head
     * @return
     */
    public static boolean isCBT01(Node head){
        if(head == null){
            return false;
        }
        // 是否遇到左右孩子不双全的节点
        boolean leaf = false;
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(head);
        while(!queue.isEmpty()) {
            head = queue.poll();
            Node l = head.left;
            Node r = head.right;
            if( (leaf && (l != null || r != null)) || (l == null && r != null) ){
                // 如果遇到左右孩子不双全的节点之后，又发现当前节点不是叶节点（有孩子）
                return false;
            }
            if(l != null){
                queue.add(l);
            }
            if(r != null){
                queue.add(r);
            }
            if(l == null && r == null || l != null && r == null){//由上述条件，可以简化为if(l == null || r == null)
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 递归法
     * 判断是否为完成二叉树(CBT)逻辑
     * @param head
     * @return
     */
    public static boolean isCBT02(Node head){
        return process(head).isCBT;
    }

    public static Info process(Node x){
        if(x == null){
            return new Info(true, true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;

        // 是否为完全二叉树。
        boolean isCBT = false;
        //可能性1,2,3,4种
        if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height){
            isCBT = true;
        }else if(leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height+1){
            isCBT = true;
        }else if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height+1){
            isCBT = true;
        }else if(leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height){
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }

    public static class Info{
        //是否为完全二叉树
        public boolean isFull;
        //是否为满二叉树
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }
}
