package zuo.class19;

/**
 * 数据转字母 求方法数
 * 1对应A,2对应B,3对应C...26对应Z
 * 比如111
 * 可以转为AAA KA AK
 *
 * @Author: zhenyuan
 * @Date: 2022/5/18
 */
public class Code2_ConvertToLetterString {

    // 返回多少种转化方案
    public static int number(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    /**
     * 1。递归解法
     * str[0..i-1]转化无需过问
     * str[i.....]去转化，返回有多少种转化方法
     * @param str
     * @param i
     * @return
     */
    private static int process(char[] str, int i) {
        if(i == str.length){
            // 走完了整个数组 说明有效
            return 1;
        }
        // i没到最后，说明有字符
        if(str[i] == '0'){
            // 说明之前的决策无效
            return 0;
        }
        // str[i] != '0'
        // 可能性一：i位置数字单转
        int ways = process(str, i+1);
        if(i+1 < str.length && (str[i+1] - '0')+ (str[i] - '0')*10 < 27){
            ways += process(str, i+2);
        }
        return ways;
    }

    /**
     * 2. 动态规划 (参考暴力递归时的尝试 找出规划方程)
     * @param s
     * @return
     */
    public static int dp(String s){
        if(s == null || s.length() == 0){
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N+1];
        dp[N] = 1;
        // 前面的位置依赖后面的位置
        for(int i = N-1; i >= 0; i--){
            if(str[i] != '0'){
                int ways = dp[i+1];
                if(i+1 < str.length && (str[i+1] - '0')+ (str[i] - '0')*10 < 27){
                    ways += dp[i+2];
                }
                dp[i] = ways;
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        System.out.println(number("7210231231232031203123"));
        System.out.println(dp("7210231231232031203123"));
    }
}
