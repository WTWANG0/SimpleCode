package T11_;

import java.util.Arrays;

/**
 * 问题描述：
 * 有两个升序数组，长度分别是m和n，求两个升序数组所有元素的中位数，要求算法时间复杂度是O(log2n)
 */
public class MiddleNumber {
    public static void main(String[] args) {
        test01();
    }

    static void test01() {
        int[] arr = new int[6];
        int[] brr = new int[4];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }

        for (int i = 0; i < brr.length; i++) {
            brr[i] = (int) (Math.random() * 100);
        }

        Arrays.sort(arr);
        Arrays.sort(brr);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(brr));

        //新数组
        int[] crr = new int[arr.length + brr.length];
        System.arraycopy(arr, 0, crr, 0, arr.length);
        System.arraycopy(brr, 0, crr, arr.length, brr.length);
        Arrays.sort(crr);
        System.out.println(Arrays.toString(crr));

        double middlenumber = findMiddleNumber(arr, arr.length, brr, brr.length);
        System.out.println(middlenumber);
    }


    /**
     * 求两个数组的中位数
     */
    private static double findMiddleNumber(int[] arr, int length1, int[] brr, int length2) {
        if (length1 > length2) { //短的做驱动，长的做被驱动
            return findMiddleNumber(brr, length2, arr, length1);
        }

        int i = 0;
        int j = 0;

        int begin = 0;
        int end = length1;

        int k = (length1 + length2 + 1) >> 1; //mid


        while (begin < end) { //短表
            i = (begin + end) >> 1; //arr的中位ndex

            j = k - i;  // 此处保证arr的前i个元素和brr的前j个元素的个数和是k
            // i > 0 && j < length2 区间限制
            if (i > 0 && j < length2 && arr[i - 1] > brr[j]) {
                end = i - 1;
            } else if (i < length1 && j > 0 && brr[j - 1] > arr[i]) {
                begin = i + 1;
            } else {
                break;
            }
        }

        double left = 0;
        double right = 0;
        if (i == 0) {
            left = brr[j - 1];
        } else if (j == 0) {
            left = arr[i - 1];
        } else {
            left = Math.max(arr[i - 1], brr[j - 1]);
        }

        if (i == length1) {
            right = brr[j];
        } else if (j == length2) {
            right = arr[i];
        } else {
            right = Math.max(arr[i], brr[j]);
        }

        if (((length1 + length2) & 0x01) == 1) {// 总长度是奇数
            return left; // k
        } else {
            return (left + right) / 2.0; // k k+1
        }

    }

    private static double findMiddleNumber2(int[] arr, int length1, int[] brr, int length2) {

        if (length1 > length2) {  // 说明brr数组更短，信息放入前两个参数中
            return findMiddleNumber2(brr, length2, arr, length1);
        }

        int i = 0;
        int j = 0;

        int begin = 0;
        int end = length1;

        int k = (length1 + length2 + 1) / 2; // 中间的那个元素的位置

        // 在arr和brr中找合适的i和j的位置
        while (begin <= end) {
            i = (begin + end) / 2;

            j = k - i;  // 此处保证arr的前i个元素和brr的前j个元素的个数和是k

            if (i > 0 && j < length2 && arr[i - 1] > brr[j]) {
                end = i - 1;
            } else if (i < length1 && j > 0 && brr[j - 1] > arr[i]) {
                begin = i + 1;
            } else {
                break;
            }
        }

        // i和j的位置已经找到了
        double left = 0;
        double right = 0;

        if (i == 0) { // arr太短，而且元素都很大，找不到合适的i位置，此时中位数都在brr上
            left = brr[j - 1];
        } else if (j == 0) { // brr太短，而且元素都很大，找不到合适的j位置，此时中位数都在arr上
            left = arr[i - 1];
        } else {
            left = Math.max(arr[i - 1], brr[j - 1]);
        }

        if (i == length1) { // arr太短，而且元素都很小，找不到合适的i位置，此时中位数都在brr上
            right = brr[j];
        } else if (j == length2) { // brr太短，而且元素都很小，找不到合适的j位置，此时中位数都在arr上
            right = arr[i];
        } else {
            right = Math.min(arr[i], brr[j]);
        }

        if (((length1 + length2) & 0x01) == 1) {// 总长度是奇数
            return left; // k
        } else {
            return (left + right) / 2.0; // k k+1
        }
    }


}
