package com.fixbug;

import java.util.Arrays;

/**
 * 描述: 有1，3，5三种面额的硬币，问组成指定面值如11，需要最少的硬币的数量？？？
 *                                     11
 *         10(1)                       8(3)                          6(5)
 *  9(1)   7(3)    5(5)       7(1)     5(3)     3(5)          5(1)   3(3)    1(5)
 *
 *  dp[i] : 面值i所需要的硬币的最少数量      最优解的性质 / 问题的结构特征  /  状态
 *  dp[0] : 0
 *  dp[1] : 1 + dp[1-1] = 1 + dp[0] = 1 + 0 = 1
 *  dp[2] : 1 + dp[2-1] = 1 + dp[1] = 1 + 1 = 2
 *  dp[3] : 1 + dp[3-1] = 1 + dp[2] = 1 + 2 = 3   3个1分硬币
 *          1 + dp[3-3] = 1 + dp[0] = 1  直接选择一个3分硬币
 *  dp[5] : 1 + dp[4] = 1 + 2 = 3   1,1,3
 *          1 + dp[5-3] = 1 + dp[2] = 1 + 2 = 3   3,1,1
 *          1 + dp[5-5] = 1 + dp[0] = 1
 *  状态转移方程如下：
 *  dp[i] = min{1 + dp[i-vj]}  i 表示面值   vj表示第j个硬币的面额  i>=vj  ！！！
 *
 *
 * @Author shilei
 */
public class 硬币选择 {
    public static void main(String[] args) {

        int[] arr = {1,3,5}; // 硬币面额数组
        int n = 11; // 面值

        // 1. 采用分治算法求解
        //int number = minCornNumber(n);
        //System.out.println(number);

        // 2. 动态规划算法求解
        int[] dp = new int[n+1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i; // 初始化问题的状态值 假设现在都选择1分硬币
            for (int j = 0; j < arr.length; j++) {
                if(i >= arr[j] && (1+dp[i-arr[j]]) < dp[i]){
                    dp[i] = 1+dp[i-arr[j]];
                }
            }
        }

        System.out.println(Arrays.toString(dp));
        System.out.println(dp[n]);
    }

    /**
     * 采用分治算法求硬币数量，存在子问题重复求解     返回组成面值n的硬币的最少数量！！！
     * @param n
     * @return
     */
    private static int minCornNumber(int n) {
        if(n == 0){
            return 0;
        } else if(n == 1 || n == 3 || n == 5){
            return 1;
        } else if(n == 2 || n == 4){
            return 2;
        } else {  // 面值 > 5
            int c1 = 1 + minCornNumber(n-1);  // n-1面值
            int c2 = 1 + minCornNumber(n-3);  // n-3面值
            int c3 = 1 + minCornNumber(n-5);  // n-5面值
            return Math.min(c1, Math.min(c2, c3));
        }
    }
}