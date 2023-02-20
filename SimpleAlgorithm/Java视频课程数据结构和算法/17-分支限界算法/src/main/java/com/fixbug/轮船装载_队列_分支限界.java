package com.fixbug;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 轮船装载_队列_分支限界 {

    // 集装箱的重量
    static int[] w = {12, 8, 15};
    // 轮船的装载量
    static int c = 2;
    static int n = w.length;
    static int cw = 0; // 已选择物品的重量
    static int bestw = 0; // 记录最优的装载量
    static int i = 0; // 表示从第0层，根节点开始选择物品
    static LinkedList<Node> que = new LinkedList<>();
    static Node bestnode; // 记录最优解的叶子节点
    static int r = 0; // 记录剩余的可选择物品的总重量

    // 节点类型定义
    static class Node{
        int weight; // 到当前节点所选择的物品的总重量
        int level;  // 当前节点所在的层数

        Node parent; // 记录当前节点的父节点
        boolean isleft; // 记录当前节点的选择情况  true：选择物品  false：未选择物品

        public Node(int weight, int level, Node parent, boolean isleft) {
            this.weight = weight;
            this.level = level;
            this.parent = parent;
            this.isleft = isleft;
        }
    }

    // 求节点上界的函数
    public static int maxBound(int t){
        int s = 0;
        for (int j = t+1; j < n; j++) {
            s += w[j];
        }
        return s;
    }

    public static void main(String[] args) {
        Node node = null; // 记录当前节点
        while (i < n){
            // 处理左孩子
            int wt = cw + w[i];
            if(wt <= c){
                if(wt > bestw)
                    bestw = wt;
                //que.offer(new Node(cw+w[i], i+1));
                //活结点入队列
                addLiveNode(cw+w[i], i+1, true, node);
            }

            // 总是认为右孩子可能存在最优解
            //que.offer(new Node(cw, i+1));
            //活结点入队列  注意需要判断=    限界操作
            r = maxBound(i); // 计算第i个节点的上界值
            if(cw + r >= bestw)
                addLiveNode(cw, i+1, false, node);

            // 当前节点处理完成，取队头节点，继续处理
            node = que.poll();
            cw = node.weight;
            i = node.level;
        }

        System.out.println(bestw);
        int[] bestx = new int[n];
        for (int j = n-1; j >= 0; j--) {
            bestx[j] = bestnode.isleft ? 1:0;
            bestnode = bestnode.parent;
        }
        System.out.println(Arrays.toString(bestx));
    }

    /**
     * 活结点入队列
     * @param w
     * @param level
     * @param isleft
     * @param parent
     */
    private static void addLiveNode(int w, int level, boolean isleft, Node parent) {
        Node no = new Node(w, level, parent, isleft);
        que.offer(no);

        // 遍历到叶子节点了，记录最优解
        if(level == n && w == bestw){
            bestnode = no;
        }
    }
}
