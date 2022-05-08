package zuo;

/**
 * 折纸条，从上到下折N次展开，打印纸条从上到下的凹，凸
 * 解法：二叉树的中序遍历（不利用任何容器）
 */
public class Code07_PaperFloding {

    public static void printAllFolds(int N){
        process(1, N, true);
    }

    /**
     * 功能：中序打印想象节点为头的整棵树（优点：节点数为2^N -1,额外空间占用为递归深度O(N)）
     * 1 当前来到一个节点
     * 2 这个节点在第i层，一共N层，N是固定不变的
     * 3 这个节点如果是凹的down=T，凸的down=F
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

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }
}
