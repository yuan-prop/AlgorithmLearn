package com.offer.binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TreeTest {

    static class TreeNode{

        int data;

        TreeNode left;

        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }
    }

//    3
//    /\
//    9 20
//   /\   /\
//  1  2  15 7
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode left1 = new TreeNode(9);
        TreeNode right1 = new TreeNode(20);
        TreeNode left2 = new TreeNode(15);
        TreeNode right2 = new TreeNode(7);
        root.left = left1;
        root.right = right1;
        right1.left = left2;
        right1.right = right2;
        left1.left = new TreeNode(1);
        left1.right = new TreeNode(2);

        levelOrder(root);
        System.out.println();
        zOrder(root);
        zOrder2(root);

    }

    //二叉树每层从左往右层序遍历
    public static void levelOrder(TreeNode node){
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(node);
        while(!list.isEmpty()){
            TreeNode treeNode = list.pop();
            System.out.print(treeNode.data+" ");
            if(treeNode.left != null) list.add(treeNode.left);
            if(treeNode.right != null) list.add(treeNode.right);
        }
    }

    /**
     * 按照之字形(Z字形)打印二叉树，
     * 即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，其他行以此类推
     * @param node
     */
    public static void zOrder(TreeNode node){
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(node);
        int level = 1;
        System.out.println("方式1：");
        while(!list.isEmpty()){
            //遍历每一层
//            Double log = Math.log(value) / Math.log(n);
//            int level = Double.valueOf(Math.floor(Math.pow(2, n - 1))).intValue();
            int currentSize = list.size();
            // 要知道到第几层了
            ArrayList<Integer> arr = new ArrayList<>();
            for(int i = 0; i < currentSize; i++){
                TreeNode treeNode = list.pop();
                arr.add(treeNode.data);
                if (treeNode.left != null) list.add(treeNode.left);
                if (treeNode.right != null) list.add(treeNode.right);
            }
            if(level % 2 == 0){
                Collections.reverse(arr);
            }
            System.out.println(arr);
            level++;
        }
    }

    public static void zOrder2(TreeNode node){
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(node);
        System.out.println("方式2：");
        int level = 1;
        while(!list.isEmpty()){
            //遍历每一层
//            Double log = Math.log(value) / Math.log(n);
//            int level = Double.valueOf(Math.floor(Math.pow(2, n - 1))).intValue();
            int currentSize = list.size();
            // 要知道到第几层了
            ArrayList<Integer> arr = new ArrayList<>();
            for(int i = 0; i < currentSize; i++){
                TreeNode treeNode = list.pop();
                arr.add(treeNode.data);
                if (treeNode.left != null) list.add(treeNode.left);
                if (treeNode.right != null) list.add(treeNode.right);
            }
            if(level % 2 == 0){
//                Collections.reverse(arr);
                for(int j = arr.size()-1; j >= 0; j--){
                    System.out.print(arr.get(j)+" ");
                }
            }else {
                for(int j = 0; j < arr.size(); j++){
                    System.out.print(arr.get(j)+" ");
                }
            }
            System.out.println();
//            System.out.println(arr);
            level++;
        }
    }

    // todo 递归遍历

}
