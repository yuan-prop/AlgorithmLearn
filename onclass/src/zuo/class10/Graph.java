package zuo.class10;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图
 */
public class Graph {

    // key：城市编号 value:城市这个节点
    public HashMap<Integer, Node> nodes;

    public HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }
}
