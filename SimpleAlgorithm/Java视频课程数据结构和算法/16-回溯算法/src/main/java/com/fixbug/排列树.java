package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 排列树 {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4};
        func(arr, 0);
    }

    private static void func(int[] arr, int i) {
        if(i == arr.length){
            // 访问到了排列树的一个叶子节点了
            System.out.println(Arrays.toString(arr));
        } else {
            for(int j=i; j < arr.length; ++j){
                // i是当前位置， j是从当前位置开始到后面的所有的元素
                swap(arr, i, j);
                func(arr, i+1);
                swap(arr, i, j);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
