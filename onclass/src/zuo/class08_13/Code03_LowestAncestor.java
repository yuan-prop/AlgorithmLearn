package zuo.class08_13;

import java.util.HashMap;
import java.util.HashSet;

public class Code03_LowestAncestor {

    /**
     * 方法一：依旧利用递归套路
     * 给定一棵二叉树的头节点head, 和另外两个节点a和b。返回a和b的最低公共祖先（a和b依次向上，第一个相交的点）。
     *  特殊情况如下，a和b的最低公共祖先是a本身
     *    a
     *   /
     *  b
     * 一、和X节点无关（X不是答案）
     * （1）公共节点在X左子树（左树必包含a,b）
     * （2）公共节点在X右子树（右树必包含a,b）
     * （3）X子树中不全存在a和b
     * 二、X节点就是最低公共祖先
     * （1）X的左树包含b或a，右树包含另一个
     * （2）X作为a,b在X的左树或者右树上
     * （3）X作为b,a在X的左树或者右树上
     *
     * 总之递归过程需要用到的信息：
     *  1需要知道左右子树是否发现a
     *  2需要知道左右子树是否发现b
     *  3 左右子树是有答案（最低公共祖先）
     */
    public static Node lowestAncestor(Node head, Node a, Node b){
        return process(head, a, b).ans;
    }

    public static class Info{
        public boolean findA;
        public boolean findB;
        public Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    public static Info process(Node x, Node a, Node b){
        if(x == null){
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        Node ans = null;
        if(leftInfo.ans != null){
            ans = leftInfo.ans;
        }else if(rightInfo.ans != null){
            ans = rightInfo.ans;
        }else{
            if(findA && findB) {
                ans = x;
            }
        }
        return new Info(findA, findB, ans);
    }

    /**
     * 方法一的简化版（不需要自定义INfo对象）
     * @param head
     * @param a
     * @param b
     * @return
     */
    public static Node lowestAncestor11(Node head, Node a, Node b){
        return process1(head, a, b);
    }

    public static Node process1(Node x, Node a, Node b){
        if(x == null || x == a || x == b){
            return x;
        }
        Node left = process1(x.left, a, b);
        Node right = process1(x.right, a, b);
        if(left != null && right != null){
            return x;
        }
        return left != null ? left : right;
    }


    /**
     * todo 方法二：利用map将每个节点的父节点记录下来
     * a利用map向上获取所有的父节点，加入到set集合中
     * b再利用map依次向上走到cur，如果set中包含cur，则就是最低公共祖先
     */
    public static Node lowestAncestor2(Node x, Node a, Node b){
        if(x == null || x == a || x == b){
            return x;
        }
        HashMap<Node, Node> map = new HashMap<>();
        map.put(x, x);
        getMap(map, x);
        HashSet<Node> set = new HashSet<>();
        set.add(x);
        while(a != x){
            set.add(a);
            a = map.get(a);
        }
        while (b != x){
            if(set.contains(b)){
                return b;
            }
            b = map.get(b);
        }
        return null;
    }

    private static void getMap(HashMap<Node, Node> map, Node node) {
        if(node == null){
            return;
        }
        Node left = node.left;
        Node right = node.right;
        if(left != null){
            map.put(left, node);
        }
        if(right != null){
            map.put(right, node);
        }
        getMap(map, left);
        getMap(map, right);
    }

    public static class Node{
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node r = new Node(0);
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);

        r.left = a;
        a.left = b;
        a.right = c;
        c.right = d;
        System.out.println(lowestAncestor(r, b, d));
        System.out.println(lowestAncestor11(r, b, d));
        System.out.println(lowestAncestor2(r, b, d));

        System.out.println("----------------------");
        System.out.println(lowestAncestor(r, c, d));
        System.out.println(lowestAncestor11(r, c, d));
        System.out.println(lowestAncestor2(r, c, d));

        System.out.println("----------------------");
        System.out.println(lowestAncestor(r, b, c));
        System.out.println(lowestAncestor11(r, b, c));
        System.out.println(lowestAncestor2(r, b, c));


    }

}
