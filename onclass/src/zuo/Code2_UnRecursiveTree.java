package zuo;

import java.util.Stack;

/**
 * 二叉树的非递归遍历
 */
public class Code2_UnRecursiveTree {

    /**
     * 二叉树的前序遍历,利用栈结构
     * @param cur 从根节点开始
     * 核心思想：
     *            1 栈顶出来记为cur
     *            2 有右节点压入右节点，有左压左，先右再左
     */
    public static void pre(Node cur){
        System.out.println("pre-order: ");
        if(cur != null ){
            Stack<Node> stack = new Stack<>();
            // 头先压栈
            stack.add(cur);
            while(!stack.isEmpty()) {
                cur = stack.pop();
                System.out.println(cur.value + " ");
                if(cur.right != null){
                    stack.push(cur.right);
                } else if(cur.left != null) {
                    stack.push(cur.left);
                }
            }
        }
        System.out.println();
    }

    /**
     * 二叉树的中序遍历,利用栈结构
     * @param cur 从根节点开始
     *    核心思想：
     *           1 当前节点记为cur，以cur为头的树，整条左边界入栈，直到遇到空--> cur==null
     *           2 当cur==null,栈中节点弹出，打印，节点的右孩子成为cur
     */
    public static void in(Node cur){
        System.out.println("in-order: ");
        if(cur != null ){
            Stack<Node> stack = new Stack<>();
            while(!stack.isEmpty() || cur != null) {
                // 1 整棵树的左边界先进栈
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {// 2 当左边界到达末尾，右节点进栈
                    cur = stack.pop();
                    System.out.println(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 二叉树的后序遍历,利用栈结构
     * @param cur 从根节点开始
     * 核心思想：
     *            1 创建两个栈，栈顶出来记为cur，弹出放到另一个栈中
     *            2 然后树子节点先左再右，压入栈中
     *            3 重复以上，直到第一个栈空
     */
    public static void pos1(Node cur){
        System.out.println("pos-order: ");
        if(cur != null ){
            Stack<Node> stack = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            // 头先压栈
            stack.push(cur);
            while(!stack.isEmpty()) {
                cur = stack.pop(); //弹出顺序： 头 右 左
                s2.push(cur);
                if(cur.left != null){
                    stack.push(cur.left);
                } else if(cur.right != null) {
                    stack.push(cur.right);
                }
            }
            while(!s2.isEmpty()){ // 左 右 头（后序）
                System.out.println(s2.pop().value + " ");
            }
        }
        System.out.println();
    }


    public static class Node {
        int value;
        public Node left;
        public Node right;

        public Node(int value){
            this.value = value;
            left = null;
            right = null;
        }
    }

}
