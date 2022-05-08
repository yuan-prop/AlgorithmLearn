package zuo.class10;

import java.util.*;


/**
 * 最小生成树算法之Kruskal贪心算法(利用并查集)
 * 1 总是从权值最小的边开始考虑，依次考虑权值依次变大的边
 * 2 当前的边要么进入最小生成树的集合，要么丢弃
 * 3 如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 4 如果当前的边进入最小生成树的集合中形成环，就不要当前边
 * 5考察完所有边之后，最小生成树的集合也得到了
 */
public class Code04_Kruskal {

    // Union-Find Set
    public static class UnionFind{
        //key 某一个节点，value key节点往上的节点
        private HashMap<Node, Node> fatherMap;

        // key 某一个集合的代表节点，value：key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;


        public UnionFind(){
            fatherMap = new HashMap<Node, Node>();
            sizeMap = new HashMap<Node, Integer>();
        }

        public void makeSets(Collection<Node> nodes){
            fatherMap.clear();
            sizeMap.clear();
            for(Node n : nodes){
                fatherMap.put(n, n);
                sizeMap.put(n, 1);
            }
        }

        // 寻找当前节点所在集合的代表节点
        private Node findFather(Node n){
            Stack<Node> path = new Stack<>();
            while(n != fatherMap.get(n)){
                path.add(n);
                n = fatherMap.get(n);
            }
            while(!path.isEmpty()){
                fatherMap.put(path.pop(), n);
            }
            return n;
        }

        public boolean isSameSet(Node a, Node b){
            return fatherMap.get(a) == fatherMap.get(b);
        }

        public void union(Node a, Node b){
            if(a == null || b == null){
                return;
            }
            Node aDai = findFather(a);
            Node bDai = findFather(b);
            if(aDai != bDai){
                Integer aSetSize = sizeMap.get(aDai);
                Integer bSetSize = sizeMap.get(bDai);
                if(aSetSize >= bSetSize){
                    fatherMap.put(bDai, aDai);
                    sizeMap.put(aDai, aSetSize + bSetSize);
                    sizeMap.remove(bDai);
                }else{
                    fatherMap.put(aDai, bDai);
                    sizeMap.put(bDai, bSetSize + aSetSize);
                    sizeMap.remove(aDai);
                }
            }
        }
    }

    public static class EdgeComparator implements Comparator<Edge>{

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     * Kruskal主方法
     */
    public static Set<Edge> KruskalMST(Graph gragh){
        UnionFind uf = new UnionFind();
        uf.makeSets(gragh.nodes.values());
        // 从小边到大边，依次弹出，（小根堆）
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for(Edge edge : gragh.edges){// M 条边
            priorityQueue.add(edge); // O(logM)
        }
        Set<Edge> result = new HashSet<>();
        while(!priorityQueue.isEmpty()){
            Edge edge = priorityQueue.poll();
            if(!uf.isSameSet(edge.from, edge.to)){
                result.add(edge);
                uf.union(edge.from, edge.to);
            }
        }
        return result;
    }

}
