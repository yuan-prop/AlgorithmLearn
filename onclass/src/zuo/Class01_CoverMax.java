package zuo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 小根堆应用：
 * 一组线段中重合的最多次数
 */
public class Class01_CoverMax {

    // 方法1：笨方法
    public static int maxCover1(int[][] lines){
        // 所有线段中求最小起点和最大终点
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < lines.length; i++){
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for(float f = min + 0.5f; f < max; f++){
            // 所有线段中包含当前点的总次数
            int count = 0;
            for(int i = 0; i < lines.length; i++){
                if(lines[i][0] < f && f < lines[i][1]){
                    count++;
                }
            }
            cover = Math.max(cover, count);
        }
        return cover;
    }

    /**
     * 方法2：利用小根堆
     * @param m
     */
    public static int maxCover2(int[][] m){
        Line[] lines = new Line[m.length];
        for(int i=0; i < m.length; i++){
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        // 线段按首端从小到大排序
        Arrays.sort(lines, new StartComparator());
        // 小根堆 放每一条线段的结尾数值
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for(int i = 0; i < lines.length; i++) {
            // 1 如果小根堆中堆顶的数小于当前线段的首端，则堆顶的数从堆顶弹出
            while (!heap.isEmpty() && lines[i].start >= heap.peek()) {
                heap.poll();
            }
            // 2 将线段尾节点加入小根堆
            heap.add(lines[i].end);
            // 3 查看当前堆大小
            max = Math.max(max, heap.size());
        }
        return max;
    }

    /**
     * 定义线段的数据结构
     */
    public static class Line{
        public int start;
        public int end;

        public Line(int s, int e){
            this.start = s;
            this.end = e;
        }
    }

    public static class StartComparator implements Comparator<Line>{

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static void main(String[] args) {
        int[][] lines = {{1,3},{2,6},{4,9},{5,8}};
        int a = maxCover1(lines);
        int b = maxCover2(lines);

        System.out.println(a + " "+b );
    }

}
