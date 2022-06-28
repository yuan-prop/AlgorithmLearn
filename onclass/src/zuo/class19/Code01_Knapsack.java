package zuo.class19;

/**
 * 背包问题
 * 求背包最大能装的物品价值
 */
public class Code01_Knapsack {

    /**
     *
     * @param w 各物品的重量 >=0
     * @param v 各物品的价值 >=0
     * @param bag 背包大小
     * @return
     */
    public static int maxValue(int[] w, int[] v, int bag){
        if(w == null || v == null || w.length != v.length || w.length ==0){
            return 0;
        }
        // 尝试函数！
        return process1(w, v, 0, bag);
    }

    /**
     * 当前考虑到了index号货物，index...所有的货物可以自由选择
     * 做的选择不能超过背包容量
     * 返回最大价值
     * @param w
     * @param v
     * @param index
     * @param bag
     * @return
     */
    private static int process1(int[] w, int[] v, int index, int bag) {
        if(bag < 0){
            return -1;
        }
        if(index >= w.length){
            return 0;
        }
        // 有货，index位置的货
        // bag有空间（0也可以，因为有物品重量可能为0）
        // 不要当前的货
        int p1 = process1(w, v, index + 1, bag);
        // 要当前的货
        int p2 = 0;
        if(w[index] <= bag){
            p2 = v[index] + process1(w, v, index + 1, bag - w[index]);
        }
        return Math.max(p1, p2);
    }

    /**
     * 用动态规划法
     * 影响的参数为当前物品索引index和背包剩余容量bag
     *      0   1   2   3   4 (rest)
     * 0    依赖下面的
     * 1    依赖下面的
     * 2    依赖下面的
     * 3    0   0   0   0   0
     * index
     *
     * @param w
     * @param v
     * @param bag
     * @return
     */
    public static int dp(int[] w, int[] v, int bag){
        if(w == null || v == null || w.length != v.length || w.length ==0){
            return 0;
        }
        int N = w.length;
        // index 0~N
        // rest 0~bag
        int[][] dp = new int[N+1][bag+1];
        // dp[N][...] = 0; int[][]中默认就是0，不用再初始化
        // 通过依赖关系将 dp填好
        for(int index = N-1; index >=0; index--){
            for(int rest = 0; rest < bag+1; rest++){
                int p1 = dp[index+1][rest];
                int p2 = w[index] <= rest ? v[index] + dp[index+1][rest - w[index]] : 0;
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6,3,19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue(weights, values,bag));
        System.out.println(dp(weights, values,bag));
    }

}
