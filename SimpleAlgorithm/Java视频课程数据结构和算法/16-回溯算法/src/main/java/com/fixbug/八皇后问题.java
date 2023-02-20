package com.fixbug;

import java.util.Arrays;

/**
 * 描述: 怎么排列皇后，能保证它们不会互相吃掉! 问共有多少种满足条件的排列方式？
 *
 * @Author shilei
 */
public class 八皇后问题 {
    static int count = 0;
    public static void main(String[] args) {
        // 数组的下标代表行， 对应的元素的值代表列
        int[] arr = {1,2,3,4,5,6,7,8};
        func(arr, 0);
        System.out.println(count);
    }

    private static void func(int[] arr, int i) {
        if(i == arr.length){
            count++;
            System.out.println(Arrays.toString(arr));
        } else {
            for(int j=i; j < arr.length; ++j){
                swap(arr, i, j); // 交换i位置和j位置的元素的值
                // 把明显不满足题目要求的分支节点剪掉，提高排列树的搜索效率
                if(queue(arr, i)){  // 剪枝操作
                    func(arr, i+1);  // 递归生成下一层节点
                }
                swap(arr, i, j); // 把i和j位置的元素还原，准备生成下一层节点的后一个节点
            }
        }
    }

    /**
     * 根据题目要求，判断皇后是否在同一行，同一列，同一斜线上
     * @param arr
     * @param i
     * @return
     */
    private static boolean queue(int[] arr, int i) {
        // 判断第i行的皇后和0 - i-1行的皇后，不在同一列上，也不再统一斜线上
        for (int j = 0; j < i; j++) {
            // 前面判断在同一列上，后面判断在统一斜线上
            if(arr[j] == arr[i] || (Math.abs(i-j) == Math.abs(arr[i] - arr[j]))){
                return false;
            }
        }
        return true;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
