package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 选硬币 {
    public static void main(String[] args) {
        Integer[] arr = {1,3,5}; // 硬币的面额
        int c = 11; // 组成的价值
        int num = func(arr, c);
        System.out.println("num:" + num);
    }

    /**
     * 分支算法 - 选择硬币
     * @param arr
     * @param c
     * @return
     */
    private static int func(Integer[] arr, int c) {
        // 先对硬币面额数组进行降序排序
        Arrays.sort(arr, (a, b)->{return -a.compareTo(b);}); // 5,3,1
        int idx = 0;
        int cnt = 0;
        while (c > 0){
            if(c >= arr[idx]){
                c -= arr[idx];
                cnt++;
            } else {
                idx++;
            }
        }
        return cnt;
    }
}
