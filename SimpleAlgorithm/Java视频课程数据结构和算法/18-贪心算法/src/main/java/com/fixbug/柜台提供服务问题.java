package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 柜台提供服务问题 {

    static class Counter implements Comparable<Counter>{
        int id; // 柜台的id
        int time; // 柜台提供服务的时间

        public Counter(int id, int time) {
            this.id = id;
            this.time = time;
        }

        @Override
        public int compareTo(Counter o) {
            return ((Integer)time).compareTo(o.time);
        }
    }

    public static void main(String[] args) {
        int[] arr = {3,2,4}; // 每一个柜台提供服务的时间
        int m = 3;  // 柜台的数量
        int n = 15; // 办理业务的人数

        Counter[] counters = new Counter[m];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = new Counter(i, arr[i]);
        }

        Arrays.sort(counters); // 按照柜台提供服务的时间，进行升序排序

        int mintime = 0; // 记录所有柜台提供服务的最少时间
        int[] x = new int[m]; // 辅助数组，用来记录每一个柜台安排的人数
        // 优先给处理时间短的柜台安排用户
        for (int i = 0; i < n; i++) {
            // 先计算把i号用户放入时间最短的柜台，花费的总时间
            int time = (x[0] + 1) * counters[0].time;
            int j = 1;
            for (; j < m; j++) {
                // 计算i用户放在当前柜台j花费的时间
                int t = (x[j] + 1) * counters[j].time;
                // 直接放入第j号柜台
                if(t <= time){
                    // 第j号柜台的人数++
                    x[j]++;
                    // 还需要更新一下，整个柜台现在花费的最少时间
                    if(x[j] * counters[j].time > mintime){
                        mintime = x[j] * counters[j].time;
                    }
                    break;
                }
            }

            if(j == m){
                // 表示第i号用户放入0号柜台所花费的时间最少
                x[0]++;
                // 还需要更新一下，整个柜台现在花费的最少时间
                mintime = x[0] * counters[0].time;
            }
        }

        for (int i = 0; i < counters.length; i++) {
            System.out.println(arr[counters[i].id] + " " + x[i]);
        }
        System.out.println(mintime);
    }
}
