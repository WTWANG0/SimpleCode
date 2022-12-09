package T11_;

import java.util.Arrays;

/**
 * 快排分割求top K问题
 * 1:有N个数，求最小的前10个数
 * 2：求N个数中第10小的数
 */
public class Partation {
    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        System.out.println(Arrays.toString(arr));
        int index = topKin(arr, 0, arr.length - 1, 10 - 1);
        System.out.println(Arrays.toString(arr));
        System.out.println(arr[index]);
        for (int i = 0; i <= index; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }


    /**
     * 通过快排求出top K问题
     */
    static int topKin(int[] arr, int start, int end, int k) {
        int index = partation(arr, start, end);
        if (index > k) {
            return topKin(arr, start, index - 1, k);
        } else if (index < k) {
            return topKin(arr, index + 1, end, k);
        } else {
            return index;
        }
    }


    /**
     * 快排分割函数
     */
    private static int partation(int[] arr, int i, int j) {
        int val = arr[i];//随便找一个下标作为基准

        int l = i;
        int r = j;

        while (l < r) {
            //从后往前面找第一个小于val值的下标放在左边
            while (l < r && arr[r] > val) {
                r--;
            }

            if (l < r) {
                arr[l++] = arr[r];
            }

            //从前往后找第一个大于val值的下标放在右边
            while (l < r && arr[l] < val) {
                l++;
            }
            if (l < r) {
                arr[r--] = arr[l];
            }
        }
        //那么得到，左边都是小于val的值，右边都是大于val1的值
        arr[l] = val;

        return l;
    }


}
