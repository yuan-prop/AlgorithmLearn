package zuo.class09_15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * 力扣 岛问题（200. Number of Islands）：
 * 给定一个二维数组matrix,里面的值不是1就是0，
 * 上、下、左、右相邻（斜向相邻不算）的1认为是一片岛，返回matrix中岛的数量
 */
public class Code02_NumberOfIslands {


    /**
     * 感染周边区域法：利用递归法解，复杂度O(M*N)
     * @param board
     * @return
     */
    public static int numIslands3(char[][] board){
        int islands = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][i] == '1'){
                    islands++;
                    infect(board, i, j);
                }
            }
        }
        return islands;
    }

    // 从(i,j)位置出发，将连成一片的'1字符'，改成0
    public static void infect(char[][] board, int i, int j){
        if(i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] == '1'){
            return;
        }
        //将'1'改成0，不改掉'1'的话递归无法结束
        board[i][j] = 0;
        infect(board, i-1, j);
        infect(board, i+1, j);
        infect(board, i, j-1);
        infect(board, i, j+1);
    }

    /*****************并查集解法 方法一开始**********************/
    /**
     * 并查集：方法一（利用hash表结构）
     * 每个'1'节点查看自己左边或上边的节点是否为'1'
     */
    public static int numIslands1(char[][] board){
        int row  = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(board[i][j] == '1'){
                    dots[i][j] = new Dot();//不同的内存地址区分
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        // 第一行特殊处理
        for(int j = 1; j < col; j++){
            if(board[0][j - 1] == '1' && board[0][j] == '1'){
                // 左边是'1'，合并
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        // 第一列特殊处理
        for(int i = 1; i < row; i++){
            if(board[i][0] == '1' && board[i-1][0] == '1'){
                // 上边是'1'，合并
                uf.union(dots[i][0], dots[i-1][0]);
            }
        }
        for(int i = 1; i < row; i++) {
            for(int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j-1] == '1') {
                        uf.union(dots[i][j-1], dots[i][j]);
                    }
                    if (board[i-1][j] == '1') {
                        uf.union(dots[i-1][j], dots[i][j]);
                    }
                }
            }
        }
        return uf.sets();
    }

    public static class UnionFind1<V>{
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for(V cur : values){
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public void union(V a, V b){
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if(aHead != bHead){
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big;
                Node<V> small;
                if(aSetSize >= bSetSize){
                    big = aHead;
                    small = bHead;
                }else{
                    big = bHead;
                    small = aHead;
                }
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets(){
            return sizeMap.size();
        }

        public boolean isSameSet(V a, V b){
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        // 给定一个节点，往上到不能再往上，找到头节点（也叫集合的代表节点）
        private Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while(cur != parents.get(cur)){
                path.push(cur);
                cur = parents.get(cur);
            }
            // cur == parents.get(cur),此时cur到了头节点
            // 将路径缩短
            while(!path.isEmpty()){
                Node<V> pop = path.pop();
                parents.put(pop, cur);
            }
            return cur;
        }
    }

    public static class Node<V>{
        V value;
        public Node(V value) {
            this.value = value;
        }
    }

    public static class Dot{}

    /*****************并查集 方法一结束**********************/


    /*****************并查集 方法二开始**********************/

    public static int numIslands2(char[][] board){
        int row = board.length;
        int col = board[0].length;
        UnionFind2 uf = new UnionFind2(board);
        // 每一行
        for(int j = 1; j < col; j++){
            if(board[0][j] == '1' && board[0][j-1] == '1'){
                uf.union(0, j, 0, j-1);
            }
        }
        // 第一列
        for(int i = 1; i < row; i++){
            if(board[i][0] == '1' && board[i-1][0] == '1'){
                uf.union(i, 0, i-1, 0);
            }
        }
        // 遍历其他行列
        for(int i = 2; i < row; i++){
            for(int j = 2; i < col; j++){
                if(board[i][j] == '1') {
                    if (board[i - 1][j] == '1') {
                        uf.union(i, j, i - 1, j);
                    }
                    if (board[i][j - 1] == '1') {
                        uf.union(i, j, i, j - 1);
                    }
                }
            }
        }
        return uf.sets;
    }

    public static class UnionFind2 {
        // 存储当前节点的父节点的下标
        private int[] parent;
        // 当前节点所在的集合大小
        private int[] size;
        private int[] help;
        private int col;
        // 集合总个数
        private int sets;

        public UnionFind2(char[][] board) {
            col = board[0].length;
            sets = 0;
            int row = board.length;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    if(board[i][j] == '1'){
                        int index = index(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }

        }

        private int index(int r, int c){
            // 二维数组下标对应的一维数组下标
            return r * col + c;
        }


        // 找出当前节点的头节点（集合的代表节点）下标
        private int find(int i){
            int hi = 0;
            while(i != parent[i]){
                help[hi++] = i;
                i = parent[i];
            }
            //此时 i == parent[i]
            // 缩短路径
            for(--hi; hi >= 0; hi--){
                parent[help[hi]] = i;
            }
            return i;
        }

        // 合并两个下标对应节点所在的两个集合
        public void union(int r1, int c1, int r2, int c2){
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            int f1 = find(i1);
            int f2 = find(i2);
            // 两个集合头节点不同时（还未合并）
            if(f1 != f2){
                if(size[f1] >= size[f2]){
                    parent[f2] = f1;
                    size[f1] += size[f2];
                }
                if(size[f2] >= size[f1]){
                    parent[f1] = f2;
                    size[f2] += size[f1];
                }
                sets--;
            }
        }
    }


}
