package T12_;

import java.util.Scanner;

/**
 * 数字三角形
 * 问题描述: 一个数字三角宝塔，规定从最顶层走到最底层，每一步可沿垂直向下、右斜线向下，求解
 * 从最顶层走到最底层的一条路径，使得沿着该路径所经过的数字的总和最大，输出最大值
 * （1）样例输入：
 * 第一行是塔层数N(1 <= N <= 100)。
 * 第二行起，从一个数字按树塔图形依次递增，共有N层。
 * 5
 * 13
 * 11  8
 * 12  7  26
 * 6  14  15  8
 * 12  7  13  24  11
 * （2）样例输出：86
 */
public class C03_triangle {
    public static void main(String[] args) {
        test00();
    }

    static void test() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int[][] arr = new int[n][n];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                arr[i][j] = in.nextInt();
            }
        }
        test1(5, arr);
    }

    static void test00() {

        int n = 5;

        int[][] arr = new int[n][n];
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                arr[i][0] = 13;
            }

            if (i == 1) {
                arr[i][0] = 11;
                arr[i][1] = 8;
            }

            if (i == 2) {
                arr[i][0] = 12;
                arr[i][1] = 7;
                arr[i][2] = 26;
            }

            if (i == 3) {
                arr[i][0] = 6;
                arr[i][1] = 14;
                arr[i][2] = 15;
                arr[i][3] = 8;
            }


            if (i == 4) {
                arr[i][0] = 12;
                arr[i][1] = 7;
                arr[i][2] = 13;
                arr[i][3] = 24;
                arr[i][4] = 11;
            }
        }
        test1(5, arr);
        test2(5, arr);
    }

    /**
     * way2
     * 上层结果依赖于下层，所以第一步处理最后一层
     */
    static void test2(int n, int[][] arr) {
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) { //最后一层不变
            dp[n - 1][i] = arr[n - 1][i];
        }

        //从倒数第二层开始
        for (int i = n - 2; i >= 0; --i) {
            for (int j = 0; j <= i; j++) { //每一个dp[i][j]取决于下方和右下方的两个数值的大值
                dp[i][j] = Math.max(dp[i + 1][j], dp[i + 1][j + 1])
                        + arr[i][j];
            }
        }
        System.out.println("max:" + dp[0][0]);
    }


    //---------------------------------------

    /**
     * way1
     */
    static void test1(int n, int[][] arr) {
        int[][] dp = new int[n][n];
        int way1 = findMaxPath(arr, 0, 0, n, dp);
        System.out.println("way1:" + way1);

        System.out.println("---------------------------------------------------------");
    }

    private static int findMaxPath(int[][] arr, int i, int j, int n, int[][] dp) {
        if (i == n - 1) {
            dp[i][j] = arr[i][j]; //最后一层直接返回当前值
            return dp[i][j];
        } else {
            if (dp[i][j] > 0) {
                return dp[i][j];
            }

            dp[i][j] = Math.max(findMaxPath(arr, i + 1, j, n, dp), findMaxPath(arr, i + 1, j + 1, n, dp))
                    + arr[i][j];
            return dp[i][j];
        }

    }
}
