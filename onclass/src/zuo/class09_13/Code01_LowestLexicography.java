package zuo.class09_13;

import java.util.Arrays;
import java.util.Comparator;

public class Code01_LowestLexicography {


    /**
     * 自定义字符串比较器
     * 这是字符串拼接得到最小字典序的核心
     */
    public static class MyComparator implements Comparator<String>{

        @Override
        public int compare(String a, String b) {
            /**
             * a + b和b+a都是字符串拼接后的字符串
             * 如果a+b < b+a则说明 a应该放在前面
             * 注意a和b都是有一定长度（length >= 0）的字符串
             * 另外还有个排序的传递性规律：
             *      有字符串c, 如果a+b < b+a, b+c < c+b,则推出a+c < c+a
             */
            return (a + b).compareTo(b + a);
        }
    }

    /**
     * 贪心算法:
     * 求一组字符串拼接后得到字典序最小的String结果
     * @param strs
     * @return
     */
    public static String lowestString(String[] strs){
        if(strs == null || strs.length == 0){
            return "";
        }
        Arrays.sort(strs, new MyComparator());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strs.length; i++){
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] strs = {"dbd", "a", "dgd", "rg", "ad"};
        String s = lowestString(strs);
        System.out.println(s);
    }

}
