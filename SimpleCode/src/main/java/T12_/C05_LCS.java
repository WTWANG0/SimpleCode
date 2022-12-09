package T12_;

import java.util.Arrays;

/**
 * 问题描述:
 * 有两个序列：
 * X = {X1,X2,...,Xn}
 * Y = {Y1,Y2,...,Ym}
 * 求X和Y序列的最长公共子序列的长度是多少？
 * （最长的公共子串长度？   子串：必须是连续的   子序列：可以不连续）
 * <p>
 * 当Xn == Ym：
 * LCS{Xn,Ym} = LCS{X1...Xn-1, Y1...Ym-1} + 1;
 * <p>
 * 当Xn != Ym：
 * LCS{X,Y} = max{LCS{X1...Xn, Y1...Ym-1}, LCS{X1...Xn-1, Y1...Ym}}
 * <p>
 * 动态规划算法 - 子问题的状态
 * dp[n][m] : 表示X1-Xn和Y1-Yn两个序列的LCS的长度
 */
public class C05_LCS {
    static int count = 0;

    public static void main(String[] args) {
        String src = "helloworld";
        String dest = "oyfuiwod";
        test1(src, dest);
        test2(src, dest);
    }


    static void test1(String src, String dest) {
        int[][] dp = new int[src.length()][dest.length()];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        int length = 0;
        length = lcsLength(src, src.length() - 1, dest, dest.length() - 1, dp);
        System.out.println("length" + length);

    }


    private static int lcsLength(String src, int n, String dest, int m, int[][] dp) {
        if (n < 0 || m < 0) {
            return 0;
        }

        if (dp[n][m] != -1) {
            return dp[m][n];
        }

        count++;

        if (src.charAt(n) == dest.charAt(m)) {
            dp[n][m] = 1 + lcsLength(src, n - 1, dest, m - 1, dp);
            return dp[n][m];
        } else {
            int len1 = lcsLength(src, n, dest, m - 1, dp);
            int len2 = lcsLength(src, n - 1, dest, m, dp);
            dp[n][m] = len1 > len2 ? len1 : len2;
            return dp[n][m];
        }
    }


    static void test2(String src, String dest) {
        int[][] dp = new int[src.length() + 1][dest.length() + 1];

        int[][] path = new int[src.length() + 1][dest.length() + 1];

        int length = 1;//todo: lcsLength2(src, src.length() - 1, dest, dest.length() - 1, dp, path);
        System.out.println("length:" + length);
        for (int[] ints : path) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        backStrace(src, src.length(), dest.length(), path);
    }

    private static void backStrace(String src, int n, int m, int[][] path) {
        if (n < 0 || m < 0) {
            return;
        }

        if (path[n][m] == 1) {
            backStrace(src, n - 1, m - 1, path);
            System.out.println(src.charAt(n - 1));
        } else if (path[n][m] == 2) {
            backStrace(src, n - 1, m, path);
        } else {
            backStrace(src, n, m - 1, path);
        }
    }


}
