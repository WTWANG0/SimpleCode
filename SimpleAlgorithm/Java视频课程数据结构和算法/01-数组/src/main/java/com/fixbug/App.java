package com.fixbug;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * Java数据结构 - 线性表的顺序存储方式 - 数组
 *
 */
public class App 
{
    @Test
    public void test02(){
        Random rd = new Random();
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt(100);
        }
        System.out.println(Arrays.toString(arr));

        // 线性查找
        int idx = 0;
        idx = find(arr, 51);
        System.out.println("idx:" + idx);

        // arr数组进行排序
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // 因为arr已经是有序的了，所以采用二分搜索算法，进行元素查找
        idx = bianrySearch(arr, 51);
        System.out.println("idx:" + idx);
    }

    /**
     * 二分搜索代码实现
     * @param arr
     * @param val
     * @return
     */
    private int bianrySearch(int[] arr, int val) {
        int f = 0;
        int l = arr.length-1;
        int m = 0;

        while(f <= l){  // f > l
            m = (f+l)/2;
            if(arr[m] == val){
                return m;
            } else if(arr[m] < val){
                f = m+1;
            } else{
                l = m-1;
            }
        }

        return -1;
    }

    /**
     * 线性搜索数组元素
     * @param arr
     * @param val
     * @return
     */
    private int find(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == val) {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void test01()
    {
        // 以系统时间作为随机数种子
        Random rd = new Random();
        // 定义一个有20个元素的数组
        int[] arr = new int[20];
        // 记录arr数组元素的个数
        int idx = 0;

        // 给arr数组的末尾添加10个元素
        for (int i = 0; i < 10; i++) {
            arr[idx++] = rd.nextInt(100);
        }

        // 打印数组元素
        for (int i = 0; i < idx; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        // 给arr第5个位置插入一个50
        for(int i=idx; i>=5; --i){
            arr[i] = arr[i-1];
        }
        arr[4] = 50;
        idx++;

        // 打印数组元素
        for (int i = 0; i < idx; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        // 删除arr数组末尾元素
        idx--;
        // 打印数组元素
        for (int i = 0; i < idx; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        // 删除第3个元素  arr[2]
        for(int i=2; i<idx-1; ++i){
            arr[i] = arr[i+1];
        }
        idx--;

        // 打印数组元素
        for (int i = 0; i < idx; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}