package zuo.class11_17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Code02_PrintAllSubsequences {

    public static void main(String[] args) {
        String str = "abc";
        for(String s : subs(str)){
            System.out.println(s);
        }
    }


    /**
     * 打印一个字符串的全部子序列 (空字符串也算)
     * @param s
     * @return
     */
    public static List<String> subs(String s){
        char[] chars = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        // 如果要求打印字面值不同的子序列，可以用Hashset自动去重
//        HashSet<String> ans = new HashSet<>();
        process1(chars, 0, ans, path);
        return ans;
    }

    /**
     *
     * @param str 固定参数
     * @param index 当前来到的位置下标，str[0...index-1]已经走过了！
     * @param ans 生成的子序列放到ans里
     * @param path 之前的决定就是path
     */
    private static void process1(char[] str, int index, List<String> ans, String path) {
        if(index == str.length){
            ans.add(path);
            return;
        }
        // 没有要index位置的字符
        process1(str, index + 1, ans, path);

        // 要了index位置的字符
        String yes = path + str[index];
        process1(str, index + 1, ans, yes);
    }

}
