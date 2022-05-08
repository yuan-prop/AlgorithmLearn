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

    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;


    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
