package zuo.class20;

/**
 * 字符串&动态规划
 *
 * 求两个字符串的最长公共子序列（子序列在字符串中可以不连续）
 * @Author: zhenyuan
 * @Date: 2022/5/23
 */
public class Code01_LongestCommonSubsequence {

    public static int longestCommonSubsequence1(String s1, String s2){
        if(s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0){
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // 尝试
        return process1(str1, str2,str1.length-1,str2.length-1);
    }

    /**
     * str1[0...i] 与str2[0...j]最长公共子序列多长？
     */
    public static int process1(char[] str1, char[] str2, int i, int j) {
        if(i == 0 && j == 0){
            return str1[i] == str2[j] ? 1 : 0;
        }else if(i == 0){// str1只有一个元素
            if(str1[i] == str2[j]){
                return 1;
            }else{
                return process1(str1, str2, i,j-1);
            }
        }else if(j == 0){
            if(str1[i] == str2[j]){
                return 1;
            }else{
                return process1(str1, str2, i-1, j);
            }
        }else{// i != 0 && j != 0
            // 1 完全不考虑i是结尾 但考虑j可能是结尾
            int p1  = process1(str1, str2, i-1, j);
            // 2 完全不考虑j是结尾 但考虑i可能是结尾
            int p2  = process1(str1, str2, i, j-1);
            // 3 考虑是i和j位置都是结尾
            int p3 = str1[i] == str2[j] ? (1 + process1(str1, str2, i-1, j-1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequence2(String s1, String s2) {
        if(s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0){
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for(int j = 1; j<M;j++){
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j-1];
        }
        for(int i = 1; i<N; i++){
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i-1][0];
        }
        for(int i = 1; i<N; i++){
            for(int j = 1; j<M;j++){
                int p1 = dp[i-1][j];
                int p2 = dp[i][j-1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i-1][j-1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N-1][M-1];
    }

    public static void main(String[] args) {
        int i = longestCommonSubsequence1("ab12c45d6efascd", "ks1234tz56a,sd");
        int j = longestCommonSubsequence2("ab12c45d6efassd", "ks1234tz56a,sd");
        System.out.println(i);
        System.out.println(j);
    }

}
