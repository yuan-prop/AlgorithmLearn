package zuo.class10;

import java.util.ArrayList;

/**
 * 图中的点结构描述
 */
public class Node {

    public int value;
    // 入度
    public int in;
    // 出度
    public int out;

    // 后继邻居
    public ArrayList<Node> nexts;
    // 从它出发的有哪些边
    public ArrayList<Edge> edges;


    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
