package zuo.class10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * 图的深度优先遍历
 *
 * 过程可根据栈的结构 画图分析
 * @Author: zhenyuan
 * @Date: 2022/6/11
 */
public class Graph_DFS {

    public static void dfs(Node node){
        if(node == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            for(Node next : cur.nexts){
                if(!set.contains(next)){
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }

    public static void dfsRecursive(Node node, HashSet<Node> set){
        if(node == null){
            return;
        }
        set.add(node);
        System.out.println(node.value);
        for(Node next : node.nexts){
            if(!set.contains(next)) {
                dfsRecursive(next, set);
            }
        }
    }


    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);

        node1.nexts = new ArrayList<>(Arrays.asList(node2, node3, node4));
        node2.nexts = new ArrayList<>(Arrays.asList(node5));
        node5.nexts = new ArrayList<>(Arrays.asList(node6));
        node3.nexts = new ArrayList<>(Arrays.asList(node7));
        node4.nexts = new ArrayList<>(Arrays.asList(node8));
        // 环
        node8.nexts = new ArrayList<>(Arrays.asList(node1));

        dfs(node1);

        System.out.println("---------");
        dfsRecursive(node1, new HashSet<>());

    }

}
