package zuo.class10;

import java.util.HashMap;

/**
 * 一个图中从源节点出发求到各点的最短路径 (Dijkstra算法)
 * 适用范围：不能有累加和为负数的环
 */
public class Code01_Dijkstra {

    public static void main(String[] args) {
        // 创建图（参考 Dijkstra算法各点最短距离.png）
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


        HashMap<Node, Integer> res = dijkstra2(a, 5);

        for(Node node : res.keySet()){
            System.out.println("a到"+(char) node.value+" -> " +res.get(node));
        }
    }

    /**
     * 改进后的dijkstra算法
     * 从head出发，所有head能到达的节点中，生成到达每个节点的最小路径记录并返回
     * @return
     */
    public static HashMap<Node, Integer> dijkstra2(Node head, int size){
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while(!nodeHeap.isEmpty()){
            NodeRecord nodeRecord = nodeHeap.pop();
            Node cur = nodeRecord.node;
            int distance = nodeRecord.distance;
            for(Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    public static class NodeRecord{
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    /**
     * 加强堆
     */
    public static class NodeHeap{
        // 堆
        private Node[] nodes;
        // 索引表，node -> 堆上的位置（数组下标），这是加强堆的关键。
        // 如果value值为-1表示该节点的所有直接路径已经处理过不用再考虑
        private HashMap<Node, Integer> heapIndexMap;
        // 原出发点到当前节点的最小距离
        private HashMap<Node, Integer> distanceMap;
        // 堆中的记录数
        private int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            this.size = 0;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        /**
         * 当前节点node, 从源节点出发到达node的距离为distance
         * 判断要不要更新
         * @param node
         * @param distance
         */
        public void addOrUpdateOrIgnore(Node node, int distance){
            if(inHeap(node)){ // update
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                // 距离只可能变小，所以向上调整堆结构
                insertHeapify(node, heapIndexMap.get(node));
            }
            if(!isEntered(node)){ // add
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
            // ignore
        }

        public NodeRecord pop(){
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            // 堆顶元素(最小元素)和最后一个元素互换
            swap(0, size - 1);
            // 最后一个节点（也即pop出去的）下标改成-1
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // 如果是C++，须把原本堆顶节点释放 free
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        // 往堆中加元素，从下往上重新调整堆结构(小根堆，节点大小由源节点到当前节点的distance表示)
        private void insertHeapify(Node node, int index){
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])){
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 自顶向下heapify（小根堆）
        private void heapify(int index, int size){
            int left = index * 2 + 1;
            while(left < size){
                int smallest = left + 1 < size && distanceMap.get(nodes[left]) > distanceMap.get(nodes[left + 1])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[index]) < distanceMap.get(nodes[smallest]) ? index : smallest;
                if(smallest == index){
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        // 是否已经进入过堆
        private boolean isEntered(Node node){
            return heapIndexMap.containsKey(node);
        }

        // 是否在堆上
        private boolean inHeap(Node node){
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2){
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }

    }

}
