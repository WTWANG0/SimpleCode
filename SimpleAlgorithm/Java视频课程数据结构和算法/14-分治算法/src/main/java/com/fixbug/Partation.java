package com.fixbug;

import java.util.Arrays;

/**
 * 描述: 使用快排分割函数求解top K问题
 * top K问题：
 * 1. 有n个数，求出n个数最小的前10个数字
 * 2. 有n个数，求出n个数第10小的数字
 *
 * @Author shilei
 */
public class Partation {

    /**
     * 快排的分割函数
     * @param arr
     * @param i
     * @param j
     * @return 返回基准数的下标
     */
    public static int partation(int[] arr, int i, int j){
        int val = arr[i]; // 基准数
        int l = i;
        int r = j;
        while (l < r){
            while (l < r && arr[r] > val){
                r--;
            }
            if(l < r){
                arr[l++] = arr[r];
            }
            while (l < r && arr[l] < val){
                l++;
            }
            if(l < r){
                arr[r--] = arr[l];
            }
        }
        arr[l] = val; // 把基准数写入最终的位置
        return l;
    }

    /**
     * 通过快排的分割函数，求解top k问题
     * @param arr
     * @param i
     * @param j
     * @param k
     * @return
     */
    private static int topKMin(int[] arr, int i, int j, int k) {
        int index = partation(arr, i, j);
        if(index == k){
            return index;
        } else if(index > k) {
            return topKMin(arr, i, index-1, k);
        } else {
            return topKMin(arr, index+1, j, k);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 100);
        }

        System.out.println(Arrays.toString(arr));
        int index = topKMin(arr, 0, arr.length-1, 4-1);
        System.out.println(Arrays.toString(arr));
        System.out.println(arr[index]);
        for (int i = 0; i <= index; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}