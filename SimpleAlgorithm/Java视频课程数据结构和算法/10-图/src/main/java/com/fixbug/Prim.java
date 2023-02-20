package com.fixbug;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 描述: 最小生成树 - 普里姆算法代码实现 - 加点法
 *
 * @Author shilei
 */
public class Prim {

    /**
     * 邻接表节点的类型
     */
    static class Entry{
        int id; // 表示顶点的id
        int cost; // 表示到达id顶点的权值

        public Entry(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }
    }

    /**
     * 邻接表数组元素类型
     */
    static class Vertic{
        char name;
        LinkedList<Entry> adjlist;

        public Vertic(char name) {
            this.name = name;
            this.adjlist = new LinkedList<>();
        }
    }

    public static void main(String[] args) {
        // 定义顶点信息和顶点的个数
        char[] vertics = {'A', 'B', 'C', 'D', 'E', 'F'};
        int number = vertics.length;

        // 手动输入边的信息，建立邻接表结构
        Scanner in = new Scanner(System.in);
        Vertic[] verticList = new Vertic[number];
        for (int i = 0; i < verticList.length; i++) {
            verticList[i] = new Vertic(vertics[i]);
        }

        // 输入边的信息
        System.out.print("输入边的个数:");
        int edgeNumber = in.nextInt();
        in.nextLine(); // 把读完整数，缓冲区剩余的回车读掉

        System.out.println("输入边的信息:"); // A B 6

        int minCost = Integer.MAX_VALUE;
        int minCostIndex = 0; // 记录权值最小的顶点的下标
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
            if(minCost > cost){
                minCost = cost;
                minCostIndex = start;
            }

            // 把信息添加到邻接表当中 start - end cost  end - start cost
            verticList[start].adjlist.add(new Entry(end, cost));
            verticList[end].adjlist.add(new Entry(start, cost));
        }

        // 上面创建好了邻接表结构，下面开始普里姆算法代码实现
        final int INF = Integer.MAX_VALUE;
        Entry[] cost = new Entry[number];
        boolean[] use = new boolean[number];

        // u放选择好的顶点   v待选择的顶点
        use[minCostIndex] = true; // 表示把minCostIndex选入u集合
        for (int i = 0; i < cost.length; i++) {
            cost[i] = new Entry(minCostIndex, INF);
        }

        LinkedList<Entry> list = verticList[minCostIndex].adjlist;
        for (int i = 0; i < list.size(); i++) {
            cost[list.get(i).id].cost = list.get(i).cost;
        }

        // 开始继续搜索后面的顶点
        for (int i = 1; i < number; i++) {
            // 找到权值最小的顶点
            int k = -1;
            int min = INF;

            for (int j = 0; j < cost.length; j++) {
                if(!use[j] && cost[j].cost < min){
                    min = cost[j].cost;
                    k = j;
                }
            }

            // 选择了第k各顶点
            use[k] = true;
            // 打印最小生成树的边
            System.out.println(verticList[cost[k].id].name + " -> " +
                    verticList[k].name);

            // 把第k各节点新添加到u集合当中，更新剩下的顶点的权值信息了
            for (int j = 0; j < cost.length; j++) {
                if(!use[j]){
                    // 计算j顶点和k顶点的权值，是否比j之前记录的到某个顶点的权值更小，然后更新顶点权值
                    list = verticList[k].adjlist;
                    for (Entry entry : list) {
                        if(entry.id == j){
                            if(entry.cost < cost[j].cost){
                                cost[j].cost = entry.cost;
                                cost[j].id = k;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
