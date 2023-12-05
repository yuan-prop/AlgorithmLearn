package com.offer.sort;

/** 堆结构和堆排序地（完全二叉树）
 * 默认用大根堆
 *
 */
public class HeapSort {

    /**
     * 大根堆的heapInsert
     * 重要： 某个数在现在处在index位置，继续往上移动（元素往整个树中从下向上走）
     */
    public static void heapInsert(int arr[], int index){
        // 当前节点如果大于父节点则交换两者，重复进行直到节点到达堆顶或者不大于父节点
        while(arr[index] > arr[(index-1)/2]){
            swap(arr, index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    /**
     * 大根堆的heapify
     * 重要：某个数在树的index位置，能否往下移动（元素往整个树中向下走）
     */
    public static void heapify(int arr[], int index, int heapSize){
        int left = index * 2 + 1; // 左孩子的下标
        while(left < heapSize){//当前节点还有孩子时
            //两个孩子，谁的值大，把下标给largest
            int largest = left + 1<heapSize && arr[left] < arr[left+1] ? left+1 : left;

            // 父和较大孩子间，谁的值大，把下标给largest
            largest = arr[index] < arr[largest] ? largest : index;
            if(largest == index){
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 +1;
        }
    }

    public static void heapify2(int arr[], int index, int heapSize){
        int left = index * 2 + 1; // 左孩子的下标
        int temp = arr[index];
        while(left < heapSize){//当前节点还有孩子时
            //两个孩子，谁的值大，把下标给largest
            int largest = left + 1<heapSize && arr[left] < arr[left+1] ? left+1 : left;

            // 父和较大孩子间，谁的值大，把下标给largest
            if(temp >= arr[largest]){
                break;
            }
            arr[index] = arr[largest];
            index = largest;
            left = index * 2 +1;
        }
        arr[index] = temp;
    }

    public static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 堆排序：时间复杂度O(N*logN)，空间复杂度：O(1)
     * @param arr
     */
    public static void heapSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        // 将整个数组调整成大根堆
        //方法1 从数据中依次取出元素顺序建立 每一次来一个数据，就在堆的末尾添加数据，并通过heapInsert方法判断新添加的数据该存放在哪个位置
        for(int i=0; i<arr.length; i++){//O(N)
            heapInsert(arr, i);//复杂度 O(logN)
        }
        // 方法2 在数组的基础上，逆向建立。此时我们从最后一个非叶子结点开始（叶结点不用调整，第一个非叶子结点 arr.length/2-1），从左至右，从下至上进行调整。
//        for(int i=arr.length/2-1; i >=0 ;i--){//O(N)
//            heapify(arr, i, arr.length);
//        }

        int heapSize = arr.length;
        // 2先将根节点和最后一个元素交换，最大的数会放到数组最后位
        swap(arr, 0, --heapSize);
        // 3 将剩下的含--heapSize个元素的二叉树（数组）做heapify，
        while(heapSize > 0){//O(N)
            heapify(arr, 0, heapSize);// O(logN)
            // 将当前根节点和最后一个元素交换，最大的数会放到数组最后位
            swap(arr, 0, --heapSize);// O(1)
        }
    }

    public static void main(String[] args) {
        int[] arr = {3425,6453,7,6,78,90,3,4,6,1,2,9,18,15,12,44,32};
        heapSort(arr);
        for(int i : arr){
            System.out.print(i+" ");
        }
    }
}
