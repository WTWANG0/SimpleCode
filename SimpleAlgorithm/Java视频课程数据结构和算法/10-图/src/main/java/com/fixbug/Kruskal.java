package com.fixbug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * 描述: 最小生成树 - 克鲁斯卡尔算法代码实现
 *
 * @Author shilei
 */
public class Kruskal {

    /**
     * 定义边的类型
     */
    static class Edge{
        int start; // 表示边的起点
        int end; // 表示边的终点
        int cost; // 表示边的权值

        public Edge(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start=" + start +
                    ", end=" + end +
                    ", cost=" + cost +
                    '}';
        }
    }

    public static void main(String[] args) {
        // 定义顶点信息和顶点的个数
        char[] vertics = {'A', 'B', 'C', 'D', 'E', 'F'};
        int number = vertics.length;

        // 定义边集数组，存储所有边的信息
        ArrayList<Edge> edgeList = new ArrayList<>(number);

        // 手动输入边的信息，建立边集数组
        Scanner in = new Scanner(System.in);
        System.out.print("输入边的个数:");
        int edgeNumber = in.nextInt();
        in.nextLine(); // 把读完整数，缓冲区剩余的回车读掉

        System.out.println("输入边的信息:");

        for (int i = 0; i < edgeNumber; i++) {
            String line = in.nextLine();
            String[] infos = line.trim().split(" ");

            // 获取起点和终点的编号
            int start = 0, end = 0;
            for (int j = 0; j < vertics.length; j++) {
                if(vertics[j] == infos[0].charAt(0)){
                    start = j;
                } else if(vertics[j] == infos[1].charAt(0)){
                    end = j;
                }
            }

            // 获取输入的权值
            int cost = Integer.parseInt(infos[2]);

            // 把边的信息添加到边集数组当中
            edgeList.add(new Edge(start, end, cost));
        }

        // 对边集数组按边的权值，小到大进行排序
        Collections.sort(edgeList, (a, b)->{
            return Integer.valueOf(a.cost).compareTo(b.cost);
        });

        /*edgeList.forEach((a)->{
            System.out.println(a.start + " " + a.end + " " + a.cost);
        });*/
        // 开始根据Kruskal算法挑选最小生成树的边
        // record用来记录某个顶点最终能跑到的顶点的编号
        int[] record = new int[number];
        Arrays.fill(record, -1);
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            int m = findPath(record, edge.start);
            int n = findPath(record, edge.end);
            if(m != n){ // m == n 表示连接edge.start和edge.end，会产生回路
                // 表示edge.start和edge.end走不到同一个顶点，所以连接起来没有回路
                record[m] = edge.end;
                System.out.println(vertics[edge.start] + " -> " +
                        vertics[edge.end] + " cost: " + edge.cost);
            }
        }
    }

    /**
     * 找id编号的顶点最终能走到哪个顶点去，返回该顶点的编号
     * @param record
     * @param id
     * @return
     */
    private static int findPath(int[] record, int id) {
        while(record[id] != -1){
            id = record[id];
        }
        return id;
    }
}
