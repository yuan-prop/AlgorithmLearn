package zuo.class08_12;

/**
 * 在二叉树中找出最大的搜索二叉子树(注意：如果一棵树maxBSTSubSize==整棵树的size，则这棵树就是BST)
 */
public class Code05_MaxSubBSTSize {

    public class Node{
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public static class Info {
        // 最大搜索二叉树大小
        public int maxBSTSubtreeSize;
        // 当前整棵子树的大小
        public int allSize;
        // 当前树中所有节点最大值
        public int max;
        public int min;

        public Info(int maxBSTSubtreeSize, int allSize, int max, int min) {
            this.maxBSTSubtreeSize = maxBSTSubtreeSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 递归套路
     * 父节点收集左右子节点的信息后汇聚，类似后序遍历
     * @param x
     * @return
     */
    public static Info process(Node x){
        if(x == null){
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int max = x.value;
        int min = x.value;
        int allSize = 1;
        if(leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if(rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        // 记录子树最大BST大小（节点个数）
        int p1 = -1;//用p1记录左树的maxBSTSubtreeSize
        if(leftInfo != null){
            p1 = leftInfo.maxBSTSubtreeSize;
        }
        int p2 = -1;//用p1记录右树的maxBSTSubtreeSize
        if(rightInfo != null){
            p2 = rightInfo.maxBSTSubtreeSize;
        }
        // 可能整个树是BST，用p3记录整个树的maxBSTSubtreeSize
        int p3 = -1;
        // 判断左边和右边是否为BST(搜索二叉树)
        boolean leftBST = leftInfo == null ? true : leftInfo.allSize == leftInfo.maxBSTSubtreeSize;
        boolean rightBST = rightInfo == null ? true : rightInfo.allSize == rightInfo.maxBSTSubtreeSize;;
        if(leftBST && rightBST){// 左右均为BST,进一步判断加上X节点后，整个树是否为BST
            // 注意leftInfo和rightInfo可能为null（下同）
            boolean leftMaxLessX = leftInfo == null ? true : leftInfo.max < x.value;
            boolean rightMinMoreX = rightInfo == null ? true : x.value < rightInfo.min;
            if(leftMaxLessX && rightMinMoreX){
                int leftSize = leftInfo == null ? 0 : leftInfo.maxBSTSubtreeSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.maxBSTSubtreeSize;
                p3 =  + rightSize + 1;
            }
        }

        int maxBSTSubtreeSize = Math.max(p1, Math.max(p2, p3));

        return new Info(maxBSTSubtreeSize, allSize, max, min);
    }

    /**
     * 求最大BST大小的主函数
     * @param head
     * @return
     */
    public static int maxSubBSTSize(Node head){
        if(head == null){
            return 0;
        }
        return process(head).maxBSTSubtreeSize;
    }

    public static void main(String[] args) {

    }

}
