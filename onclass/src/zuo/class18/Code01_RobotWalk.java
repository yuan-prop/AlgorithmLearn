package zuo.class18;

/**
 * 假设有排成一行的N个位置，记为1~N, N一定大于等于2
 * 开始时机器人在其中的M位置上（M一定是1~N中的一个）
 * 如果机器人来到1位置，那么下一步只能往右来到2位置
 * 如果机器人来到N位置，那么下一步只能往左来到N-1位置
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走K步，最终能来到P位置（P也是1~N中的一个）的方法有多少种
 * 给定四个参数N, M, K, P，返回方法数
 */
public class Code01_RobotWalk {

    public static int way1(int N, int start, int aim, int K){
        return process1(start, K, aim, N);
    }

    /**
     * 暴力 递归法
     * @param cur 机器人当前来到的位置
     * @param rest 机器人还有剩余可以走的步数
     * @param aim 最终的目标是aim
     * @param N 有哪些位置？ 1~N
     * @return 机器人从cur出发，走过rest步之后，最终停在aim的方法数，是多少？
     */
    public static int process1(int cur, int rest, int aim, int N){
        if(rest == 0){
            return cur == aim ? 1 : 0;
        }
        // rest > 0, 还有步数要走
        if(cur == 1){// 1 -> 2
            return process1(2, rest -1 , aim, N);
        }
        if(cur == N){// N-1 <- N
            return process1(N-1, rest-1, aim, N);
        }
        // 中间位置上
        return process1(cur -1, rest-1,aim,N) + process1(cur+ 1,rest-1, aim, N);

    }

    /**
     * 方法二：傻缓存法
     * 从顶向下动态规划（记忆化搜索）
     */
    public static int way2(int N, int start, int aim, int K){
        int[][] dp = new int[N+1][K+1];
        for(int i = 0; i <= N; i++){
            for(int j=0; j<=K; j++){
                dp[i][j] = -1;
            }
        }
        /**
         * dp就是缓存表
         * dp[cur][rest] == -1 -> process2(cur,rest)之前没算过
         * dp[cur][rest] != -1 -> process2(cur,rest)之前算过！返回值，dp[cur][rest]
         */
        return process2(start, K, aim, N,dp);
    }

    /**
     *
     * @param cur 范围 1~N
     * @param rest 范围 0~K
     * @param aim
     * @param N
     * @return
     */
    public static int process2(int cur, int rest, int aim, int N,int[][] dp){
        if(dp[cur][rest] != -1){
            // 之前算过
            return dp[cur][rest];
        }
        // 之前没算过
        int ans = 0;

        if(rest == 0){
            return cur == aim ? 1 : 0;
        }else if(cur == 1){
            ans = process2(2, rest -1, aim, N, dp);
        }else if(cur == N){
            return process2(N-1, rest-1, aim, N, dp);
        }else{
            ans = process2(cur -1, rest-1,aim,N,dp) + process2(cur+ 1,rest-1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    /**
     * 暴力递归 ---尝试--> 优化的动态规划
     *  cur代表当前位置（行），rest代表剩下步数（列）
     *  cur = 1 时每个元素依赖左下，cur = N时，每个元素依赖右上
     *  1 < cur < N 时，每个元素依赖左上+右下
     *       0  1   2   3   4   5   6 (rest)
     *    0  -  -   -   -   -   -   -
     *    1  0  0   0   1   0   4   0
     *    2  0  0   1   0   4   0   13
     *    3  0  1   0   3   0   9   0
     *    4  1  0   2   0   5   0   14
     *    5  0  1   0   2   0   5   0
     *  (cur)
     */
    public static int way3(int N, int start, int aim, int K){
        if(N < 2 || K < 1 || start < 1 || start > N || aim < 1|| aim > N){
            return 0;
        }
        int[][] dp = new int[N+1][K+1];
        // 初始化第一列
        dp[aim][0] = 1; // dp[...][0] = 0;

        for(int rest = 1; rest <= K; rest++){
            // 每一行单独处理
            dp[1][rest] = dp[2][rest-1];

            for(int cur = 2; cur < N; cur++){
                dp[cur][rest] = dp[cur-1][rest-1] + dp[cur+1][rest-1];
            }
            // 最后一行单独处理
            dp[N][rest] = dp[N-1][rest -1];
        }
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(way1(5, 2, 4, 6));
        System.out.println(way2(5, 2, 4, 6));
        System.out.println(way3(5, 2, 4, 6));
    }


}
