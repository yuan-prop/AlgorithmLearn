package com.offer.binarytree;

/**
 * 查找指定数组中的第k大的数并返回（注意是第k大而不是第k小）
 * 通过快排分区思想，得到每次分区后的基准位置索引(pivot)和第k大的数的位置索引（len-k）比较
 * 从而进一步确定接下来查找左边的分区还是右边的分区
 * 注意：查找过程中和快排一样会对数据中的元素位置做变动
 *
 */
class FindKthLargest {

    public static void main(String[] args) {
        int len = 1000000;
        int[] arr1 = new int[len];
        int[] arr2 = new int[len];
        int v;
        for(int i = 0; i < len; i++) {
            v = 1;//(int) (Math.random() * (len + 1));
            arr1[i] = v;
            arr2[i] = v;
        }
        int k = len - len / 3;
        long l = System.currentTimeMillis();
        System.out.println(quickSelect(arr1, 0, len - 1, len-k));
        System.out.println("time1: "+(System.currentTimeMillis() - l));
        long l2 = System.currentTimeMillis();
        System.out.println(quickSelect3(arr2, 0, len - 1, len-k));
        System.out.println("time2: "+(System.currentTimeMillis() - l2));
    }

    public static int findKthLargest(int[] _nums, int k) {
        int n = _nums.length;
        return quickSelect(_nums, 0, n - 1, n - k);
    }

    // 当数组已经有序时且数组长度到百万时会退化到n^2复杂度，可能会因递归深度过大导致jvm栈溢出
    static int quickSelect(int[] nums, int l, int r, int k) {
        if (l == r) return nums[k];
        int x = nums[l];
        int i = l - 1;
        int j = r + 1;
        while (i < j) {
            do i++; while (nums[i] < x);
            do j--; while (nums[j] > x);
            if (i < j){
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        // 注意：此时的 j <= i，即0<= (i-j) <= 1, 数组中j右侧的元素都比j大，但是j左侧的不一定都比j小
        // 因此只能判断k>j和k<=j，不能判断k>=j, return quickSelect(nums, j, r, k) ，否则会漏掉j左侧的元素比j大的元素
        if (k <= j) return quickSelect(nums, l, j, k);
        else return quickSelect(nums, j + 1, r, k);
    }

    // 和quickSelect方式一样，但在每次循环结束后，还会将基准值交换到应该在的位置
    static int quickSelect2(int[] nums, int l, int r, int k) {
        if (l == r) return nums[k];
        int x = nums[l];// 基准位置
        int i = l;// 下面的比较和交换从l的下个位置开始
        int j = r + 1;
        while (i < j) {
            do i++; while (nums[i] < x);
            do j--; while (nums[j] > x);
            if (i < j){
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        // 将基准值交换到应该在的位置
        nums[l] = nums[j];
        nums[j] = x;
        if (k > j) return quickSelect2(nums, j+1, r, k);
        else if(k < j) return quickSelect2(nums, l, j-1, k);
        return nums[j];
    }

    static int quickSelect3(int[] arr, int l, int r, int k) {
        if(l >= r){
            return arr[l];
        }
        int[] p = Partition(arr, l, r);
        if(p[0] <= k && k <= p[1]) {
            return arr[p[0]];
        }
        if(k < p[0]) return quickSelect3(arr, l, p[0] - 1, k);
        else return quickSelect3(arr, p[1]+1, r, k);
    }

    // 利用快排分区思想 只能用荷兰国旗的三分区而不能用普通二分区，否则递归深度过大栈溢出
    static int[] Partition(int[] arr, int left, int right){
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

    static void swap(int arr[], int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}