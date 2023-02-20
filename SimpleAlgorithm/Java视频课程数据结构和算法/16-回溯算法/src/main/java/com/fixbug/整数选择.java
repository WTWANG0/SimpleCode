package com.fixbug;

import java.util.Arrays;

/**
 * 问题描述:
 * 给定一个整数数组，在里面挑选一部分数字，让挑选的数字的和，和剩下的数字的和的差最小
 * int[] arr = {12,4,5,18,21,23,17,15}
 *
 * @Author shilei
 */
public class 整数选择 {
    static int[] arr = {12,4,5,18,21,23,17,15};
    static int[] x = new int[arr.length]; // 辅助数组
    static int[] bestx = new int[arr.length]; // 记录最好的子集
    static int cv = 0; // 已经挑选的数字的和
    static int sum = 0; // 记录所有数字的和     表示未被选择的数字的和
    static int min = Integer.MAX_VALUE; // 记录和的差的最小值

    public static void main(String[] args) {
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        func(arr, 0, x);
        System.out.println(Arrays.toString(bestx));
        for (int i = 0; i < bestx.length; i++) {
            if(bestx[i] == 1){
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
        System.out.println("min:" + min);
    }

    private static void func(int[] arr, int i, int[] x) {
        if(i == arr.length){ // 从根节点到达了某一个叶子节点上
            // int ret = Math.abs(cv - (sum-cv));
            int ret = Math.abs(cv - sum);
            if(min > ret){
                min = ret;
                // 记录当前差值最小的，已经选择的子集
                for (int j = 0; j < x.length; j++) {
                    bestx[j] = x[j];
                }
            }
        } else {
            cv += arr[i];  // 表示选择i节点
            sum -= arr[i];
            x[i] = 1;
            func(arr, i+1, x);
            cv -= arr[i];  // 表示不选择i节点
            sum += arr[i];
            x[i] = 0;
            func(arr, i+1, x);
        }
    }

    /*private static void func(int[] arr, int i, int[] x) {
        if(i == arr.length){ // 从根节点到达了某一个叶子节点上
            int s = 0;
            for (int j = 0; j < arr.length; j++) {
                if(x[j] == 1){
                    s += arr[j];
                }
            }

            int ret = Math.abs(s - (sum-s));
            if(min > ret){
                min = ret;
                // 记录当前差值最小的，已经选择的子集
                for (int j = 0; j < x.length; j++) {
                    bestx[j] = x[j];
                }
            }
        } else {
            x[i] = 1;
            func(arr, i+1, x);

            x[i] = 0;
            func(arr, i+1, x);
        }
    }*/
}
