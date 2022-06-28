package zuo.class19;

import java.util.HashMap;

/**
 * @Author: zhenyuan
 * @Date: 2022/5/23
 *
 *
 *
 * 题目五
 *
 * 给定一个字符串str,给定一个字符串类型的数组arr,出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来，
 * 返回需要至少多少张贴纸可以完成这个任务
 * 例子：str="babac",arr={"ba,"c","abcd"}
 * 至少老板娘两张贴纸“ba”和“abcd",因为使用这两张贴纸，把每一个字符单独剪开，含有2个
 * a,2个b,1个c。是可以拼出str的，所以返回2
 *
 */
public class Code03_StickersToSpellWord {

    /** 1 基础版递归 */
    public static int minStickers1(String[] stickers, String target){
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 :ans;
    }

    // 所有贴纸stickers，每一种贴纸都有无穷张
    // target 目标字符串
    // 最少张数
    public static int process1(String[] stickers, String target){
        if(target.length() == 0){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for(String first : stickers){
            String rest = minus(target, first);
            if(rest.length() != target.length()){
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    private static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // 字符个数统计
        int[] count = new int[26];
        for(char c : str1){
            count[c - 'a']++;
        }
        for(char c : str2){
            count[c - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 26; i++){
            if(count[i] > 0){
                for(int j = 0; j < count[i]; j++){
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    /**
     * 2 优化版递归
     *
     * 多自己举例子就明白了
     * 比如 target = aabbbccc
     * 贴纸 aa bb c等 求最小需要的贴纸数
     */

    public static int minStickers2(String[] stickers, String target){
        // 一共有N种贴纸
        int N = stickers.length;
        // 关键优化（用词频表替代贴纸数组）
        int[][] counts = new int[N][26];
        for(int i = 0; i < N; i++){
            // i代表第几种贴纸
            char[] str = stickers[i].toCharArray();
            for(char c : str){
                counts[i][c-'a']++;
            }
        }
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 :ans;
    }

    // stickers[i]数组，当初i号贴纸的字符统计 int[][] stickers -> 所有的贴纸
    // 每一种贴纸都有无穷张
    // 返回搞定target的最少张数
    // 最少张数
    public static int process2(int[][] stickers, String t){
        if(t.length() == 0){
            return 0;
        }
        char[] target = t.toCharArray();
        // 对target做词频统计
        int[] tcounts = new int[26];
        for(char c : target){
            tcounts[c - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < N; i++){
            // 尝试第一张贴纸是谁
            int[] sticker = stickers[i];
            // 关键优化（重要的作用是剪枝！ 这一步也是贪心）
            if(sticker[target[0] - 'a'] > 0){
                StringBuilder builder = new StringBuilder();
                for(int j = 0; j < 26; j++){
                    if(tcounts[j] > 0){
                        int nums = tcounts[j] - sticker[j];
                        for(int k = 0; k < nums; k++){
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 3 动态规划 用缓存法(记忆化搜索)
     *
     */
    public static int minStickers3(String[] stickers, String target){
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for(int i = 0; i < N; i++){
            char[] str = stickers[i].toCharArray();
            for(char c : str){
                counts[i][c-'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 :ans;
    }

    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp){
        if(dp.containsKey(t)){
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for(char c : target){
            tcounts[c - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < N; i++){
            int[] sticker = stickers[i];
            if(sticker[target[0] - 'a'] > 0){
                StringBuilder builder = new StringBuilder();
                for(int j = 0; j < 26; j++){
                    if(tcounts[j] > 0){
                        int nums = tcounts[j] - sticker[j];
                        for(int k = 0; k < nums; k++){
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
    }

    public static void main(String[] args) {
        String target = "babac";
        String[] stickers = {"ba", "c", "abcd"};
        System.out.println(minStickers1(stickers, target));
        System.out.println(minStickers2(stickers, target));
        System.out.println(minStickers3(stickers, target));
    }
}
