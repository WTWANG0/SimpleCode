package T12_;

/**
 * 问题描述:
 * 有一组物品，其重量为
 * w1,w2,...,wn，其价值分别是
 * v1,v2,...,vn，现在有一个背包，其容量为c，问怎么样装入物品，能够使背包的价值最大化？
 * <p>
 * LCS的问题， 两个序列  X，Y，求LCS
 * dp[n][m] = LCS的长度
 * 物品， 背包 ， 最优价值
 * dp[可选择的物品][背包容量] = 价值最优
 * dp[i][j] : 表示可选择的物品是i,i+1,i+2,...,n，背包的容量是j，存储的是装入背包的物品的最优价值
 * <p>
 * 状态的转移方程：
 * 考虑dp[n][j]
 * wn > j    说明物品n无法装入背包   dp[n][j] = 0
 * wn <= j   说明物品n可以装入背包   dp[n][j] = wn
 * <p>
 * 考虑dp[i][j]   i,i+1,i+2,...,n
 * wi > j    说明物品i无法装入背包   dp[i][j] = dp[i+1][j]
 * wi <= j   说明物品i可以装入背包   dp[i][j] = max{dp[i+1][j], vi+dp[i+1][j-wi]}
 * <p>
 * int[] w = {8,6,4,2,5};
 * int[] v = {6,4,7,8,6};
 * int c = 12;
 * 1  2  3  4  5  6  7  8  9  10  11  12   背包容量C
 * w1
 * w2
 * w3
 * w4 0  8   8  8  8  8 14  14 14 14  14  14
 * w5 0  0   0  0  6  6  6  6  6  6   6   6
 * <p>
 * <p>
 * 1    2    3    4    5    6    7    8    9    10   11   12
 * w1    0    8    8    8    8    15   15   15   15   15   21   21
 * w2    0    8    8    8    8    15   15   15   15   15   21   21
 * w3    0    8    8    8    8    15   15   15   15   15   21   21
 * w4    0    8    8    8    8    8    14   14   14   14   14   14
 * w5    0    0    0    0    6    6    6    6    6    6    6    6
 */
public class C04_Package {
    public static void main(String[] args) {
        int[] w = {8, 6, 4, 2, 5}; //重量
        int[] v = {6, 4, 7, 8, 6}; //val

        int c = 12;

        int[][] dp = new int[w.length][c + 1]; //[使用了第几个物品][重量]

        int n = w.length - 1;

        //先处理dp[n][i]
        for (int i = 0; i < c; i++) {
            if (w[n] > i) {
                dp[n][i] = 0;
            } else {
                dp[n][i] = v[n];
            }
        }

        //
        for (int i = (n - 1); i >= 0; i--) {
            for (int j = 0; j < i; j++) {

            }
        }


    }
}
