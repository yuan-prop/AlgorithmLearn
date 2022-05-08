package zuo.class11_17;

import java.util.ArrayList;
import java.util.List;

public class Code03_PrintAllPermutations {


    /**
     * 打印字符串的全排列
     * @param s
     * @return
     */
    public static List<String> Permutations2(String s){
        List<String> ans = new ArrayList<>();
        if(s == null || s.length() == 0){
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    public static List<String> Permutations3(String s){
        List<String> ans = new ArrayList<>();
        if(s == null || s.length() == 0){
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    private static void g1(char[] str, int index, List<String> ans) {
        if(index == str.length){
            ans.add(String.valueOf(str));
        }else{
            for(int i = index; i  < str.length; i++){
                swap(str, index, i);
                g1(str, index + 1, ans);// 后序操作
                //恢复str到最初的，进行下一个遍历
                swap(str, index, i);
            }
        }
    }
    private static void g2(char[] str, int index, List<String> ans) {
        if(index == str.length){
            ans.add(String.valueOf(str));
        }else{
            // 去重(不区分原始字符串中不同位置相同的字符)
            boolean[] visited = new boolean[256];
            for(int i = index; i  < str.length; i++){
                if(!visited[str[i]]) {
                    visited[str[i]] = true;
                    swap(str, index, i);
                    g2(str, index + 1, ans);// 后序操作
                    //恢复str到最初的，进行下一个遍历
                    swap(str, index, i);
                }
            }
        }
    }


    private static void swap(char[] str, int idx1, int idx2){
        char tmp = str[idx1];
        str[idx1] = str[idx2];
        str[idx2] = tmp;
    }

    public static void main(String[] args) {
        String str = "acc";
        List<String> ss = Permutations3(str);
        for(String s : ss){
            System.out.println(s);
        }
    }

}
