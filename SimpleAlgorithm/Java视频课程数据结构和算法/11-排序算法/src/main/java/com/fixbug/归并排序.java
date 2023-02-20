package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 归并排序 {
    public static void main(String[] args) {
        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*100);
        }

        mergeSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 归并排序算法代码实现
     * @param arr
     * @param i
     * @param j
     */
    private static void mergeSort(int[] arr, int i, int j) {
        if(i >= j){ // i == j表示序列已经划分成一个元素了，不用再划分了，开始往上回溯
            return;
        }
        int mid = (i+j)/2;
        mergeSort(arr, i, mid);
        mergeSort(arr, mid+1, j);
        // 到此，序列划分完成，递归回溯开始进行有序序列的合并操作
        merge(arr, i, j, mid);
    }

    /**
     * 进行二路归并操作代码    外部排序 - 归并排序的思想
     * @param arr
     * @param low
     * @param high
     * @param mid
     */
    private static void merge(int[] arr, int low, int high, int mid) {
        // 开辟额外的空间，存储合并后的有序序列
        int[] array = new int[high-low+1];
        int i = low;
        int j = mid+1;
        int k = 0;

        // 把 [i,mid] 和 [mid,j]两个有序序列进行合并，结果放入辅助空间array中
        while (i <= mid && j <= high){
            if(arr[i] > arr[j]){
                array[k++] = arr[j++];
            } else {
                array[k++] = arr[i++];
            }
        }

        while (i <= mid){
            array[k++] = arr[i++];
        }

        while (j <= high){
            array[k++] = arr[j++];
        }

        // 把辅助空间存储的结果拷贝到原始序列的[low, high]中
        for(int pos = low; pos <= high; ++pos){
            arr[pos] = array[pos-low];
        }
    }
}
