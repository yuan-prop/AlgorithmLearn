package zuo.class29;

/**
 * 求数组中第K小的数
 * 1 排序后取每K个位置，即为答案
 *
 * 2 利用bfprt算法（暂时没给出）
 *
 * @Author: zhenyuan
 * @Date: 2022/6/19
 */
public class Code01_FindMinKth {


    // 改写快排，时间复杂度O(N)
    // k >= 1
    public static int minKth2(int[] array, int k){
        int[] arr = copyArray(array);
        return process2(arr, 0, arr.length - 1, k -1);
    }

    public static int[] copyArray(int[] arr){
        int[] ans = new int[arr.length];
        for(int i = 0; i != ans.length; i++){
            ans[i] = arr[i];
        }
        return ans;
    }

    /**
     * arr第k小的数 process(arr, 0, N-1, k-1)
     * arr[L...R]范围上，如果排序的话（不是真的去排序），找位于index的数
     * @param arr
     * @param L
     * @param R
     * @param index L ~ R
     * @return
     */
    private static int process2(int[] arr, int L, int R, int index) {
        if(L == R){// L == R == index 即数组只有一个数时
            return arr[L];
        }
        // 不止一个数时，随机选一个数 L + [0, R - L]
        int pivot = arr[L + (int) (Math.random() * (R - L +1))];
        // 分区 小于pivot的数在左边 等于的在中间 大于的在右边
        // range包含两个数，分别是等于区域的左边界和右边界
        int[] range = partition(arr, L, R, pivot);
        if(index >= range[0] && index <= range[1]){
            return arr[index];
        }else if(index < range[0]){
            return process2(arr, L, range[0] - 1, index);
        }else{
            return process2(arr, range[1] + 1, R, index);
        }

    }

    private static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while(cur < more){
            if(arr[cur] < pivot){
                swap(arr, ++less, cur++);
            }else if(arr[cur] > pivot){
                swap(arr, cur, --more);
            }else{
                cur++;
            }
        }
        return new int[]{less, more};
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    public static int minKth(int[] arr, int index){
        int L = 0;
        int R = arr.length -1;
        int pivot = 0;
        int[] range = null;
        while(L < R){
            pivot = arr[L + (int) (Math.random()* (R-L+1))];
            range = partition(arr, L, R, pivot);
            if(index < range[0]){
                R = range[0] - 1;
            }else if(index > range[1]){
                L = range[1] + 1;
            }else{
                return pivot;
            }
        }
        return arr[L];
    }
}
