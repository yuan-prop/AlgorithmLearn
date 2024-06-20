package com.offer.find;

/**
 * 华为面试HJ28 素数伴侣
 * 来源：牛~客~网
 *
 * @author wzy
 * @since 2024/6/19
 */
import java.util.Scanner;
import java.util.ArrayList;


public class PrimeCompanion {

    //这里包含了判断素数的方法
    //小技巧！！！除了1，2，3之外的素数中都不是偶数，那么和是素数的话就是奇数+偶数
    //那么可以分成两堆,一堆偶数，一堆奇数
    //匈牙利算法，先到先得 能让就让
    //有机会上，没机会创造机会也要上
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别<
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n = in.nextInt();
            int[] arr = new int[n];
            for(int i = 0; i < n; i++) {
                arr[i] = in.nextInt();
            }
            ArrayList<Integer> evens = new ArrayList<Integer>();
            ArrayList<Integer> odds = new ArrayList<Integer>();
            for(int i = 0; i < n; i++) {
                if((arr[i] & 1) != 1) {
                    // 偶数
                    evens.add(arr[i]);
                } else {
                    // 奇数
                    odds.add(arr[i]);
                }
            }
            int[] evensMatch = new int[evens.size()];
            int ret = 0;
            // 确定奇数去找偶数
            for(int i = 0; i < odds.size(); i++) {
                int[] used = new int[evens.size()];
                if(find(odds.get(i), evens, used, evensMatch)) {
                    ret++;
                }
            }
            System.out.println(ret);
        }
    }

    // 判断num是否为素数, 注意
    public static boolean isPrime(int num) {
        if(num == 0) return false;
        for(int i = 2; i * i <= num; i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }

    // x是奇数
    public static boolean find(int x, ArrayList<Integer> evens, int[] used, int[] evensMatch) {
        for(int i = 0; i < evens.size(); i++) {
            if(used[i] == 0 && isPrime(evens.get(i) + x)) {
                used[i] = 1;
                //如果第i个偶数没有伴侣
                //或者第i个偶数原来有伴侣，并且该伴侣能够重新找到伴侣的话(这里有递归调用)
                //则奇数x可以设置为第i个偶数的伴侣
                //这里采用了匈牙利算法，能让则让
                if(evensMatch[i] == 0 || find(evensMatch[i], evens, used, evensMatch)) {
                    evensMatch[i] = x;
                    return true;
                }
            }
        }
        return false;
    }
}
