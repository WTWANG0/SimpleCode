package com.fixbug;

/**
 * 描述:
 *
 * @Author shilei
 */
public class BF {
    public static void main(String[] args) {
        String S = "goodgoogle";
        String T = "google";

        int index = bf(S, T);
        System.out.println("index:" + index);
    }

    /**
     * 模式匹配，BF算法，找到返回子串的下标，否则返回-1
     * @param S
     * @param T
     * @return
     */
    private static int bf(String S, String T) {
        int i = 0;
        int j = 0;
        while(i < S.length() && j < T.length()){
            if(S.charAt(i) == T.charAt(j)){
                i++;
                j++;
            } else {
                i = i-j+1;
                j = 0;
            }
        }

        if(j == T.length()){
            return i - j;
        } else {
            return -1;
        }
    }
}
