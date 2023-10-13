package zuo.class03_05;

/**
 * 快排
 * 分区（荷兰国旗问题）
 * @Author: zhenyuan
 * @Date: 2022/6/22
 */
public class Code02_PartitionAndQuickSort {

    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 划分两个区域
     * arr[L...R]上，以arr[R]位置的数X做划分值
     * <=X的数放左，>X的数放右
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int partition(int[] arr, int L, int R){
        if(L > R){
            return -1;
        }
        if(L == R){
            return L;
        }
        // 小于区域的右边界，开始为-1
        int lessEqual = L - 1;
        int index = L;
        while (index < R){
            if(arr[index] <= arr[R]){
                swap(arr, index, ++lessEqual);
            }
            index++;
        }
        // 划分值X和右边区域第一个数做交换
        swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    /**
     * 划分3个区域
     * arr[L...R]玩荷兰国旗问题的划分，以arr[R]做划分值
     * <arr[R]放左边， ==arr[R]放中间 ，>arr[R]放右边
     * 返回的数组是等于区域的下标范围（长度为2）
     */
    public static int[] netherlandsFlag(int[] arr, int L, int R){
        if(L > R){
            return new int[]{-1, -1};
        }
        if(L == R){
            return new int[]{L, R};
        }
        int less = L-1; // < 区 右边界
        int more = R; // > 区 左边界, R位置的数做为划分值
        int index = L; // 当前来到的位置
        while(index < more){ // 当前位置不能和 >区的左边界撞上
            if(arr[index] == arr[R]){//当前数 == 划分值
                // 1 当前数等于划分值
                index++;
            }else if(arr[index] < arr[R]){ //当前数 < 划分值
                // 2 当前数和<区域的下一个数交换，<区向右扩，当前数跳下一个（index++）
                swap(arr, index++, ++less);
            }else{//当前数 > 划分值
                // 3 当前数和>区的前一个数做交换，>区向左扩，index不变
                swap(arr, index, --more);
            }
        }
        // 此时在L到R-1范围 划分成了三个区: <arr[R]放左边， ==arr[R]放中间 ，>arr[R]放右边
        // 将R位置和>区的第一个数交换
        swap(arr, R, more);
        return new int[]{less+1, more};
    }

    /**
     * 快排1.0 复杂度n^2
     * @param arr
     */
    public static void quickSort1(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        process1(arr, 0 , arr.length-1);
    }

    private static void process1(int[] arr, int L, int R) {
        if(L >= R){
            return;
        }
        int M = partition(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    /**
     * 快排2.0 复杂度n^2
     * @param arr
     */
    public static void quickSort2(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        process2(arr, 0 , arr.length-1);
    }

    private static void process2(int[] arr, int L, int R) {
        if(L >= R){
            return;
        }
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    /**
     * 快排3.0 复杂度n*logn
     * @param arr
     */
    public static void quickSort3(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        process3(arr, 0 , arr.length-1);
    }

    private static void process3(int[] arr, int L, int R) {
        if(L >= R){
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalArea = netherlandsFlag(arr, L, R);
        process3(arr, L, equalArea[0] - 1);
        process3(arr, equalArea[1] + 1, R);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{234,54,768,978,76,55,3,2,81, 32,234,65,76,5,37,82, 1};
        int[] arr2 = new int[]{234,54,768,978,76,55,3,2,81, 32,234,65,76,5,37,82, 1};
        int[] arr3 = new int[]{234,54,768,978,76,55,3,2,81, 32,234,65,76,5,37,82, 1};
        quickSort1(arr);
        quickSort2(arr2);
        quickSort3(arr3);
        for(int d : arr){
            System.out.print(d + " ");
        }
        System.out.println();
        for(int d : arr2){
            System.out.print(d + " ");
        }
        System.out.println();
        for(int d : arr3){
            System.out.print(d + " ");
        }
    }

}
