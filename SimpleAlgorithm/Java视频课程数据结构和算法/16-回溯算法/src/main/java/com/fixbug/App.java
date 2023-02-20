package com.fixbug;

/**
 * 描述: 理解   解空间    子集树
 *
 * @Author shilei
 */
public class App {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        int[] x = new int[arr.length+1];
        func(arr, 0, x);
    }

    private static void func(int[] arr, int i, int[] x) {
        if(i == arr.length){
            for (int j = 0; j < arr.length; j++) {
                if(x[j] == 1){
                    System.out.print(arr[j] + " ");   // 1 2 3
                }
            }
            System.out.println();
        } else {
            x[i] = 1;  // 选择当前的i节点
            func(arr, i+1, x);  // 去i的左孩子

            x[i] = 0;  // 不选择当前的i节点
            func(arr, i+1, x);  // 去i的右孩子
        }
    }
}