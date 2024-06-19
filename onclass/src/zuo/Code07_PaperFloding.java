package zuo;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 折纸条，从下到上折N次展开，打印纸条从上到下的凹，凸
 *      请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开
 *      此时折痕是凹下去的，即折痕突起的方向指向纸条的背面 如果从纸条的下边向上方连续对折2次，
 *      压出折痕后展开 此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。 给定一个输入参数N，
 *      代表纸条都从下边向上方连续对折N次 请从上到下打印所有折痕的方向。 N=1时，打印: down N=2时，打印: 凹 凹 凸
 * 解法：二叉树的中序遍历（不利用任何容器）
 */
public class Code07_PaperFloding {

    public static void printAllFolds(int N){
        process(1, N, true);
        System.out.println();
        inOrder(N);
    }

    /**
     * 功能：中序打印想象节点为头的整棵树（优点：节点数为2^N -1,额外空间占用为递归深度O(N)）
     * 1 当前来到一个节点
     * 2 这个节点在第i层，一共N层，N是固定不变的
     * 3 这个节点如果是凹的down=true，凸的down=false
     * @param i
     * @param N
     * @param down
     */
    public static void process(int i, int N, boolean down){
        if(i > N){
            return;
        }
        process(i + 1, N, true);//想象下一层的凹节点（左子树）
        System.out.print(down? "凹 " : "凸 ");
        process(i + 1, N, false);//想象下一层的凸节点（右子树）
    }

    /** 拓展：非递归实现 ************************************************/

    // 构建满二叉树
    public static Node buildTree(int n){
        if(n < 1) {
            return null;
        }
        // 使用队列宽度遍历（层次遍历）
        LinkedList<Node> list = new LinkedList<>();
        Node node = new Node("凹");
        list.add(node);
        int i = 0;
        while(i < ((1 << n) - 1)) {
            Node cur = list.poll();
            i = i+2;
            if(i < ((1 << n) - 1)) {
                cur.left = new Node("凹");
                list.add(cur.left);
                cur.right = new Node("凸");
                list.add(cur.right);
            }
        }
        return node;
    }

    // 中序遍历
    public static void inOrder(int n) {
        Node root = buildTree(n);
        Stack<Node> stack = new Stack<>();
        if(root != null) {
            while (stack.size() > 0 || root != null) {
                if(root != null) {
                    stack.push(root);
                    root = root.left;
                }else {
                    root = stack.pop();
                    System.out.print(root.value + " ");
                    root = root.right;
                }
            }
        }
    }

    public static class Node {
        String value;
        public Node left;
        public Node right;

        public Node(String value){
            this.value = value;
        }
    }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }
}
