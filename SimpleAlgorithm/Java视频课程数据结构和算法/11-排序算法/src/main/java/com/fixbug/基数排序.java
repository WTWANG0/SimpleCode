package com.fixbug;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 描述:
 *
 * @Author shilei
 */
public class 基数排序 {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*100);
        }

        arr[3] = -128;  // -8  -2   -1
        arr[4] = -228;
        System.out.println(Arrays.toString(arr));
        redixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基数排序
     * @param arr
     */
    private static void redixSort(int[] arr) {
        // #1 从arr中获取长度最长的数字的长度
        int maxVal = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(maxVal < Math.abs(arr[i])){
                maxVal = Math.abs(arr[i]);
            }
        }
        // #1 length记录的就是最长的数字的长度
        int length = String.valueOf(maxVal).length();

        /**
         * 为了处理负数的基数排序，此处的ArrayList应该包含20个桶
         * 0 - 9 放负数的     -678    -6 -7 -8  + 10
         * 10 - 19 放正数的
         */
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>(20);
        for (int j = 0; j < 20; j++) {
            buckets.add(new ArrayList<>());
        }
        int mod = 10;
        int dev = 1;
        for (int i = 0; i < length; i++, mod*=10, dev*=10) {  // O(d) d代表位数

            // 按照第i位的数字大小，把所有的数字放入桶当中
            for (int j = 0; j < arr.length; j++) {  // O(n) n元素的个数
                int index = (arr[j] % mod)/dev + 10;
                buckets.get(index).add(arr[j]);
            }

            // 按顺序从桶中把元素全部拷贝到原始的序列里面
            int pos = 0;
            for(ArrayList<Integer> list : buckets){
                if(!list.isEmpty()){
                    for(Integer val : list){
                        arr[pos++] = val;
                    }
                    list.clear();
                }
            }
        }
    }
}