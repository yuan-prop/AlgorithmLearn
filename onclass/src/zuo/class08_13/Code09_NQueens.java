package zuo.class08_13;

/**
 * 回溯算法——N皇后问题
 *
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行，不同列，
 * 也不在同一条直线上。
 * 给定一个整数n,返回n皇后的摆法有多少种
 * n=1返回1
 * n=2或3 无论怎么摆都不行，返回0
 * n=8,返回92
 *
 * @Author: zhenyuan
 * @Date: 2024/6/19
 */
public class Code09_NQueens {

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        System.out.println(num1(14));
        System.out.println(System.currentTimeMillis() - l +"ms");
        long l2 = System.currentTimeMillis();
        System.out.println(num2(14));
        System.out.println(System.currentTimeMillis() - l2 +"ms");
    }

    public static int num1(int n){
        if(n < 1) {
            return 0;
        }
        // 记录第i行的皇后放在了第几列
        int[] record = new int[n];
        return process1(0, record, n);
    }

    /**
     * record[0...i-1]表示之前的行，放了皇后的位置，
     *  任何两个皇后一定都不共行，不共列，不共斜线
     * @param i 目前来到了第i行
     * @param record
     * @param n 代表整体一共有多少行
     * @return 摆完所有的皇后，返回合理的摆法有多少种
     */
    private static int process1(int i, int[] record, int n) {
        if(i == n) { // 终止行
            return 1;
        }
        int res = 0;
        // 当前行在i行，尝试i行所有的列
        for (int j = 0; j < n; j++) {
            // 当前i行的皇后，放在j列，会不会和之前(0...i-1)的皇后，不共行共列共斜线
            // 如果是，认为有效，否则无效
            if(isValid(record, i, j)) {
                record[i] = j;
                res += process1(i + 1, record, n);
            }
        }
        return res;
    }

    // record[0...i-1]需要检查，record[i...]不需要检查
    // 返回i行皇后，放在了j列是否有效
    // 只需要检查是否共列或共斜线
    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            if(j == record[k] || record[k] - j == i - k || record[k] - j == k - i) {
                return false;
            }
        }
        return true;
    }


    /**
     * 用位运算加速
     * 请不要超过32皇后问题
     * 常数优化版本，不能超过32位整形（否则需要改成long）
     */
    public static int num2(int n){
        if(n < 1 || n > 32) {
            return 0;
        }
        // 一个二进制整数，后n位全是1
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    /**
     *
     * @param limit
     * @param colLim 列的限制，1的位置不能放皇后，0的位置可以
     * @param leftDiaLim 左斜线的限制，1的位置不能放皇后，0的位置可以
     * @param rightDiaLim 右斜线的限制，1的位置不能放皇后，0的位置可以
     * @return
     */
    private static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if(colLim == limit) { // base case N个皇后填满
            return 1;
        }
        // 所有候选皇后的位置都在pos的二进制位为1的位置上
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            // 提取候选位置中最右边的一个
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit,
                    colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

}
