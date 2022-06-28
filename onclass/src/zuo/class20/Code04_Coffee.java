package zuo.class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 题目三(京东面试题，较难)
 * 给定一个数组arr,arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a,洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b,咖啡杯可以并行挥发
 * 返回从开始等到所有黑咖啡机变干净的最短时间
 * 三个参数：int[] arr, int N, int a, int b
 *
 * @Author: zhenyuan
 * @Date: 2022/5/31
 */
public class Code04_Coffee {

    /*** 方法一：暴力尝试方法 ***/
    public static int minTime1(int[] arr, int n, int a, int b){
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }
    private static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if(kth == n){
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++){
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre+work;
            time = Math.min(time, forceMake(arr, times, kth+1, drink, n, a,b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }
    // 洗咖啡杯，方法一：暴力尝试洗咖啡杯的方式
    public static int  forceWash(int[] drinks, int a, int b, int index, int washLine, int time){
        if(index == drinks.length){
            return time;
        }
        //选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index+1,wash, Math.max(wash, time));
        //选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index]+ b;
        int ans2 = forceWash(drinks, a, b, index+1,washLine, Math.max(dry, time));
        return Math.min(ans1,ans2);
    }


    /***********方法二****************************************/
    static class Machine{
        private int timePoint;
        private int workTime;

        public Machine(int t, int w) {
            // 什么时间点可再用
            this.timePoint = t;
            // 泡一杯咖啡时间
            this.workTime = w;
        }
    }
    static class MachineComparator implements Comparator<Machine>{
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }
    // 方法二：每个人暴力尝试用每一个咖啡机给自己做咖啡，优化成贪心
    public static int minTime2(int[] arr, int n, int a, int b){
        // 重点：利用小根堆
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for(int i=0; i<arr.length;i++){
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for(int i = 0; i < n; i++){
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        // 这里同方法三，洗咖啡杯的暴力尝试改成最优了
        return bestTimeDp(drinks, a, b);

    }

    /************方法三:最终版，把方法三洗咖啡杯的暴力尝试进一步优化成动态规划*********************************/
    public static int minTime3(int[] arr, int n, int a, int b){
        // 重点：利用小根堆
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for(int i=0; i<arr.length;i++){
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for(int i = 0; i < n; i++){
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        if(a >= b){
            return drinks[n-1]+b;
        }
        int[][] dp = new int[n][drinks[n-1] + n * a];
        for(int i = 0; i < dp[0].length; i++){
            dp[n-1][i] = Math.min(Math.max(i, drinks[n-1]) + a, drinks[n-1] +b);
        }
        for(int row = n-2; row >= 0; row--){// row 咖啡杯的编号
            int washLine = drinks[row] + (row + 1) * a;
            for(int col = 0; col < washLine; col++){
                int wash = Math.max(col, drinks[row]) + a;
                dp[row][col] = Math.min(Math.max(wash, dp[row+1][wash]), Math.max(drinks[row] + b, dp[row+1][col]));
            }
        }
        return dp[0][0];
    }

    /**
     * 下面再具体分析下洗杯子的最短时间
     * @param drinks 代表所有杯子可以开始洗的时间
     * @param wash 单杯洗干净的时间（串行）
     * @param air 挥发干净的时间（并行）
     * @param index 当前来到第几号咖啡杯
     * @param free 洗的机器什么时候可用
     * @return drinks[index...]都变干净，最早的结束时间（返回）
     */
    public int bestTime(int[] drinks, int wash, int air, int index, int free){
        if(index == drinks.length){
            return 0;
        }
        // 1 index 号杯子 决定洗
        int selfClean1 = Math.max(drinks[index], free) + wash;
        int restClean1 = bestTime(drinks, wash, air, index+1, selfClean1);
        int p1 = Math.max(selfClean1, restClean1);
        // 2 index 号杯子 决定挥发
        int selfClean2 = drinks[index] + air;
        int restClean2 = bestTime(drinks, wash, air, index+1, free);// 没用洗杯机
        int p2 = Math.max(selfClean2, restClean2);

        return Math.min(p1, p2);
    }

    /**
     * 洗咖啡杯方法二：动态规划
     * @param drinks
     * @param wash
     * @param air
     * @return
     */
    public static int bestTimeDp(int[] drinks, int wash, int air){
        int N = drinks.length;
        // 由于free的规范不好确定，但一定不会超过最大洗杯时间，下面求最大洗杯时间
        int maxFree = 0;
        // 所有杯子都用洗杯机去洗的最大时间
        for(int i = 0; i < drinks.length; i++){
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[N+1][maxFree + 1];

        // 根据递归中的依赖关系，dp[][]二维表中，index层依赖下面层index+1,从下往上填
        // dp[N][...] = 0
        for(int index = N-1;index>=0; index--){
            for(int free = 0; free <= maxFree; free++){
                int selfClean1 = Math.max(drinks[index], free) + wash;
                if(selfClean1 > maxFree){//排除越界的情况
                    continue;
                }
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // 2 index 号杯子 决定挥发
                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index +1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    public static int[] randomArray(int len, int max){
        int[] arr = new int[len];
        for(int i = 0; i < len; i++){
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    public static void printArray(int[] arr){
        System.out.print("arr: ");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+", ");
        }
        System.out.println();
    }

    // 对数器
    public static void main(String[] args) {
        int len = 5;
        int max = 9;
        int testTime = 50000;
        for(int i = 0; i < testTime; i++){
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 5) + 1;
            int a = (int) (Math.random() * 5) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = minTime1(arr, n, a, b);
            int ans2 = minTime2(arr, n, a, b);
            int ans3 = minTime3(arr, n, a, b);
            if(ans1 != ans2 || ans2 != ans3){
                printArray(arr);
                System.out.println("n："+n);
                System.out.println("a："+a);
                System.out.println("b："+b);
                System.out.println(ans1+" , "+ans2+" , "+ans3);
                System.out.println("======================");
                break;
            }
        }
    }
}
