package com.offer.sort;

import java.util.Arrays;

/**
 * 快速排序算法
 * https://blog.csdn.net/qq_40941722/article/details/94396010
 */
public class QuickSort {

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] arr = {3,4,6,1,2,9,2};
//        quickSort.quickSort(arr, 0, arr.length-1);
        QuickSortByPart(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    // 双指针法 （ 和快排分区partition思想一样）
    private void quickSort(int[] arr, int left, int right) {
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

    public static void QuickSortByPart(int[] arr, int left, int right){
        if(left >= right){
            return;
        }
        int[] p = Partition(arr, left, right);
        QuickSortByPart(arr, left, p[0] - 1);
        QuickSortByPart(arr, p[1]+1, right);
    }

    //todo 快速排序 （partition 荷兰国旗问题）
    public static int[] Partition(int[] arr, int left, int right){
        if(left > right){
            return new int[]{-1, -1};
        }
        if(left == right){
            return new int[]{left, right};
        }
        int less = left - 1;
        int more = right+1;
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


}