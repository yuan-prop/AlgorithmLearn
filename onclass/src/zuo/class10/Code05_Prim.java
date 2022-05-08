package zuo.class10;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

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
}
