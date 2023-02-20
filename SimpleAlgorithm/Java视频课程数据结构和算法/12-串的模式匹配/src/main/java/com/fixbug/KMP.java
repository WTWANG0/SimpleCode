package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class KMP {
    public static void main(String[] args) {
        String S = "goodgoogle";
        String T = "google";

        int index = kmp(S, T);
        System.out.println("index:" + index);
    }

    /**
     * KMP算法代码实现
     * @param S
     * @param T
     * @return
     */
    private static int kmp(String S, String T) {
        int i = 0;
        int j = 0;
        int[] next = getNext(T);
        while(i < S.length() && j < T.length()){
            if(j == -1 || S.charAt(i) == T.charAt(j)){
                i++;
                j++;
            } else {
                //i = i-j+1;
                //j = 0;
                j = next[j];
            }
        }

        if(j == T.length()){
            return i - j;
        } else {
            return -1;
        }
    }

    private static int[] getNext(String T) {
        int[] next = new int[T.length()];
        int j = 0; // j表示子串T的下标
        int k = -1; // k表示子串T每一个字符对应的k值
        next[0] = k;  // #1

        while (j < T.length()-1){
            if(k == -1 || T.charAt(k) == T.charAt(j)){ // #2
                k++;
                j++;

                // 对KMP算法的优化，例如ABCABC(0)  ABCABD(2)
                if(T.charAt(k) == T.charAt(j)){
                    next[j] = next[k];
                } else {
                    next[j] = k;  // #3 next[j] = 0
                }
            } else {
                // k != -1说明有公共前后缀   T.charAt(k) != T.charAt(j) 表示公共前后缀结束了
                k = next[k];
            }
        }
        return next;
    }
}
