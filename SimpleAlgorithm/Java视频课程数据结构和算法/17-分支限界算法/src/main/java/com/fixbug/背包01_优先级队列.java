package com.fixbug;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 背包01_优先级队列 {

    static int[] w = {16, 14, 15}; // 物品的重量
    static int[] v = {45, 25, 25}; // 物品的价值
    static int c = 30; // 背包的容量
    static int n = w.length; // 表示物品的数量
    static int cw = 0; // 已选择物品的总重量
    static int cv = 0; // 已选择物品的总价值
    static int bestv = 0; // 记录当前背包容纳物品的最优价值
    static PriorityQueue<Node> que = new PriorityQueue<>((a,b)->{return -a.compareTo(b);}); // 大根堆

    static class PNode{
        PNode parent; // 记录父节点
        boolean isleft; // 是否选择当前物品

        public PNode(PNode parent, boolean isleft) {
            this.parent = parent;
            this.isleft = isleft;
        }
    }

    // 定义节点类型
    static class Node implements Comparable<Node>{
        int weight; // 节点对应的重量
        int value;  // 节点对应的价值
        int level;  // 节点对应的层数
        int upbound; // 记录当前节点的上界值

        // Node parent; // 记录父节点
        // boolean isleft; // 是否选择当前物品
        PNode liveNode;

        public Node(int weight, int value, int level, int upbound, PNode liveNode) {
            this.weight = weight;
            this.value = value;
            this.level = level;
            this.upbound = upbound;
            this.liveNode = liveNode;
        }

        @Override
        public int compareTo(Node o) {
            return ((Integer)upbound).compareTo(o.upbound);
        }
    }

    // 定义计算上界的函数-计算背包剩余容量的最大价值 第t个物品往后，计算能装入背包的最大价值
    public static int maxBound(int t){
        int left = c - cw; // 背包剩余的容量
        int bound = cv;
        while (t < n && w[t] <= left){
            left -= w[t];
            bound += v[t];
            t++;
        }
        if(t < n){
            bound = bound + (int)(((double)v[t])/w[t] * left);
        }
        return bound;
    }

    public static void main(String[] args) {
        int i = 0; // 层数
        int upbound = maxBound(i);
        PNode parent = null;
        while (i < n){
            int wt = cw + w[i];
            if(wt <= c){
                if(cv + v[i] > bestv){
                    bestv = cv + v[i];
                }
                // 添加活结点
                addLiveNode(cw+w[i], cv+v[i], upbound, i+1, true, parent);
            }

            // 处理右孩子
            upbound = maxBound(i+1);
            // 这里if(upbound >= bestv)就是分支限界算法中的“限界”操作
            if(upbound >= bestv) {// 才有必要去当前节点的右子树遍历，有可能存在更好的值
                addLiveNode(cw, cv, upbound, i+1, false, parent);
            }

            // 上面把当前节点处理完了，继续处理下一个优先级最大的节点
            Node no = que.poll();
            i = no.level;
            cw = no.weight;
            cv = no.value;
            upbound = no.upbound;
            parent = no.liveNode;
        }

        System.out.println(bestv);
        int[] bestx = new int[n];
        for (int j = n-1; j >= 0; j--) {
            bestx[j] = parent.isleft ? 1:0;
            parent = parent.parent;
        }
        System.out.println(Arrays.toString(bestx));
    }

    private static void addLiveNode(int w, int v, int upbound, int level, boolean isleft, PNode parent) {
        Node no = new Node(w, v, level, upbound, new PNode(parent, isleft));
        que.offer(no);
    }
}
