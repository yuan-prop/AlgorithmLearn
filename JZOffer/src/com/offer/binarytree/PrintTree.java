package com.offer.binarytree;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author wzgl
 * @version 1.0
 * @date 2023/5/30 13:48
 */
public class PrintTree {

    /**
     * 打印二叉树
     *
     * @param info 树信息
     * @param <T>  树泛型
     * @return 树
     */
    public static <T> String printTree(PrintNodeInfo<T> info) {
        if (info == null) {
            return "";
        }
        T node = info.getNode();
        int deep = findDepth(node, info);
        if (deep > info.maxDepth) {
            throw new RuntimeException("太深了， 不好打印啊");
        }
        int size = (1 << deep) - 1; // 树中节点总数， 1 << n 等于 2的n次方
        List<PrintNode>[] lists = new ArrayList[deep];
        for (int i = 0; i < deep; i++) {
            lists[i] = new ArrayList<>();
        }
        info.setLists(lists);
        addPrint(info, node, size >> 1, 0, deep);
        for (List<PrintNode> list : lists) {
            list.sort((a, b) -> Integer.compare(a.index, b.index));
        }
        StringBuilder all = new StringBuilder();
        for (List<PrintNode> list : lists) {
            StringBuilder sb = new StringBuilder();
            int pre = -1;
            for (PrintNode printNode : list) {
                int index = printNode.index;
                if(index < 0){
                    System.out.println();
                }
                String value = printNode.value;
                int i = index - pre - 1;
                for (int j = 0; j < i; j++) {
                    sb.append(info.getWight());
                }
                pre = index;
                sb.append(value);
            }
            all.append(sb).append(info.line);
        }
        return all.toString();
    }

    /**
     * 前序 遍历，获取节点，并存储到lists中（按层存储，可以层序遍历实现）
     *
     * @param info  树信息
     * @param node  节点
     * @param index 当前元素的坐标为位置
     * @param item  当前深度 0起始
     * @param depth 树深度
     * @param <T>   树泛型
     */
    private static <T> void addPrint(PrintNodeInfo<T> info, T node, int index, int item, int depth) {
        // index < 0 时 node 为空
        if (node == null) {
            return;
        }
        List<PrintNode>[] lists = info.getLists();
        List<PrintNode> list = lists[item];
        String s = info.valStr == null ? String.valueOf(info.val.apply(node)) : info.valStr.apply(node);
        PrintNode printNode = new PrintNode(index, s);
        list.add(printNode);
        if(item == 3){
            System.out.println();
        }else if(item == 4){
            System.out.println();
        }
        int w = 1 << (depth - item - 2);
        addPrint(info, info.left.apply(node), index - w, item + 1, depth);
        addPrint(info, info.right.apply(node), index + w, item + 1, depth);
    }

    /**
     * 获取深度
     *
     * @param node 节点
     * @param info 树信息
     * @param <T>  树泛型
     * @return 深度
     */
    private static <T> int findDepth(T node, PrintNodeInfo<T> info) {
        if (node == null) {
            return 0;
        }
        return Math.max(findDepth(info.left.apply(node), info), findDepth(info.right.apply(node), info)) + 1;
    }

    /**
     * 打印的信息
     */
    private static class PrintNode {
        // 所处的坐标
        private int index;
        // 打印的内容
        private String value;

        public PrintNode() {
        }

        private PrintNode(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 存储一棵树的信息
     *
     * @param <T> 树节点的泛型
     */
    public static class PrintNodeInfo<T> {

        // 跟节点
        private T node;

        // 获取当前值，默认为Integer类型，若是其他的类型，则需要修改
        private Function<T, Object> val;

        // 获取左节点
        private Function<T, T> left;

        // 获取右节点
        private Function<T, T> right;

        // 层序遍历时，存储每一层的节点
        private List<PrintNode>[] lists;

        // 打印的值,若没有设置，则取val（自定义用）
        private Function<T, String> valStr;

        // 占位符，若节点为null,则需要空格占位（长度可以自定义）。
        private String wight = " ";

        // 每一层拼接换行符，（可以自定义）
        private String line = "\n\n";

        // 若是树深大于此值，则抛出异常，防止打印的过大（默认8，可自定义）
        private int maxDepth = 8;

        public PrintNodeInfo() {
        }

        public PrintNodeInfo(T node, Function<T, Object> val, Function<T, T> left, Function<T, T> right) {
            this.node = node;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public T getNode() {
            return node;
        }

        public void setNode(T node) {
            this.node = node;
        }

        public Function<T, Object> getVal() {
            return val;
        }

        public void setVal(Function<T, Object> val) {
            this.val = val;
        }

        public Function<T, T> getLeft() {
            return left;
        }

        public void setLeft(Function<T, T> left) {
            this.left = left;
        }

        public Function<T, T> getRight() {
            return right;
        }

        public void setRight(Function<T, T> right) {
            this.right = right;
        }

        public List<PrintNode>[] getLists() {
            return lists;
        }

        public void setLists(List<PrintNode>[] lists) {
            this.lists = lists;
        }

        public Function<T, String> getValStr() {
            return valStr;
        }

        public void setValStr(Function<T, String> valStr) {
            this.valStr = valStr;
        }

        public String getWight() {
            return wight;
        }

        public void setWight(String wight) {
            this.wight = wight;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public int getMaxDepth() {
            return maxDepth;
        }

        public void setMaxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
        }
    }

}
