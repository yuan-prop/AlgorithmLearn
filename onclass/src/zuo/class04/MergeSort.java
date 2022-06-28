package zuo.class04;

/**
 * @Author: zhenyuan
 * @Date: 2022/6/20
 */
public class MergeSort {

    /**
     * 递归方法实现
     */
    public static void mergeSort1(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    /**
     * 把arr[L...R}排有序,递归实现
     * 时间复杂度：Master公式 T(N) = 2*T(1/2) + O(N) = O(N * logN)
     * Master公式适用于子问题规模一致的递归
     * Master公式：T(N) = a*T(N/b) + O(N^d),其中a,b,d为常数
     * 当log以b为底a的对数 < d,则整个递归复杂度为O(N^d)
     * 当log以b为底a的对数 > d,则整个递归复杂度为O(N^(log以b为底a的对数))
     * 当log以b为底a的对数 == d,则整个递归复杂度为O(N^d * logN)
     */

    public static void process(int[] arr, int L, int R){
        if(L == R){ // base case
            return;
        }
        int mid = L + ((R - L) >> 1);// L到R的中间位置
        process(arr, L, mid);
        process(arr, mid+1, R);
        merge(arr, L, mid, R);
    }

    /**
     * 给你一个L~M和M~R范围分别有序的数组arr，合并成一个有序的大数组并放到原来位置
     * @param arr
     * @param L
     * @param M
     * @param R
     */
    private static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while(p1 <= M && p2 <= R){
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 要么p1越界，要么p2越界，只发生一个
        while(p1 <= M){
            help[i++] = arr[p1++];
        }
        while(p2 <= R){
            help[i++] = arr[p2++];
        }
        for(i = 0; i < help.length; i++){
            arr[L + i] = help[i];
        }
    }

    /**
     * 非递归方法实现
     * 思路：
     * 1 将长度为N的arr数组按照步长1开始，划分成N个组，每两个组构成一对左组和右组
     * 2 每一对左组和右组依次merge
     * 3 然后步长*2 ，重复1，2,直到步长>=N
     */
    public static void mergeSort2(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        int N = arr.length;
        // 步长
        int mergeSize = 1;
        while (mergeSize < N){// 循环logN次
            // L是按当前步长划分的各组中左组第一个位置
            int L = 0;
            while(L < N){
                // 左组最后一个位置
                int M = L + mergeSize - 1;
                if(M >= N){
                    break;
                }
                // 右组最后一个位置
                int R = Math.min(M + mergeSize, N - 1);
                merge(arr, L, M, R);
                L = R + 1;// L指到下一个左组第一位位置
            }
            // 防止步长*2后溢出
            if(mergeSize > N /2){
                break;
            }
            mergeSize <<= 1;// 步长*2
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,2,4,6,87,21,45,78,342,354,645,8,24,432};
        mergeSort1(arr);
//        mergeSort2(arr);
        for(int i = 0; i< arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }
}
