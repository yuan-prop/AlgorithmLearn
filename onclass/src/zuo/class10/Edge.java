package zuo.class10;

/**
 * 图的边
 */
public class Edge {

    // 边的权重
    public int weight;
    public Node from;
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }


}
