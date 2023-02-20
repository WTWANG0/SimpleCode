package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 * int[] w = {8,6,4,2,5};
 * int[] v = {6,4,7,8,6};
 * int c = 12;
 * 怎样挑选物品放入背包，能够使背包的价值最大化
 *
 * @Author shilei
 */
public class $01背包问题 {
    static int[] w = {8,6,4,2,5}; // 物品的重量
    static int[] v = {6,4,7,8,6}; // 物品的价值
    static int c = 12; // 背包的容量

    static int[] x = new int[w.length]; // 子集树的辅助数组
    static int[] bestx = new int[w.length]; // 记录最优物品的子集
    static int cw = 0; // 记录所选物品的总重量
    static int cv = 0; // 记录所选物品的总价值
    static int bestv = 0; // 记录物品的最优价值
    static int r = 0; // 记录剩余物品的总价值

    public static void main(String[] args) {
        for (int i = 0; i < v.length; i++) {
            r += v[i];
        }
        func(w, v, 0, x);
        System.out.println(bestv);
        System.out.println(Arrays.toString(bestx));
    }

    private static void func(int[] w, int[] v, int i, int[] x) {
        if(i == w.length){
            // cw  <= c
            if(bestv < cv){
                bestv = cv;
                for (int j = 0; j < x.length; j++) {
                    bestx[j] = x[j];
                }
            }
        } else {
            r -= v[i];
            // 如果已经选择的物品的总重量cw加上即将要选择的物品i的重量<=c，才有必要选择第i个物品
            if(cw + w[i] <= c){ // 剪枝操作
                cw += w[i];
                cv += v[i];
                x[i] = 1;
                func(w, v, i+1, x);
                cw -= w[i];
                cv -= v[i];
            }
            if(cv + r > bestv){
                x[i] = 0;
                func(w, v, i+1, x);
            }
            // i节点继续向上回溯
            r += v[i];
        }
    }
}