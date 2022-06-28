package zuo.class10;

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

}
