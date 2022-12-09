package T12_;

import java.util.Arrays;

/**
 * 5 3 4 1 8 7 9
 * 找问题的状态
 * dp[i] : 表示以第i个数字结尾的最长非降子序列的长度值
 * >>>>>>>>>非降<<<<<<<<<<<<<<<<<<<<
 * 找状态的转移方程
 * dp[0] : 1
 * dp[1] : 1
 * dp[2] : 2
 * dp[3] : 3
 * dp[i] = max{1, dp[j]+1}  条件：j < i && ar[j] <= ar[i]
 */
public class C06_LCS_MAX {
    public static void main(String[] args) {
        int[] ar = {5, 3, 4, 1, 8, 7, 9};

        int[] dp = new int[ar.length];

        int max = 0;
        dp[0] = 1;
        for (int i = 1; i < ar.length; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (ar[j] <= ar[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }

            if (dp[i] > max) {
                max = dp[i];
            }
        }

        System.out.println(Arrays.toString(dp));
        System.out.println(max);
    }

}
