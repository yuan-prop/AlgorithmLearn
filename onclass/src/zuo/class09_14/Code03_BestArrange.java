package zuo.class09_14;

import java.util.Arrays;
import java.util.Comparator;

public class Code03_BestArrange {

    /**
     * 贪心算法：
     * 如果宣讲室同一时间只能安排一个会议，求一组会议中（有开始时间和结束时间）最多可以安排的个数
     *
     * 核心：会议结束时间早的优先安排，才能安排最多会议（其他的比如会议起始时间早、会议时长短的优先均可以举出反例）
     *
     * 注意：贪心算法可以不用证明（一般用对数器验证结果是否正确）
     */


    public static class Program{
        // 会议起始时间
        public int start;
        // 会议结束时间
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 会议排序的比较器
     */
    public static class ProgramComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    /**
     * 方法一：暴力递归，尝试所有情况中取最大值
     */
    public static int bestArrange1(Program[] programs){
        if(programs == null || programs.length == 0){
            return 0;
        }
        return process(programs, 0,0);
    }

    //还剩下的会议都放到programs里
    // done表示之前已经安排了多少会议的数量
    //timeline 目前来到的时间点

    // 目前来到timeline时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
    // 返回能安排的最多会议数量
    private static int process(Program[] programs,int done, int timeline){
        if(programs.length == 0){
            return done;
        }
        // 还有会议可以选择
        int max = done;
        // 当前安排的是什么会，每一个都枚举
        for(int i = 0; i<programs.length; i++){
            if(programs[i].start >= timeline){
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done+1, programs[i].end));
            }
        }
        return max;
    }

    private static Program[] copyButExcept(Program[] programs, int i) {
        if(programs == null){
            return null;
        }
        Program[] ans = new Program[programs.length -1];
        int index = 0;
        for(int k = 0; k < programs.length; k++){
            if(k != i){
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    /**
     * 方法二。贪心法求解过程
     * @param programs
     * @return
     */
    public static int bestArrange2(Program[] programs){
        Arrays.sort(programs, new ProgramComparator());
        // 记录当前时间点
        int timeline = 0;
        int result = 0;
        // 遍历每一个会议，结束时间早的会议先遍历
        for(Program p : programs){
            // 会议的开始时间晚于当前时间的才能安排
            if(p.start >= timeline){
                result++;
                timeline = p.end;
            }
        }
        return result;
    }

    /**
     * 测试用例，随机生成数组（对数器）
     * @param programSize 会议个数
     * @param timeMax 最大结束时间
     * @return
     */
    public static Program[] generatePrograms(int programSize, int timeMax){
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for(int i = 0; i < ans.length; i++){
            // 每个会议的起止时间是随机的
            int r1 = (int) Math.random() * (timeMax +1);
            int r2 = (int) Math.random() * (timeMax +1);
            if(r1 == r2){
                ans[i] = new Program(r1, r1 + 1);
            }else{
                ans[i] = new Program(Math.max(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int testTimes = 1000000;// 测试次数
        for(int n = 0; n < testTimes; n++){
            Program[] programs = generatePrograms(programSize, timeMax);
            if(bestArrange1(programs) != bestArrange2(programs)){
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
