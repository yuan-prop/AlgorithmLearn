package zuo.class20;

/**
 * 求两个字符串的最长回文子序列（子序列可以不连续）
 * 方法1。将一个字符串与另外一个的逆序求最长公共子序列，即为最长回文子序列
 * 方法2。尝试在某个范围上求最长回文子序列（递归到动态规划）
 * @Author: zhenyuan
 * @Date: 2022/5/26
 */
public class Code02_PalindromeSubsequence {

    public static int lpls(String s){
        if(s == null || s.length() == 0){
            return 0;
        }
        char[] str = s.toCharArray();
        return f(str,  0, str.length -1);
    }

    /**
     * 方法一 递归
     * str[L...R]最长回文子序列长度返回
     * @param str
     * @param L
     * @param R
     * @return
     */
    private static int f(char[] str, int L, int R) {
        if(L == R){// 数组只有一个字符
            return 1;
        }
        if(L == R-1){//两个字符
            return str[L] == str[R] ? 2 : 1;
        }
        //1 回文串既不以L开头也不以R结尾
        int p1 = f(str, L+1, R-1);
        //2 回文串以L开头但不以R结尾
        int p2 = f(str, L, R-1);
        //3 回文串不以L开头但以R结尾
        int p3 = f(str, L+1, R);
        //4 回文串以L开头且以R结尾
        int p4 = str[L] != str[R] ? 0 : (2 + f(str, L+1, R-1));
        return Math.max(Math.max(p1,p2),Math.max(p3,p4));
    }

    //方法二 动态规划
    public static int lpsl2(String s){
        if(s == null || s.length() == 0){
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        /* L 0...N-1
           R 0...N-1 形成N*N的矩阵
         */
        int[][] dp = new int[N-1][N-1];
        dp[N-1][N-1] = 1;
        // 矩阵的两条对角线填完
        for(int i = 0; i < N-1; i++){
            dp[i][i] = 1;
            dp[i][i+1] = str[i] == str[i+1] ? 2 : 1;
        }
        // 从下往上，从左往右填充矩阵剩下的格式（也可以对角线式填充）
        for(int L = N-3; L >=0 ; L--){
            for(int R = L+2; R < N; R++){
//                int p1 = dp[L+1][R-1];
//                int p2 = dp[L][R-1];
//                int p3 = dp[L+1][R];
//                int p4 = str[L] != str[R] ? 0 : (2 + dp[L+1][R-1]);
//                dp[L][R] = Math.max(Math.max(p1,p2),Math.max(p3,p4));
                // 根据当前位置的值 > 左、下、左下的值， 而左大于左下，下大于左下 所以只需比较左和下
                dp[L][R] = Math.max(dp[L-1][R], dp[L][R-1]);
                if(str[L] == str[R]){
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L+1][R-1]);
                }
            }
        }
        return dp[0][N-1];
    }

    public static void main(String[] args) {
        int lpls = lpls("123ad321");
        int lpls2 = lpls("123ad321");
        System.out.println(lpls);
        System.out.println(lpls2);
    }

}
