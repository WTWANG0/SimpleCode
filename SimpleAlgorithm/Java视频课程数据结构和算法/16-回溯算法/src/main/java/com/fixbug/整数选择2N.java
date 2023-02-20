package com.fixbug;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 描述: 有2n个整数，从中选择n个，让选择的n个整数和剩下的n个整数的和的差最小
 *
 * @Author shilei
 */
public class 整数选择2N {
    static int[] arr = {12,4,5,18,21,23,17,15};
    static int[] x = new int[arr.length]; // 子集树的辅助数组
    static int[] bestx = new int[arr.length];
    static int cv = 0; // 表示选择的数字的和
    static int lv = 0; // 未选择的数字的和
    static int min = Integer.MAX_VALUE; // 记录和的差的最小值
    static int choice = 0; // 记录选择的元素的个数

    public static void main(String[] args) {
        for (int i = 0; i < arr.length; i++) {
            lv += arr[i];
        }
        func(arr, 0, x);
        System.out.println(min);
        System.out.println(Arrays.toString(bestx));
    }

    private static void func(int[] arr, int i, int[] x) {
        if(i == arr.length){
            if(choice != arr.length/2){
                return;
            }

            int ret = Math.abs(cv - lv);
            if(min > ret){
                min = ret;
                // 记录目前位置最优的子集
                for (int j = 0; j < x.length; j++) {
                    bestx[j] = x[j];
                }
            }
        } else {
            // 避免搜索无效的子集
            if(choice < arr.length/2){  // 表示子集树的剪枝操作
                // 往i节点的左孩子   表示选择i节点
                choice++;
                cv += arr[i];
                lv -= arr[i];
                x[i] = 1;
                func(arr, i+1, x);
                cv -= arr[i];
                lv += arr[i];
                choice--;
            }

            // 往i节点的右孩子    表示不选择i节点
            x[i] = 0;
            func(arr, i+1, x);
        }
    }
}
