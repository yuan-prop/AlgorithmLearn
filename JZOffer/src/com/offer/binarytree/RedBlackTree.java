package com.offer.binarytree;

import static com.offer.binarytree.RedBlackTree.Color.BLACK;
import static com.offer.binarytree.RedBlackTree.Color.RED;

/**
 * @author wzy
 * @since 2024/6/3
 */
public class RedBlackTree {

    enum Color {
        RED, BLACK;
    }

    private Node root;

    private static class Node {
        int key;
        Object value;
        Node left;
        Node right;
        Node parent; // 父节点
        Color color = RED; // 颜色

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        // 是否是左孩子
        boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        // 叔叔
        Node uncle(){
            if(parent == null || parent.parent == null){
                return null;
            }
            if(parent.isLeftChild()){
                return parent.parent.right;
            }else{
                return parent.parent.left;
            }
        }

        // 兄弟
        Node sibling() {
            if(parent == null){
                return null;
            }
            if(this.isLeftChild()){
                return parent.right;
            }else {
                return parent.left;
            }
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public String name(){
            return this.key + (this.color == BLACK ? "B" : "R");
        }
    }

    // 判断红
    boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    // 判断黑
    boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    // 右旋 1.parent的处理 2.旋转后新根的父子关系
    private void rightRotate(Node pink) {
        Node parent = pink.parent;
        Node yellow = pink.left;
        Node green = yellow.right;
        if(green != null) {
            green.parent = pink;
        }
        yellow.right = pink;
        yellow.parent = parent;
        pink.left = green;
        pink.parent = yellow;
        if(parent == null){
            // 说明pink原来是树的根节点
            root = yellow;
        }else if(parent.left == pink){
            parent.left = yellow;
        }else {
            parent.right = yellow;
        }
    }

    // 左旋
    private void leftRotate(Node pink) {
        Node parent = pink.parent;
        Node yellow = pink.right;
        Node green = yellow.left;
        if(green != null) {
            green.parent = pink;
        }
        yellow.left = pink;
        yellow.parent = parent;
        pink.right = green;
        pink.parent = yellow;
        if(parent == null){
            root = yellow;
        }else if(parent.left == pink){
            parent.left = yellow;
        }else {
            parent.right = yellow;
        }
    }

    /**
     * 新增或更新
     * 正常增、遇到红红不平衡进行调整
     * @param key 键
     * @param value 值
     */
    public void put(int key, Object value) {
        Node p = root;
        Node parent = null;
        while(p != null){
            parent = p;
            if(key < p.key) {
                p = p.left;
            }else if(p.key < key) {
                p = p.right;
            } else {
                p.value = value; // 更新
                return;
            }
        }
        Node inserted = new Node(key, value);
        if(parent == null){
            root = inserted;
        }else if(key < parent.key){
            parent.left = inserted;
            inserted.parent = parent;
        }else {
            parent.right = inserted;
            inserted.parent = parent;
        }
//        fixRedRed(inserted);
        fixAfterInsertion(inserted);
    }

    // fixRedRed的非递归版本
    private void fixAfterInsertion(Node x) {
        x.color = RED;
        while (x != null && x != root && x.parent.color == RED) {
            // 插入节点的父亲为红色，触发红红相邻
            Node y = x.uncle();
            if(x.parent == root){
                System.out.println();
            }
            if (x.parent.isLeftChild()) {
                if (isRed(y)) { // 说明y != null
                    x.parent.color = BLACK;
                    y.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    // x父亲为红色说明父亲是非根节点 因此x.parent.parent != null
                    if (!x.isLeftChild()) {
                        x = x.parent;
                        if(x.parent.color == RED){
                            System.out.println("no way!");
                        }
                        leftRotate(x);
                    }
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    rightRotate(x.parent.parent);
                }
            } else {
                if (isRed(y)) {
                    x.parent.color = BLACK;
                    y.color = BLACK;
                    x.parent.parent.color = RED;
                    x = x.parent.parent;
                } else {
                    if (x.isLeftChild()) {
                        x = x.parent;
                        if(x.parent.color == RED){
                            System.out.println("no way!");
                        }
                        rightRotate(x);
                    }
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    leftRotate(x.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * 插入节点均视为红色
     * case1: 插入节点为根节点，将根节点变黑
     * case2: 插入节点的父节点为黑色，树的红黑性质不变，无需调整
     *
     * 插入节点的父亲为红色，触发红红相邻
     * case3: 叔叔为红色
     * case4: 叔叔为黑色
     * @param x
     */
    void fixRedRed(Node x){
        // case1: 插入节点为根节点，将根节点变黑
        if(x == root){
            x.color = BLACK;
            return;
        }
        // case2: 插入节点的父节点为黑色，树的红黑性质不变，无需调整
        if (isBlack(x.parent)) {
            return;
        }
        // case3: 当红红相邻 叔叔为红色时
        // 需要将父亲变黑，祖父变红，然后对祖父做递归处理
        Node parent = x.parent;
        Node uncle = x.uncle();
        Node grandparent = parent.parent;
        if(isRed(uncle)){
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;
            fixRedRed(grandparent);
            return;
        }
        /* case4 当红红相邻 叔叔为黑色
         * 1 父亲为左孩子，插入节点也是左孩子，此时即LL不平衡
         * 2 父亲为左孩子，插入节点是右孩子，此时即LR不平衡
         * 3 父亲为右孩子，插入节点也是右孩子，此时即RR不平衡
         * 4 父亲为右孩子，插入节点是左孩子，此时即RL不平衡
         */
        if(parent.isLeftChild() && x.isLeftChild()){// LL
            parent.color = BLACK;
            grandparent.color = RED;
            rightRotate(grandparent);
        }else if(parent.isLeftChild()){// LR
            leftRotate(parent);
            x.color = BLACK;
            grandparent.color = RED;
            rightRotate(grandparent);
        } else if (!x.isLeftChild()) { // RR
            parent.color = BLACK;
            grandparent.color = RED;
            leftRotate(grandparent);
        } else { // RL
            rightRotate(parent);
            x.color = BLACK;
            grandparent.color = RED;
            leftRotate(grandparent);
        }
    }

    /**
     * 删除一个节点有如下情况：
     *     简单情况(doRemove):
     *      case0 如果删除节点有两个孩子，化简成只有一个孩子或没孩子
     *      case1 删的是根节点
     *      case2 删的是黑，剩下的是红，剩下的这个红节点变黑
     *     双黑的情况(fixDoubleBlack):
     *      case3 被调整节点的兄弟为红，此时两个侄子定为黑，通过旋转过渡到case4或5
     *      case4 被调整节点的兄弟为黑，两个侄子都为黑，只需要变色即可
     *      case5 被调整节点的兄弟为黑，至少一个红侄子
     *          如果兄弟是左孩子，左侄子是红 LL不平衡
     *          如果兄弟是左孩子，右侄子是红 LR不平衡
     *          如果兄弟是右孩子，右侄子是红 RR不平衡
     *          如果兄弟是右孩子，左侄子是红 RL不平衡
     * 正常删、会用到李代桃僵、遇到黑黑平衡进行调整
     * @param key 键
     */
    public void remove(int key) {
        Node deleted = find(key);
        if(deleted == null) {
            return;
        }
        doRemove(deleted);
    }

    // 处理删除节点和剩下节点都是黑，触发双黑的情况
    private void fixDoubleBlack(Node x) {
        if(x == root) {
            return;
        }
        Node parent = x.parent;
        Node sibling = x.sibling();
        // case3 兄弟节点红色(两个侄子定为黑)
        if(isRed(sibling)) {
            if(x.isLeftChild()) {
                leftRotate(x.parent);
            } else {
                rightRotate(x.parent);
            }
            parent.color = RED;
            sibling.color = BLACK;
            fixDoubleBlack(x);
            return;
        }
        // 兄弟为空 则自己必定为红，与自己为黑矛盾，因此 sibling != null
        // case4 兄弟是黑色，两个侄子也是黑色
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = RED;
            if(isRed(parent)) {
                parent.color = BLACK;
            } else {
                fixDoubleBlack(parent);
            }
        }
        // case5 兄弟是黑色，侄子有红色
        else {
            // 如果兄弟是左孩子，左侄子是红 LL不平衡
            if(sibling.isLeftChild() && isRed(sibling.left)) {
                rightRotate(parent);
                sibling.left.color = BLACK;
                sibling.color = parent.color;
            }
            // 如果兄弟是左孩子，右侄子是红 LR不平衡
            else if(sibling.isLeftChild() && isRed(sibling.right)) {
                Node right = sibling.right;
                leftRotate(sibling);
                rightRotate(parent);
                right.color = parent.color;
            }
            // RL
            else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                Node left = sibling.left;
                rightRotate(sibling);
                leftRotate(parent);
                left.color = parent.color;
            }
            // RR
            else {
                leftRotate(parent);
                sibling.right.color = BLACK;
                sibling.color = parent.color;
            }
            parent.color = BLACK;
        }
    }

    private void doRemove(Node deleted) {
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;
        // 没有孩子
        if(replaced == null) {
            // case1 删除的是根节点
            if(deleted == root){
                root = null;
            } else {
                // 删除的是叶子
                if(isBlack(deleted)){
                    // 复杂处理
                    fixDoubleBlack(deleted);
                } else {
                    // 红色叶子 无需处理
                }
                if(deleted.isLeftChild()){
                    parent.left = null;
                }else {
                    parent.right = null;
                }
                deleted.parent = null;
            }
            return;
        }
        // 有一个孩子 此时待删除节点不可能是红色，因为红色必然有两个黑子节点
        if(deleted.left == null || deleted.right == null) {
            // case1 删除的是根节点
            if(deleted == root){
                root.key = replaced.key;
                root.value = replaced.value;
                root.left = root.right = null;
            } else {
                if(deleted.isLeftChild()){
                    parent.left = replaced;
                } else {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null;
                if(isBlack(deleted) && isBlack(replaced)){
                    // 复杂处理
                    fixDoubleBlack(replaced);
                } else {
                    // case2 删黑剩红 变色
                    replaced.color = BLACK;
                }
            }
            return;
        }
        // case0 如果删除的节点有两个孩子,李代桃僵，后继节点属性赋值到待删除节点 化简成只有一个孩子或没有
        int t = deleted.key;
        deleted.key = replaced.key;;
        replaced.key = t;

        Object v = deleted.value;
        deleted.value = replaced.value;
        replaced.value = v;
        doRemove(replaced);
    }

    // 查找删除节点
    Node find(int key){
        Node p = root;
        while(p != null){
            if(key < p.key){
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    // 查找删除节点后的剩余子节点
    Node findReplaced(Node deleted) {
        if(deleted.left == null && deleted.right == null) {
            return null;
        }
        if(deleted.left == null){
            return deleted.right;
        }
        if(deleted.right == null) {
            return deleted.left;
        }
        Node s = deleted.right;
        while(s.left  != null){
            s = s.left;
        }
        return s;
    }

    /**
     * 验证红黑树
     * 规则1 所有节点共有两种颜色 红与黑
     * 规则2 所有null（叶子的子节点）视为黑色
     * 规则1、2不用验证
     * @param tree
     */
    private void isValid(RedBlackTree tree) {
        if(tree.root == null) {
            return;
        }
        // 规则4 根节点必须是黑
        if(tree.root.color != BLACK) {
            throw new AssertionError("root's color must be black!");
        }
        validateRedBlackInvariant(null, tree.root, 0, new MutableHeight());
    }

    private static class MutableHeight {
        private Integer value;
    }

    private void validateRedBlackInvariant(Node parent, Node node, int thisHeight, MutableHeight height) {
        // 规则5 每条路径黑色相等 node == null 表示一条路径走到头
        if(node == null) {
            // First NIL node?
            if(height.value == null) {
                height.value = thisHeight;
            } else if (height.value != thisHeight) {
                throw new AssertionError(
                        "Black-height rule violated(height = "
                        + height.value
                        + "; thisHeight = "
                        + thisHeight
                        +"; parent = "
                        + parent.key
                        + ")");
            }
            return;
        }

        // Count black nodes on path
        if(node.color == BLACK) {
            thisHeight++;
        }

        // 规则3 红色不相邻
        else if (parent != null && parent.color == RED) {
            throw new AssertionError("Node " + node.key + "has adjacent red node!");
        }
        validateRedBlackInvariant(node, node.left, thisHeight, height);
        validateRedBlackInvariant(node, node.right, thisHeight, height);
    }

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.put(2, null);
        redBlackTree.put(3, null);
        redBlackTree.put(4, null);
        redBlackTree.put(6, null);
        redBlackTree.put(8, null);
        redBlackTree.put(9, null);
        redBlackTree.put(12, null);
        redBlackTree.put(34, null);
        redBlackTree.put(65, null);
        redBlackTree.put(45, null);
        redBlackTree.put(67, null);
        redBlackTree.put(87, null);
        redBlackTree.put(100, null);
//        for (int i = 0; i < 1000000; i++) {
//            int v = (int)(Math.random() * i) + 1;
//            redBlackTree.put(v, null);
//        }
        redBlackTree.isValid(redBlackTree);
        PrintTree.PrintNodeInfo<RedBlackTree.Node> info = new PrintTree.PrintNodeInfo<>(redBlackTree.root, RedBlackTree.Node::name, RedBlackTree.Node::getLeft, RedBlackTree.Node::getRight);
        PrintTree.printTree(info);
    }


}
