package com.offer.find;

/**
 * 异或求数组中出现奇数次的两个数
 */
public class ArrNum {

    public static void main(String[] args) {

        int[] arr = {1,1,3,2,2,5,6,6,6,6};
        printOddTimesNum2(arr);

    }

    public static void printOddTimesNum2(int arr[]){
        int eor = 0;
        for(int i=0;i<arr.length;i++){
            eor ^= arr[i];
        }
        // eor = a ^ b;
        // eor != 0;
        // eor 二进制位中必有一个位置上是1
        int rightOne = eor & (~eor + 1);// 提取出二进制最右位上1，假设为第x位，其他位全是0
        //  eor的x位不为0，说明a和b的第x位上分别一个是0，另一个是1
        int onlyOne = 0;
        for(int cur : arr){
            /**
             * 1. 从arr中找出第x位为0的数，设为第1组
             * (cur & rightOne) == 0
             * 2 (cur & rightOne) != 0说明cur的x位为1，这些数设为第2组
             * 3 a和b分别被分到了第1组和第2组
             * 第1组中所有数求异或得到a,
             * 然后 eor ^ a = a ^ b ^ a = a ^ a ^ b = b;
             */
            if((cur & rightOne) == 0){//或者 != 0
                onlyOne ^= cur;
            }
        }
        System.out.println("奇数次出现的数："+ onlyOne + "和"+(eor ^ onlyOne));
    }
}
