package T01_数组;


import org.junit.Test;

import java.util.Random;

/**
 * 线性表的存储方式-数组
 * 二分查找：
 * 对于有序数组的二分查找，
 *      1：查找区间范围：star <= end
 *      2: 与中间位置比较，
 *          arr[m] > val,找前半部分： end = mid -1 ，
 *          arr[m] < val,否则找后半部分： start = end + 1
 *          arr[m] = mid 返回m
 */
public class Demo {

    /**
     * 非递归
     * 二分查找有序数组一个值的位置
     */
    public int binarySearch(int[] arr, int val) {
        int start = 0;//开始位置
        int end = arr.length - 1;//结束位置
        int mid = 0;//中间位置

        while (start <= end) {
            mid = (start + end) / 2; //todo:中间位置可以优化
            if (arr[mid] > val) { //中间数比当前数大，从前半部分查找
                end = mid - 1;
            } else if (arr[mid] < val) { //反之从后半部分查找
                start = mid + 1;
            } else {
                return mid;
            }
        }

        return -1; //找不到这个数
    }

    /**
     * 递归查找
     * 二分查找有序数组一个值的位置
     */
    public int binarySearch2(int[] arr, int val) {
        return doSearch(arr, val, 0, arr.length - 1);
    }

    /**
     * 递归需要注意的是返回值由当前查找返回
     * */
    public int doSearch(int[] arr, int val, int start, int end) {
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;
        if (arr[mid] > val) {
            return doSearch(arr, val, start, mid - 1);
        } else if (arr[mid] < val) {
            return doSearch(arr, val, mid + 1, end);
        } else {
            return mid;
        }
    }


    @Test
    public void test01() {
        Random rd = new Random();
        int[] arr = new int[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = 20 + i;
        }

        int i1 = binarySearch(arr, 25);

        int i2 = binarySearch2(arr, 25);

        if (i1 == i2) {
            System.out.println("yes" + i1);
        } else {
            System.out.println("no" + i1);
        }
    }




}
