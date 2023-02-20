package com.fixbug;

import java.util.Arrays;

/**
 * 描述: 迪杰斯特拉算法实现
 *
 * @Author shilei
 * @Date 2019/10/24
 */
public class Dijkstra {

    /**
     * 实现一个基于邻接矩阵的带权图最短路径
     * @param start
     * @param end
     */
    public void shortestPath(int start, int end){
        // 顶点的个数
        final int N = 6;
        // 表示节点之间无法到达
        final int INF = 100;
        // 定义邻接矩阵
        int[][] graph = new int[][]{
                {INF, 6, 3, INF, INF, INF},
                {6, INF, 2, 5, INF, INF},
                {3, 2, INF, 3, 4, INF},
                {INF, 5, 3, INF, 2, 3},
                {INF, INF, 4, 2, INF, 5},
                {INF, INF, INF, 3, 5, INF}
        };

        // 辅助数组，存储start到其它节点的权值
        int[] cost = new int[N];
        // 用来表示节点是否已经计算好最短路径，用来区分S集合和U集合
        boolean[] use = new boolean[N];
        // 用来记录节点的前驱节点
        int[] path = new int[N];

        // 初始化操作
        use[start] = true; // 把start节点放入S集合当中
        for (int i = 0; i < N; i++) {
            cost[i] = graph[start][i];
            // 更新path数组
            if(cost[i] < INF){
                path[i] = start;
            }
        }

        // 迪杰斯特拉算法的时间复杂度是O(n^2)
        // 开始迪杰斯特拉算法，计算start节点到U集合所有节点的最短路径
        for(int i=1; i < N; ++i){

            // 从U集合当中，找权值最小的节点
            int k = -1;
            int min = INF;
            for (int j = 0; j < N; j++) {
                if(!use[j] && cost[j] < min){
                    min = cost[j];
                    k = j;
                }
            }

            // U集合中剩下的顶点都是无法到达的
            if(k == -1){
                break;
            }

            // 把k节点从U集合放入S集合当中
            use[k] = true;

            // 更新权值数组 min是start节点到k节点的最短路径
            for (int j = 0; j < N; j++) {
                if(!use[j] && min + graph[k][j] < cost[j]){
                    cost[j] = min + graph[k][j];
                    // 这里表示从start走到k，从k再到j，其权值更小，路径更短
                    path[j] = k;
                }
            }
        }

        System.out.println("最短路径是:" + cost[end]);
        // 打印从start是怎么走到end的
        char[] datas = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
        System.out.print(datas[end] + " <- ");
        for(;;){
            end = path[end];
            System.out.print(datas[end] + " <- ");
            if(end == start){
                break;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Dijkstra dkstra = new Dijkstra();
        dkstra.shortestPath(0, 5);
    }
}
