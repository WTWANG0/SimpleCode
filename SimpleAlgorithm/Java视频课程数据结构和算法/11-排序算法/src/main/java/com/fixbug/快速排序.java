package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 快速排序 {
    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*100);
        }

        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 实现快速排序
     * @param arr
     * @param i
     * @param j
     */
    private static void quickSort(int[] arr, int i, int j) {
        if(i >= j){ // 序列中只剩下一个元素了，不用再处理了，递归结束
            return;
        }

        /*if(j - i >= 20){  // 快排优化，当划分的序列小于某一个长度的时候，采用插入排序算法
            insertSort(arr, i, j);
            return;
        }*/

        // 选取起始的元素i为基准数
        // #1
        int val = arr[i];
        // #2
        int l = i;
        int r = j;

        // #3 #4
        while(l < r){ // 当l == r的时候，就是调整完成了
            // 从后往前找第一个小于val的数字
            while(l < r && arr[r] > val){
                r--;
            }
            if(l < r){
                // 真正找到了一个小于val的数字
                arr[l++] = arr[r];
            }

            // 从前往后找第一个大于val的数字
            while(l < r && arr[l] < val){
                l++;
            }
            if(l < r){
                // 真正找到了一个大于val的数字
                arr[r--] = arr[l];
            }
        }

        // #5 l == r 放置基准数
        arr[l] = val;

        // 继续以上面同样的算法思想才处理基准数的左右序列
        quickSort(arr, i, l-1);
        quickSort(arr, l+1, j);
    }
}
