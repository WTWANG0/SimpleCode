package com.fixbug;

import java.util.Scanner;

/**
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
 *
 * @Author shilei
 */
public class 数字三角形 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int[][] arr = new int[n][n];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                arr[i][j] = in.nextInt();
            }
        }

        // 定义dp数组，记录子问题的最优解
        int[][] dp = new int[n][n];
        // 用递归实现
        //int max = findMaxPath(arr, 0, 0, n, dp);

        // 非递归代码实现
        for (int j = 0; j < n; j++) {
            dp[n-1][j] = arr[n-1][j];
        }
        for(int i=n-2; i>=0; --i){
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.max(dp[i+1][j], dp[i+1][j+1]) + arr[i][j];
            }
        }

        System.out.println("max:" + dp[0][0]);
    }

    /**
     * 递归求解三角形数组最大值
     * @param arr
     * @param i
     * @param j
     * @param n
     * @param dp
     * @return
     */
    private static int findMaxPath(int[][] arr, int i, int j, int n, int[][] dp) {
        if(i == n-1){
            dp[i][j] = arr[i][j];
            return dp[i][j];
        } else {
            if(dp[i][j] > 0){
                return dp[i][j];
            }

            dp[i][j] = Math.max(findMaxPath(arr, i+1, j, n, dp),
                    findMaxPath(arr, i+1, j+1, n, dp)) + arr[i][j];
            return dp[i][j];
        }
    }
}
