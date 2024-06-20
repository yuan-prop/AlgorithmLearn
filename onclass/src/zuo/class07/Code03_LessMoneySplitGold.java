package zuo.class07;

import java.util.PriorityQueue;

/**
 * Huffman树在贪心算法中的应用
 *  一块金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不管切成长度多大的两半，都要花费20个铜板
 *
 *  一群人想整分一块金条，怎么分最省铜板？
 *  例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为10+20+30=60. 金条要分成10，20，30有三个部分，
 *  如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板。
 *  但是如果先把长度60的金条分成30和30，花费60，再把长度为30的分成10和20，花费30，一共花费90铜板。
 *  输入一个数组，返回分割的最小代价
 *
 * @author wzy
 * @since 2024/6/20
 */
public class Code03_LessMoneySplitGold {

    public static void main(String[] args) {
        int[] arr = new int[] {10,20,30};
        System.out.println(lessMoney(arr));
    }

    public static int lessMoney(int[] arr) {
        if(arr == null || arr.length == 0) {
            return 0;
        }

        // 默认的比较器是自然序，从小到大，因此是小根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int i : arr) {
            queue.add(i);
        }
        int res = 0;
        int cur;
        while(queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            res += cur;
            queue.add(cur);
        }
        return res;
    }

}
