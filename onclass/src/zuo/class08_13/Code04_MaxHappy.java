package zuo.class08_13;

import java.util.List;


/**
 * 将一个公司成员的层级关系想象成多叉树，树根是老板，向下等级依次沿伸
 * 每个成员都有一个快乐值happy
 * 如果从整棵树选出一组成员且直接上下级的成员不能同时选进来，求选出的这组成员的happy值之和的最大值
 */
public class Code04_MaxHappy {

    public static class Employee{
        public int happy;
        public List<Employee> nexts;

        public Employee(int happy, List<Employee> nexts) {
            this.happy = happy;
            this.nexts = nexts;
        }
    }

    public static class Info{
        // 表示当前节点不被选
        public int no;
        // 表示当前节点被选
        public int yes;

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
        }
    }

    /**
     * 递归求解
     * @param x
     */
    public static Info process(Employee x){
        if(x == null){
            return new Info(0, 0);
        }
        // 若不选当前节点，当前节点贡献的happy值自然为0
        int no = 0;
        int yes = x.happy;

        for(Employee next : x.nexts){
            Info nextInfo = process(next);
            // 当前节点选，则下级节点不选
            yes += nextInfo.no;
            // 当前节点不选，则下级节点可以选，也可以不选
            no += Math.max(nextInfo.no, nextInfo.yes);
        }
        return new Info(no, yes);
    }

    public static int maxHappy(Employee head){
        Info headInfo = process(head);
        // 最终结果是最大老板（根节点）来或不来取最大
        return Math.max(headInfo.no, headInfo.yes);
    }

    public static void main(String[] args) {

    }


}
