package zuo.class10;

import java.util.*;

/**
 * 最小生成树算法之Prim
 * 1 可以从任意节点出发来寻找最小生成树
 * 2 某个节点加入到被选取的点中后，解锁这个点出发的所有新的边
 * 3 在所有解锁的边中选最小的边，然后看这个边会不会形成环
 * 4 如果会，不要这条边，继续考察剩下解锁的边中最小的边，重复3
 * 5 如果不会，要这条边，将该边的指向点加入到被选取点中，重复2
 * 6 当所有点都被选取，最小生成树就得到了
 */
public class Code05_Prim {

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> primMST(Graph gragh){
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

        // 哪些点被解锁出来了
        HashSet<Node> nodeSet = new HashSet<>();
        Set<Edge> result = new HashSet<>(); //依次挑选的边放入result里
        // 用for是考虑图可能是森林（多个不连通的 子图）
        for(Node node : gragh.nodes.values()){
            //node是开始节点
            if(!nodeSet.contains(node)){
                nodeSet.add(node);
                for(Edge edge : node.edges){// 由一个点，解锁所有相邻的边
                    priorityQueue.add(edge);
                }
                while(!priorityQueue.isEmpty()){
                    Edge edge = priorityQueue.poll();//弹出解锁的边中最小的边
                    Node toNode = edge.to; // 可能的一个新的点
                    if(!nodeSet.contains(toNode)){// 不含有，就是新点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for(Edge nextEdge : toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Node a = new Node('a');
        Node b = new Node('b');
        Node c = new Node('c');
        Node d = new Node('d');
        Node e = new Node('e');

        Edge ab = new Edge(1, a, b);
        Edge ac = new Edge(6, a, c);
        Edge ad = new Edge(4, a, d);
        Edge bc = new Edge(1, b, c);
        Edge be = new Edge(6, b, e);
        Edge ce = new Edge(2, c, e);
        Edge cd = new Edge(1, c, d);
        Edge de = new Edge(5, d, e);

        a.edges.add(ab);
        a.edges.add(ac);
        a.edges.add(ad);
        b.edges.add(bc);
        b.edges.add(be);
        c.edges.add(cd);
        c.edges.add(ce);
        d.edges.add(de);

        Graph graph = new Graph();
        graph.nodes.put(1, a);
        graph.nodes.put(2, b);
        graph.nodes.put(3, c);
        graph.nodes.put(4, d);
        graph.nodes.put(5, e);

        graph.edges.addAll(Arrays.asList(ab,ac,ad,bc,be,ce,cd,de));

        Set<Edge> edges = primMST(graph);
        for(Edge edge : edges) {
            System.out.println((char)edge.from.value + "->" +(char)edge.to.value + "    " + edge.weight);
        }
    }
}
