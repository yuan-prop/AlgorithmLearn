package zuo.class18;

public class Code03_CardsInLine {

    /**
     * 根据规则，返回获胜者的分数
     * 纯暴力递归
     */

    public static int win1(int[] arr){
        if(arr == null || arr.length == 0){
            return 0;
        }
        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    // arr[L...R] 先手获得最大分数返回
    public static int f1(int[] arr, int L, int R){
        if(L == R){
            // 只有一张牌了
            return arr[L];
        }
        int p1 = arr[L] + g1(arr, L + 1, R);
        int p2 = arr[R] + g1(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    // arr[L...R] 后手获得最小分数返回
    public static int g1(int[] arr, int L, int R){
        if(L == R){
            return 0;
        }
        int p1 = f1(arr, L + 1, R); // 若对手（先手）拿走了L位置的数
        int p2 = f1(arr, L, R - 1); // 若对手（先手）拿走了R位置的数
        return Math.min(p1, p2);
    }

    /**
     * 动态规划一：傻缓存法
     */
    public static int win2(int[] arr){
        if(arr == null || arr.length == 0){
            return 0;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for(int i = 0;i<N;i++){
            for(int j=0;j<N;j++){
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    // arr[L...R] 先手获得最大分数返回
    public static int f2(int[] arr, int L, int R,int[][] fmap, int[][] gmap){
        if(fmap[L][R] != -1){
            return fmap[L][R];
        }
        int ans = 0;
        if(L == R){
            // 只剩一张牌
            ans = arr[L];
        }else{
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[L][R] = ans;

        return ans;
    }

    // arr[L...R] 后手获得最小分数返回
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap){
        if(gmap[L][R] != -1){
            return gmap[L][R];
        }
        int ans = 0;
        if(L != R){
            int p1 = f2(arr, L + 1, R, fmap, gmap); // 若对手（先手）拿走了L位置的数
            int p2 = f2(arr, L, R - 1, fmap, gmap); // 若对手（先手）拿走了R位置的数
            ans = Math.min(p1, p2);
        }
        gmap[L][R] = ans;
        return ans;
    }

    /**
     * 优化后的动态规划
     * 假设arr[] = {7,4,16,15,1}
     * L代表最左边的牌的索引，R最右边的牌的索引
     * 0<= L <=R <= N
     *  L <= R所以-表示不存在的情况，不用考虑
     *  先手f[][]:
     *  (1)当L == R时 f[L][R] == arr[L] == arr[R]
     *  (2)L != R时，f[L][R] == Math.max(arr[L] + g[L+1][R], arr[R]+g[L][R-1]);
     *      0    1   2   3   4   (R)
     *  0   7                *（答案）
     *  1   -    4           ?(依赖g[2][4],g[1][3])
     *  2   -    -  16
     *  3   -    -  -   15
     *  4   -    -  -   -   1
     *  (L)
     *
     *  后手g[][]:
     *  (1)当L == R时 g[L][R] == 0
     *  (2)L != R时，g[L][R] == Math.min(f[L+1][R], f[L][R-1]);
     *      0    1   2   3   4   (R)
     *  0   0               *(答案)
     *  1   -    0          ?'
     *  2   -    -  0
     *  3   -    -  -   0
     *  4   -    -  -   -   0
     *  (L)
     *
     * @param arr
     * @return
     */
    public static int win3(int[] arr){
        if(arr == null || arr.length == 0){
            return 0;
        }
        int N = arr.length;
        int[][] f = new int[N][N];
        int[][] g = new int[N][N];
        // f矩阵对角线对应数组中各值
        for(int i = 0; i < N; i++){
            f[i][i] = arr[i];
        }
        for(int startCol = 1; startCol < N; startCol++){
            int i = 0;
            int j = startCol;
            while(/*i < N &&*/j < N){
                f[i][j] = Math.max(arr[i] + g[i + 1][j], arr[j] + g[i][j - 1]);
                g[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
                i++;
                j++;
            }
        }
        return Math.max(f[0][N-1], g[0][N-1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
    }

}
