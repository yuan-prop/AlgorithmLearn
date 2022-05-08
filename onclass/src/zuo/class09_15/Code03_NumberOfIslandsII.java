package zuo.class09_15;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 力扣 岛问题（345. Number of Islands II）：
 * 给定一个二维数组matrix,初始时各位置全为0，每次将任一0位置设置为1，求每次得到的岛个数
 * 上、下、左、右相邻（斜向相邻不算）的1认为是一片岛
 */
public class Code03_NumberOfIslandsII {


    /*****************并查集法求解**********************/

    // m行n列的矩阵， positions是列数为2的二维数组，表示每次将矩阵的(x,y)位置设置为1 如： { {0,0},{1,1},{2,3}...{x行,y列} } x<=m,y<=n
    public static List<Integer> numIslands21(int m, int n, int[][] positions){
        UnionFind uf = new UnionFind(m, n);
        List<Integer> ans = new ArrayList<>();
        for(int[] position: positions){
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind {
        // 存储当前节点的父节点的下标
        private int[] parent;
        // 当前节点所在的集合大小
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        // 集合总个数
        private int sets;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int r, int c){
            // 二维数组下标对应的一维数组下标
            return r * col + c;
        }

        private int connect(int i, int j){
            int index = index(i, j);
            // size[index] == 0表示第一次到这个位置
            if(size[index] == 0){
                // 先初始化自身
                parent[index] = index;
                size[index] = 1;
                sets++;
                // 联结上下左右
                union(i, j, i - 1, j);
                union(i, j, i + 1, j);
                union(i, j, i, j - 1 );
                union(i, j, i, j + 1 );
            }
            return sets;
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
            // 是否越界
            if(r1 <0|| r1==row ||r2 < 0 || r2 == row || c1 < 0 ||c1==col || c2 < 0 || c2 == col){
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // 相邻两个都为1才连
            if(size[i1] == 0 || size[i2] == 0){
                return;
            }
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
