package zuo.class27;

/**
 * 求字符串str1中包含的str子串的起始位置下标
 *
 * @Author: zhenyuan
 * @Date: 2022/6/16
 */
public class Code01_KMP {


    /**
     * 判断字符串s1是否包含s2，s1长度N >= s2长度M
     * @param s1 ...abbstkscabbstks
     *                            x位置
     * @param s2    abbstkscabbstkz
     *                            y位置
     *              当s2的y位置z和s1的x位置s匹配不上，而z前面的串中最长前缀和最长后缀是abbstk，长度为6（通过next数组得到）
     *              此时用s2中y位置的最长前缀abbstk的下一位s(y置为next[y] + 1)和s1的x位置s比较
     *
     *  复杂度:
     *  由于 x < N 且 x-y < N
     *  循环中三个分支:第一个x增，y增，x-y不变
     *              第二个x增，y不变，x-y增
     *              第三个x不变，y减小，x-y增
     *  因此复杂度小于2N -> O(N)
     * @return
     */
    public static int getIndexOf(String s1, String s2){
        if(s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()){
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int x = 0;
        int y = 0;
        // 求next[]数组，复杂度：O(M), M <= N(N,M分别是Str1,str2的长度)
        int[] next = getNextArray(str2);
        // O(N)
        while(x < str1.length && y < str2.length){
            if(str1[x] == str2[y]){
                x++;
                y++;
            }else if(next[y] == -1){//即 y == 0时,str2从0位开始重新和str1的x位置比较
                x++;
            }else{
                y = next[y];
            }
        }
        // y越界了说明匹配成功 或者 x越界
        return y == str2.length ? x - y : -1;
    }

    // 求next[]数组 复杂度O(M) M为str2数组长度
    // next[i]代表Str2第i个元素的最长前缀长度(也是最长后缀的长度)
    private static int[] getNextArray(char[] str2) {
        if(str2.length == 1){
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2; // 目前在哪个位置上求next数组的值
        int cn = 0;// 前缀串的后一个位置索引
        while(i < next.length){
            if(str2[i - 1] == str2[cn]){// i位置的前一个元素和cn位置的元素一样的时候
                next[i++] = ++cn;
            }else if(cn > 0){
                cn = next[cn];
            }else{
                next[i++] = 0;
            }
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size){
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for(int i = 0; i < ans.length; i++){
            ans[i] = (char)((int) (Math.random() * possibilities) + 'a');
        }
        return new String(ans);
    }

    public static void main(String[] args) {
        String s1 = getRandomString(25, 10);
        String s2 = getRandomString(25, 5);
        System.out.println(s1);
        System.out.println(s2);
        int indexOf = getIndexOf(s1, s2);
        System.out.println(indexOf);
    }
}
