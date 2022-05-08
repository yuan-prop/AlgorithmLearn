package com.prac.day01;

import com.offer.linkedlist.ListNode;

import java.util.HashMap;
import java.util.Map;

public class TwoNumSum {

    /**
     * 求数组中的两数之和==target 的数组下标，返回的下标不相同（两个数）
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target){

        if(nums == null){
            return null;
        }
        int res[] = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i < nums.length; i++){
            // 说明已经找到补数
            if(map.containsKey(nums[i])){
                res[0] = i;
                res[1] = map.get(nums[i]);
                return res;
            }
            // 存入当前数组值的补数
            map.put(target-nums[i], i);
        }
        return null;
    }

    /**
     * 两数相加
     * @param
     */
    public ListNode addTwoNums(ListNode n1, ListNode n2){

        return null;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,5,7,8,14,6};
        int[] index = twoSum(arr, 4);
        for(int i:index){
            System.out.println(i);
        }
    }

}
