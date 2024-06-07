package com.offer.binarytree;

import java.util.Scanner;

/**
 * 哈夫曼树：树的最小带权路径之和
 *
 * @author wzy
 * @since 2024/6/6
 */
public class HuffmanTree {


    static class HTNode {
        private int weight;// 节点的权
        private int parent;// 父节点索引
        private int lch;// 左子节点索引
        private int rch; // 右子节点索引
    }

    /**
     * 构造哈夫曼树
     * 实际上是构造HTNode数组，它对应一个哈夫曼树。由树可以推出数组，由数组同样可以推出树结构
     * 哈夫曼算法口诀：1、构造森林全是根；2、选用两小造新树；3、删除两小添新人；4、重复2、3剩单根。
     * 总结哈夫曼树的特点：
     * 1、哈夫曼树的结点的度数为0或者2，没有度为1的结点。权越大离根越近
     * 2、在哈夫曼算法中，初始时有n棵二叉树，要经过n-1次合并最终形成哈夫曼树。
     * 3、经过n-1次合并产生n-1个新结点，且n-1个新结点都是具有两个孩子的分支结点。
     * 所以哈夫曼树中共有2n-1个结点，且其所有的分支结点的度均不为1.
     * 在哈夫曼树每个节点的左分支上标0，右分支标1 ，把从根到每个叶子的路径上的标号连接起来作为该叶子代表的字符的编码
     *
     * @param n
     * @return
     */
    public HTNode[] createHuffmanTree(int n) {
        if(n <= 1) return new HTNode[0];
        int m = (n << 1) - 1;
        HTNode[] nodes = new HTNode[m + 1];//0号不用，node[m]表示根节点
        // 初始化2n-1个元素
        for (int i = 1; i <= m; i++) {
            nodes[i] = new HTNode();
            nodes[i].lch = 0;
            nodes[i].rch = 0;
            nodes[i].parent = 0;
        }
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= n; i++) {
            // 输入前n个元素的权值
            nodes[i].weight = scanner.nextInt();
        }

        // 初始化结束，下面开始建立哈夫曼树
        for (int i = n + 1; i <= m; i++) {
            int[] s = selectTowSmall(nodes, i-1);
            int s1 = s[0];
            int s2 = s[1];
            nodes[s1].parent = i;
            nodes[s2].parent = i;
            // 新节点
            nodes[i].weight = nodes[s1].weight + nodes[s2].weight;
            nodes[i].lch = s1;
            nodes[i].rch = s2;
        }
        return nodes;
    }

    private int[] selectTowSmall(HTNode[] nodes, int len) {
        int[] arr = new int[2];
        int s1 = 0;
        int minWeight = 0x3f3f3f3f;//先赋予最大值;
        for (int i = 1; i <= len; i++) {
            if(nodes[i].parent == 0 && nodes[i].weight < minWeight) {
                minWeight = nodes[i].weight;
                s1 = i;
            }
        }
        arr[0] = s1;
        int temp = nodes[s1].weight;
        minWeight = nodes[s1].weight = 0x3f3f3f3f;
        int s2 = 0;
        for (int i = 1; i <= len; i++) {
            if(nodes[i].parent == 0 && nodes[i].weight < minWeight) {
                minWeight = nodes[i].weight;
                s2 = i;
            }
        }
        arr[1] = s2;
        nodes[s1].weight = temp;
        return arr;
    }

    /**
     * 哈夫曼编码
     * 要传输的字符集 D = {C,A,S,T,;}
     * 字符出现频率 w = {2,4,2,3,3}
     * 前缀编码: 在一个编码方案中，任一个编码都不是其他任何编码的前缀
     * 重点是将每一个字符出现的频率（即出现的次数）作为权值，构造哈夫曼树
     *                         14
     *                      0/   \1
     *                      6       8
     *                   0/  \1   0/  \1
     *                   3    3   4    4
     *                   T    ;   A  0/ \1
     *                   2   2
     *                   C   S
     * 字符出现频率为权，构造哈弗曼树如上，然之后左分支为0，右分支为1
     * 若编码是：1101000 其译文是CAT
     * <p>
     * 两个问题：
     * 1为什么哈夫曼编码能够保证是前缀编码
     * 因为没有一个叶节点是另一个叶节点的祖先，所以每个叶结点的编码就不可能是其它叶节点编码的前缀
     * 2 为什么哈夫曼编码能够保证字条编码总长度最短
     * 因为啥夫曼树的带权路径长度（之和）最短，故字符编码的总长最短
     *
     * @return
     */
    public String[] creatHuffmanCode(HTNode[] nodes, int n) {
        //从叶子到根逆向求每个字符的赫夫曼编码，存储在编码表HC中
        int index,c,p;
        // n个字符各自的编码列表
        String[] hc = new String[n+1];
        // n个节点生成的哈夫曼树高为n-1，因此每个字符的最长编码 <= n-1
        // 初始的n个字符，其权即为哈夫曼树中的叶节点
        for(int i = 1; i <= n; ++i) {
            char[] cd = new char[n-1];
            // index指向ch数组尾端
            index = n-2;
            c = i;
            p = nodes[i].parent;
            while(p != 0) {
                if(nodes[p].lch == c)
                    // 结点c是左孩子
                    cd[index]='0';
                else
                    cd[index]='1';
                c = p;
                p = nodes[p].parent;
                --index;
            }
            hc[i] = new String(cd);
        }
        return hc;
    }

    void show(HTNode[] nodes, String[] hc)
    {
        for(int i=1; i < hc.length;i++)
            System.out.println(nodes[i].weight + "编码为:" + hc[i]);
    }

    public static void main(String[] args) {
        int n = 5;
        HuffmanTree huffmanTree = new HuffmanTree();
        HTNode[] nodes = huffmanTree.createHuffmanTree(n);
        String[] strings = huffmanTree.creatHuffmanCode(nodes, n);
        huffmanTree.show(nodes, strings);
    }
}
