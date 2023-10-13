package zuo.class03_05;

import java.util.Stack;

/**
 * @Author: zhenyuan
 * @Date: 2022/6/26
 */
public class Code03_QuickSortRecursiveAndUnRecursive {

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

    // 快排非递归版本需要的辅助类
    // 要处理的是什么范围上的排序
    public static class Op{
        public int l;
        public int r;

        public Op(int left, int right){
            l = left;
            r = right;
        }
    }

    // 快排3.0 非递归版本
    public static void quickSort2(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        int N = arr.length;
        swap(arr, (int) (Math.random() * N), N - 1);
        int[] equalArea = netherlandsFlag(arr, 0, N - 1);
        // 等于区域的左右边界
        int el = equalArea[0];
        int er = equalArea[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, el - 1));
        stack.push(new Op(er + 1, N - 1));
        while (!stack.isEmpty()){
            Op op = stack.pop();
            if(op.l < op.r){
                swap(arr, op.l + (int) (Math.random() * (op.r - op.l + 1)), op.r);
                equalArea = netherlandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(op.l, el - 1));
                stack.push(new Op(er + 1, op.r));
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{123,435,6547,876,90,234,78,32,432};
        quickSort2(arr);
        for(int d : arr){
            System.out.print(d + " ");
        }
    }

}
