package com.offer.sort;

import java.util.Arrays;

/**
 * 快速排序算法(几种细节的对比说明)
 * 平均效率：QuickSortByPart > process2 > process1 = quickSort2 > quickSort
 * https://blog.csdn.net/qq_40941722/article/details/94396010
 */
public class QuickSort {

    public static void main(String[] args) {
        int len = 1000000;
        int[] arr1 = new int[len];
        int[] arr2 = new int[len];
        int v;
        for(int i = 0; i < len; i++) {
            v = (int) (Math.random() * (len + 1));
            arr1[i] = v;
//            arr2[i] = v;
        }
        long l = System.currentTimeMillis();
        quickSort(arr1, 0, arr1.length-1);
        System.out.println(System.currentTimeMillis() - l);

//        System.out.println(Arrays.toString(arr1));
        long l2 = System.currentTimeMillis();
        QuickSortByPart(arr1, 0, arr1.length-1);
        System.out.println("排序2: " + (System.currentTimeMillis() - l2));

        // 对数器
//        for(int i = 0;i < len; i++) {
//            if(arr1[i] != arr2[i]) {
//                throw new RuntimeException("error");
//            }
//        }
    }

    /**
     * 左右指针法
     *  和快排左右二分区partition思想一样
     *  fixme 缺陷：当数组中的全部数据相等或者已经有序时且数组长度到百万时会退化到n^2复杂度，效率极低，会出现栈溢出问题,二分区partition也会存在这种问题
     *  fixme 改进：使用quickSort2
      * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        if(left > right) return;
        int tmp = arr[left];
        int i = left;
        int j = right;
        while(i != j){
            //因为此处设置的基准数是最左边的数，所以需要让哨兵j 先出动，这一点非常重要。
            while(i < j && tmp <= arr[j]){
                j--;
            }
            while(i<j && arr[i] <= tmp){
                i++;
            }
            if(i < j){
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        //此 i = j
        arr[left] = arr[i];
        arr[i] = tmp;

        quickSort(arr, left, i-1);
        quickSort(arr, i+1, right);
    }

    /**
     * 更高效的左右指标快排
     * fixme: 当数组已经有序时且数组长度到百万时会退化到n^2的复杂度，效率极低，会出现栈溢出问题
     * @param arr
     * @param l
     * @param r
     */
    public static void quickSort2(int[] arr, int l, int r) {
        if(l >= r) return;
        int x = arr[l];// 基准位置
        int i = l;
        int j = r + 1;
        while(i < j){
            do i++; while (i < j && arr[i] < x);
            do j--; while (arr[j] > x);
            if(i < j){
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[l] = arr[j];
        arr[j] = x;

        quickSort2(arr, l, j-1);
        quickSort2(arr, j+1, r);
    }

    public static void QuickSortByPart(int[] arr, int left, int right){
        if(left >= right){
            return;
        }
        int[] p = Partition(arr, left, right);
        QuickSortByPart(arr, left, p[0] - 1);
        QuickSortByPart(arr, p[1]+1, right);
    }

    //todo 快速排序 （partition 荷兰国旗问题）能保证数组已经有序和元素全相等时不会退化到n^2复杂度
    public static int[] Partition(int[] arr, int left, int right){
        if(left > right){
            return new int[]{-1, -1};
        }
        if(left == right){
            return new int[]{left, right};
        }
        int less = left - 1;
        int more = right+1;
        // note: 由于基准是随机选取的，当数组已经有序时也不至于退化到n方的复杂度
        int num = left + (int) (Math.random() * (right - left + 1));
        int d = arr[num];
//        swap(arr, num, right);
        int index = left;
        while(index < more){
            if(arr[index] < d){
                less++;
                if(less != index){
                    swap(arr, less, index);
                }
                index++;
            }else if(arr[index] == d){
                index++;
            }else{
                swap(arr, index, --more);
            }
        }
//        swap(arr, more, right);
        return new int[]{less+1, more-1};
    }

    public static void swap(int arr[], int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    private static void process1(int[] arr, int L, int R) {
        if(L >= R){
            return;
        }
        int M = partition(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    // fixme 缺陷：当数组中的全部数据相等或者已经有序时且数组长度到百万时会退化到n^2的复杂度,可能栈溢出
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
            if(arr[index] < arr[R]){
                swap(arr, index, ++lessEqual);
            }
            index++;
        }
        // 划分值X和右边区域第一个数做交换
        swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    private static void process2(int[] arr, int L, int R) {
        if(L >= R){
            return;
        }
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    public static int[] netherlandsFlag(int[] arr, int L, int R){
        if(L > R){
            return new int[]{-1, -1};
        }
        if(L == R){
            return new int[]{L, R};
        }
        int less = L-1; // < 区 右边界
        // fixme 取最右的数为基准，导致当数组已经有序时会退化到n^2的复杂度，且数组长度到百万时，效率极低，会出现栈溢出问题
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
        swap(arr, more, R);
        return new int[]{less+1, more};
    }


}