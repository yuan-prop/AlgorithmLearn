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
        quickSort.quickSort(arr, 0, arr.length-1);
        for(int i : arr){
            System.out.print(i + " ");
        }
    }

    // 双指针法
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

    //todo 快速排序 （partition 荷兰国旗问题）



}