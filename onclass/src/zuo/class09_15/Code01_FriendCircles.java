package zuo.class09_15;


/**
 * 并查集的应用
 *
 *
 * 力扣题：N个人组成的朋友圈个数
 * 将N想像成N*N的方阵，对角是1(自己认识自己)，0代表不认识
 */
public class Code01_FriendCircles {

    public static int findCircleNum(int[][] M){
        int N = M.length;
        // {0} {1} {2} ... {N-1}
        UnionFind unionFind = new UnionFind(N);
        // 遍历矩阵M的(方阵)右上半部分
        for(int i = 0; i < N; i++){
            for(int j = i; j < N; j++){
                if(M[i][j] == 1){// 如果i和j互相认识
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;
    }

    public static class UnionFind {
        // parent[i] = k i的父节点的下标是k
        private int[] parent;
        // size[i] = k, 表示i所在集合的大小为k，如果i是集合的代表节点，size[i]才有意义
        private int[] size;

        // 辅助容器
        private int[] help;
        // 一共有多少集合
        private int sets;

        public UnionFind(int N){
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for(int i = 0; i < N; i++){
                // 刚开始自己的父节点就是自己
                parent[i] = i;
                size[i] = 1;
            }
        }

        // 从i节点开始一起往上，往上到不能再往上时，即找到了代表节点(头节点)，返回
        private int find(int i){
            int hi = 0;
            while(i != parent[i]){
                // 将往上找“代表节点”所经历路径的节点记录下来，方便后面缩短路径
                help[hi++] = i;
                i = parent[i];
            }
            // 此时i == parent[i],i就是代表节点
            for(hi--; hi >=0; hi--){
                // 缩短路径,路径中节点的父都直接挂到代表节点
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            // 找到i,j各自的头节点
            int f1 = find(i);
            int f2 = find(j);
            if(f1 != f2){
                if(size[f1] >= size[f2]){
                    // f2挂到f1 形成新的集合
                    parent[f2] = f1;
                    size[f1] += size[f2];
                }else{
                    parent[f1] = f2;
                    size[f2] += size[f1];
                }
                sets--;
            }
        }

        public int sets(){
            return sets;
        }
    }

}
