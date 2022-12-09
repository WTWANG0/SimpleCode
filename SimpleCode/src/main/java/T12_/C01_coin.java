package T12_;

/**
 * 硬币选择
 * 描述: 有1，3，5三种面额的硬币，问组成指定面值如11，需要最少的硬币的数量？？？
 * 11
 * 10(1)                       8(3)                          6(5)
 * 9(1)   7(3)    5(5)       7(1)     5(3)     3(5)          5(1)   3(3)    1(5)
 * <p>
 * dp[i] : 面值i所需要的硬币的最少数量      最优解的性质 / 问题的结构特征  /  状态
 * dp[0] : 0
 * dp[1] : 1 + dp[1-1] = 1 + dp[0] = 1 + 0 = 1
 * dp[2] : 1 + dp[2-1] = 1 + dp[1] = 1 + 1 = 2
 * dp[3] : 1 + dp[3-1] = 1 + dp[2] = 1 + 2 = 3   3个1分硬币
 * 1 + dp[3-3] = 1 + dp[0] = 1  直接选择一个3分硬币
 * dp[5] : 1 + dp[4] = 1 + 2 = 3   1,1,3
 * 1 + dp[5-3] = 1 + dp[2] = 1 + 2 = 3   3,1,1
 * 1 + dp[5-5] = 1 + dp[0] = 1
 * 状态转移方程如下：
 * dp[i] = min{1 + dp[i-vj]}  i 表示面值   vj表示第j个硬币的面额  i>=vj  ！！！
 */
public class C01_coin {
    public static void main(String[] args) {
        test1();
    }

    static void test1() {
        int[] arr = {1, 3, 5};
        int n = 11;

        int way1 = minCornNumber(n);
        System.out.println("way1:" + way1);

        System.out.println("------------------------------------------");

        int way2 = dpMethod(arr, n);
        System.out.println("way2:" + way2);
    }


    /**
     * way2
     */
    static int dpMethod(int[] arr, int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i; //先默认最多的数量
            for (int j = 0; j < arr.length; j++) {
                if (i >= arr[j] && (1 + dp[i - arr[j]]) < dp[i]) {
                    dp[i] = 1 + dp[i - arr[j]];
                }
            }
        }
        return dp[n];
    }


    /**
     * way1:原始暴力解
     */
    static int minCornNumber(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 3 || n == 5) {
            return 1;
        } else if (n == 2 || n == 4) {
            return 2;
        } else {
            int c1 = 1 + minCornNumber(n - 1);
            int c2 = 1 + minCornNumber(n - 3);
            int c3 = 1 + minCornNumber(n - 5);
            return Math.min(c1, Math.min(c2, c3));
        }
    }

}
