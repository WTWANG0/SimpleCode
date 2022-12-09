package T12_;

/**
 * 最大字段和
 * 问题的状态：dp[i]：以第i个元素结尾的最大字段和
 * 状态转移方程： dp[i] = dp[i-1] + arr[i]
 */
public class C02_number {
    public static void main(String[] args) {
        int[] arr = {-2, 11, -4, 13, -5, -2};

        int[] dp = new int[arr.length];
        dp[0] = arr[0] > 0 ? arr[0] : 0;
        int maxSum = dp[0];
        for (int i = 1; i < arr.length; i++) {
            dp[i] = dp[i - 1] + arr[i];
            if (dp[i] < 0) {
                dp[i] = 0;
            }

            if (maxSum < dp[i]) {
                maxSum = dp[i];
            }
        }

        System.out.println("maxSum:" + maxSum);

    }
}
