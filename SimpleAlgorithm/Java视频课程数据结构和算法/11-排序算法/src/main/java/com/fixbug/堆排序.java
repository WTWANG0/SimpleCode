package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 堆排序 {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*100);
        }

        System.out.println(Arrays.toString(arr));
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 堆排序
     * 1. 把乱序的二叉堆调整成大根堆
     * 2. 把堆顶元素和末尾元素进行交换，然后序列元素个数-1
     * 3. 此时堆顶元素改变了，进行堆顶元素的下沉调整，调整为大根堆，
     *     转到第2步
     * @param arr
     */
    private static void heapSort(int[] arr) {
        int end = arr.length-1;
        // 从第一个非叶子节点开始，到堆顶元素0，进行大根堆的调整
        for(int i=(end-1)/2; i >= 0; --i){
            siftDown(arr, i, arr.length);
        }

        for(int i=end; i >= 0; --i){
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            siftDown(arr, 0, i); // i表示剩下的参与下沉调整的元素个数
        }
    }

    /**
     * 把arr数组的第i个元素进行下沉调整
     * @param arr
     * @param i
     * @param length
     */
    private static void siftDown(int[] arr, int i, int length) {
        int val = arr[i];
        while(i < length/2){
            // 用child来记录节点i，值最大的那个孩子的下标
            int child = 2*i + 1;
            if(child+1 < length && arr[child+1] > arr[child]){
                child = child + 1;
            }

            if(arr[child] > val){
                arr[i] = arr[child];
                i = child;
            } else {
                break;
            }
        }
        arr[i] = val;
    }
}
