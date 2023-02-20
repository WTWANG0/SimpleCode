package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class BinarySearchCase {
    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 100);
        }

        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        int index = binarySearch(arr, 0, arr.length-1, 25);
    }

    /**
     * 通过分治算法实现二分搜索代码
     * @param arr
     * @param i
     * @param j
     * @param val
     * @return 返回arr数组重val元素的下标，否则否会-1
     */
    private static int binarySearch(int[] arr, int i, int j, int val) {
        if(i > j){
            return -1;
        }

        int mid = (i+j) / 2;
        if(arr[mid] == val){
            return mid;
        } else if(arr[mid] > val){
            return binarySearch(arr, i, mid-1, val);
        } else {
            return binarySearch(arr, mid+1, j, val);
        }
    }
}
