package com.offer.binarytree;

/**
 * 平衡二叉树的实现：AVL树
 * @author wzy
 * @since 2024/5/29
 */
public class AVLTree {

    private AVLNode root;

    static class AVLNode {
        private int key;
        private Object value;
        private AVLNode left;
        private AVLNode right;
        private int height = 1;

        public AVLNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public AVLNode(int key) {
            this.key = key;
        }

        public AVLNode(int key, Object value, AVLNode left, AVLNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public AVLNode getLeft() {
            return left;
        }

        public void setLeft(AVLNode left) {
            this.left = left;
        }

        public AVLNode getRight() {
            return right;
        }

    }

    // 求节点的高度
    private int height(AVLNode node){
        return node == null ? 0 : node.height;
    }

    // 更新节点高度（新增、删除、旋转）
    private void updateHeight(AVLNode node) {
        node.height = Integer.max(height(node.left), height(node.right)) + 1;
    }

    // 平衡因子 (balance factor)= 左子树高度 - 右子树高度
    // bf = 0,1,-1表示左右平衡
    // bf > 1时 左子树太高反之右边太高
    private int bf(AVLNode node){
        return height(node.left) - height(node.right);
    }

    /**
     * 右旋
     * @param red 要旋转的节点(失衡节点)
     * @return 新的根节点
     * red向下的 yellow向上的 green换爹的
     */
    private AVLNode rightRotate(AVLNode red) {
        // 如果需要右旋，则左子树不为空,因此yellow!=null
        AVLNode yellow = red.left;
        AVLNode green = yellow.right;
        yellow.right = red; // 上位
        red.left = green; // 换爹
        // 更新节点高度
        updateHeight(red);
        updateHeight(yellow);
        return yellow;
    }

    /**
     * 左旋
     * @param red 要旋转的节点(失衡节点)
     * @return 新的根节点
     */
    private AVLNode leftRotate(AVLNode red) {
        AVLNode yellow = red.right;
        AVLNode green = yellow.left;
        yellow.left = red;
        red.right = green;
        updateHeight(red);
        updateHeight(yellow);
        return yellow;
    }

    // 先左旋左子树，再右旋根节点
    private AVLNode leftRightRotate(AVLNode node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    // 先右旋右子树，再左旋根节点
    private AVLNode rightLeftRotate(AVLNode node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    // 检查节点是否失衡，重新平衡代码
    private AVLNode balance(AVLNode node){
        if (node == null) {
            return null;
        }
        int bf = bf(node);
        if(bf > 1){
            if(bf(node.left) >= 0){ // LL
                return rightRotate(node);
            }else{ // LR
                return leftRightRotate(node);
            }
        }else if(bf < -1){
            if(bf(node.right) <= 0){ // RR
                return leftRotate(node);
            }else{ // RL
                return rightLeftRotate(node);
            }
        }
        return node;
    }

    public void put(int key, Object value){
        root = doPut(root, key, value);
    }

    private AVLNode doPut(AVLNode node, int key, Object value){
        // 1 找到空位，创建新节点
        if(node == null){
            return new AVLNode(key, value);
        }
        // 2. key已经存在，更新
        if(node.key == key){
            node.value = value;
            return node;
        }
        // 3. 继续查找
        if(key < node.key){
            node.left = doPut(node.left, key, value); // 向左查找
        }else {
            node.right = doPut(node.right, key, value); // 向右查找
        }
        updateHeight(node);
        return balance(node);
    }

    public void remove(int key) {
        root = doRemove(root, key);
    }

    private AVLNode doRemove(AVLNode node, int key) {
        // 1 node == null
        if(node == null){
            return null;
        }
        // 2 没找到key
        if(key < node.key){
            node.left = doRemove(node.left, key);
        }else if(node.key < key){
            node.right = doRemove(node.right, key);
        }else{
            // 3 找到key
            if(node.left == null && node.right == null){
                // 1)没有孩子
                return null;
            }else if(node.left == null){
                // 2）只有一个孩子
                node = node.right;
            }else if(node.right == null){
                node = node.left;
            }else{
                // 3)有两个孩子
                AVLNode s = node.right;
                while(s.left != null){
                    s = s.left;
                }
                // s 为后继节点 用它来顶替被删节点
                s.right = doRemove(node.right, s.key);
                s.left = node.left;
                node = s;
            }
            node = doRemove(node, key);
        }
        // 4 更新高度
        updateHeight(node);
        // 5 balance
        return balance(node);
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.put(1, null);
        tree.put(10, null);
        tree.put(18, null);
        tree.put(13, null);
        tree.put(234, null);
        tree.put(43, null);
        tree.put(12, null);
        tree.put(50, null);
        tree.put(16, null);
        tree.put(54, null);
        tree.put(34, null);
        tree.put(67, null);
        tree.put(80, null);
        tree.put(35, null);
        tree.put(96, null);
        tree.put(23, null);
        tree.put(2, null);
        tree.put(3, null);
        tree.put(1000, null);
        tree.put(100, null);
        tree.put(200, null);
        tree.put(0, null);
        tree.put(-200, null);
        tree.put(-10, null);
        AVLNode a = tree.root;
        PrintTree.PrintNodeInfo<AVLNode> info = new PrintTree.PrintNodeInfo<>(a, AVLNode::getKey, AVLNode::getLeft, AVLNode::getRight);
        System.out.println(PrintTree.printTree(info));
    }




    /*
     * 失衡的情况：
     *
     * LL（失衡节点应该做一次右旋）
     *  - 失衡节点的 bf > 1,即左边更高
     *  - 失衡节点的左孩子的 bf >=0 即左孩子这边也是左边更高或等高
     *
     * LR（左孩子做一次左旋，失衡节点再做一次右旋）
     *  - 失衡节点的 bf > 1,即左边更高
     *  - 失衡节点的左孩子的 bf < 0 即左孩子这边是右边更高
     *
     * RL（右孩子做一次右旋，失衡节点再做一次左旋）
     *  - 失衡节点的 bf < -1,即右边更高
     *  - 失衡节点的右孩子的 bf > 0 即右孩子这边左边更高
     *
     * RR（失衡节点应该做一次左旋）
     *  - 失衡节点的 bf < -1,即右边更高
     *  - 失衡节点的右孩子的 bf <= 0 即右孩子这边右边更高或等高
     */



}
