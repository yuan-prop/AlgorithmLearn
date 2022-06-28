package zuo.class28;

/**
 * 求字符串中的最长回文子串（子串是指连续的）
 *
 * @Author: zhenyuan
 * @Date: 2022/6/18
 */
public class Code01_Manacher {

    /**
     * 方法1 ：暴力求解
     * 列如：字符串 121aaaa232
     * 将其字符间和首尾都加入#，处理成：#1#2#1#a#a#a#a#2#3#2#（这样处理会方便很多）
     * 对每个位置，依次向两边扩展，计算回文长度，
     * #1#2#1#a#a#a#a#2#3#2#
     * 13171313579....
     * 除2，向下取整，得到数组b
     * 01030101234....
     *
     * 最终取b中最大的值，即为最长回文子串
     */


    /**
     * 方法2 Manacher算法
     * 字符串的预处理同方法1
     *
     * 假设位置C处回文左边界为L，右边界为R ，C称为回文中心点
     * 第i个位置（假设在C的右边）的对称位置为i` 则i` = 2 * C - i
     * L关于i'的对称为位置为L' R关于i的对称点为R'
     *
     * 1 i在R外，暴力扩 R递增
     * 2 i在R内分三种情况：
     *  1）i`的处的回文段在(L,R)内，则i的回文串长度=i`的回文串长度
     *  2）i`的处的回文段不在(L,R)内，即i`的回文串最左边界<L 则i的回文串长度为R' ~ R
     *  3)i`回文串最左边界==L 则i的回文串长度 >= R' ~ R
     *
     */
    public static int manacher(String s){
        if(s == null || s.length() == 0){
            return 0;
        }
        //"12132" -> "#1#2#1#3#2#"
        char[]  str = manacherString(s);
        // 数组 代表各位置回文半径的大小
        int[] pArr = new int[str.length];
        int C = -1;
        // 讲述中，R代表最右的扩成功的位置（回文串最右位置），这里的R则是它的下一位（即回文失败的位置）
        int R = -1;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < str.length; i++){
            // R第一个违规的位置i >= R
            // R > i则说明i在以C为中心的回文串内
            // i位置扩出来的答案，i位置扩的区域，Math.min(pArr[2 * C - i], R - i)表示回文半径至少是多大
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

            // 尝试往外扩展回文串
            while(i + pArr[i] < str.length && i - pArr[i] > -1){
                if(str[i + pArr[i]] == str[i - pArr[i]]){
                    pArr[i]++;
                }else {
                    break;
                }
            }
            if(i + pArr[i] > R){
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1; // 最大回文半径 -1 即为答案
    }

    private static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for(int i = 0; i < res.length; i++){
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        char[] chars = manacherString("12314");
        int manacher = manacher("adbdefgxtxgfedbad");
        System.out.println(chars);
        System.out.println(manacher);
    }


}
