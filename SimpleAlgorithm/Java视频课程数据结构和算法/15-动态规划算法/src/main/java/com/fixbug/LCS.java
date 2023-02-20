package com.fixbug;

import java.util.Arrays;

/**
 * 问题描述:
 * 有两个序列：
 * X = {X1,X2,...,Xn}
 * Y = {Y1,Y2,...,Ym}
 * 求X和Y序列的最长公共子序列的长度是多少？（最长的公共子串长度？   子串：必须是连续的   子序列：可以不连续）
 *
 * 当Xn == Ym：
 * LCS{Xn,Ym} = LCS{X1...Xn-1, Y1...Ym-1} + 1;
 * 当Xn != Ym：
 * LCS{X,Y} = max{LCS{X1...Xn, Y1...Ym-1}, LCS{X1...Xn-1, Y1...Ym}}
 *
 * 动态规划算法 - 子问题的状态
 * dp[n][m] : 表示X1-Xn和Y1-Yn两个序列的LCS的长度
 *
 */
public class LCS {
    static int count = 0;
    public static void main(String[] args) {
        String src = "helloworld";
        String dest = "oyfuiwod";


        /*
        // 用分治算法求解 - 延伸到动态规划算法解决
        int[][] dp = new int[src.length()][dest.length()];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        int length = 0;
        length = lcsLength(src, src.length()-1, dest, dest.length()-1, dp);
        System.out.println(length);
        System.out.println(count);*/


        // 非递归动态规划解决方案代码输出
        int[][] dp = new int[src.length()+1][dest.length()+1];
        // 怎么把LCS里面的公共子序列全部输出
        int[][] path = new int[src.length()+1][dest.length()+1];

        int length = 0;
        length = lcsLength02(src, src.length(), dest, dest.length(), dp, path);
        System.out.println("length:" + length);

        for (int[] ints : path) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        backStrace(src, src.length(), dest.length(), path);
    }

    /**
     * 打印最长公共子序列中的元素内容
     * @param src
     * @param n
     * @param m
     * @param path
     */
    private static void backStrace(String src, int n, int m, int[][] path) {
        if(n <= 0 || m <= 0){
            return;
        }

        if(path[n][m] == 1){ // 表示从左上角来的
            backStrace(src, n-1, m-1, path);
            System.out.print(src.charAt(n-1));
        } else {
            if(path[n][m] == 2){
                backStrace(src, n-1, m, path);
            } else {
                backStrace(src, n, m-1, path);
            }
        }
    }

    /**
     * 非递归代码 - 动态规划 - 解决LCS问题
     * @param src
     * @param length1
     * @param dest
     * @param length2
     * @param dp
     * @param path
     * @return
     */
    private static int lcsLength02(String src, int length1, String dest, int length2, int[][] dp, int[][] path) {
        int max = 0;
        for (int n = 1; n <= length1; n++) {
            for (int m = 1; m <= length2; m++) {
                if(src.charAt(n-1) == dest.charAt(m-1)){
                    // 把[X0-n-1, Y0-m-1] LCS长度记录在[n-1][m-1]右下角坐标上
                    dp[n][m] = dp[n-1][m-1] + 1;   // 二维数组中，向右下角出发
                    path[n][m] = 1;  // 从对角线来的
                } else {
                    int len1 = dp[n-1][m];  // 二维数组中，向下面出发
                    int len2 = dp[n][m-1];  // 二维数组中，向右边出发
                    if(len1 > len2){
                        path[n][m] = 2; // 表示从上面下来的
                    } else {
                        path[n][m] = 3; // 表示从左边来的
                    }
                    dp[n][m] = Math.max(len1, len2);
                }

                if(max < dp[n][m]){
                    max = dp[n][m];
                }
            }
        }
        return max;
    }

    /**
     * 求两个序列的LCS的长度
     * @param src
     * @param n
     * @param dest
     * @param m
     * @param dp
     * @return
     */
    private static int lcsLength(String src, int n, String dest, int m, int[][] dp) {
        if(n < 0 || m < 0){
            return 0;
        }

        if(dp[n][m] != -1){
            return dp[n][m];
        }

        count++;

        if(src.charAt(n) == dest.charAt(m)){
            dp[n][m] = 1 + lcsLength(src, n-1, dest, m-1, dp);
            return dp[n][m];
        } else {
            int len1 = lcsLength(src, n, dest, m-1, dp);
            int len2 = lcsLength(src, n-1, dest, m, dp);
            // dp[m][n]记录X1-Xm和Y1-Yn的LCS的最大值
            if(len1 > len2){
                dp[n][m] = len1;
            } else {
                dp[n][m] = len2;
            }
            return dp[n][m];
        }
    }
}
