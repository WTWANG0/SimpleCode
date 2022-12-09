package T11_;

import java.util.Arrays;

public class BinarySearchCase {
    public static void main(String[] args) {
        test02();
    }

    static void test02() {
        int i = 10;
        int j = 12;
        System.out.println((i + j) >> 1);
    }

    static void test01() {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 100);
        }

        System.out.println(Arrays.toString(arr));

        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        int index = binarySearch(arr, 0, arr.length - 1, 25);
    }

    static private int binarySearch(int[] arr, int i, int j, int val) {
        if (i > j) {
            return -1;
        }
        int mid = (i + j) / 2;
        if (arr[mid] > val) {
            binarySearch(arr, 0, mid - 1, val);
        } else if (arr[mid] < val) {
            binarySearch(arr, mid + 1, arr.length - 1, val);
        } else {
            return mid;
        }

        return -1;
    }
}

