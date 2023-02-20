package com.fixbug;

/**
 * 描述: 从指定的整数序列中，挑选一部分数字，让其和等于指定的值，打印挑选出来的数字序列
 *
 * @Author shilei
 */
public class 整数选择2 {
    static int[] arr = {12,4,5,18,21,23,17,15};
    static int number = 53;

    static int[] x = new int[arr.length]; // 辅助数组
    static int[] bestx = new int[arr.length]; // 记录最优解的子集
    static int cv = 0; // 记录已选择数字的和
    static int r = 0; // 记录剩余数字的和

    static int count = 0;  // 256

    public static void main(String[] args) {
        for (int i = 0; i < arr.length; i++) {
            r += arr[i];
        }
        func(arr, 0, x);
        System.out.println("count:" + count);
    }

    private static void func(int[] arr, int i, int[] x) {
        if(i == arr.length){
            count++; // 访问一个叶子节点，就统计一次
            if(cv == number){
                for (int j = 0; j < arr.length; j++) {
                    if(x[j] == 1){
                        System.out.print(arr[j] + " ");
                    }
                }
                System.out.println();
            }
        } else {
            // 访问i节点
            r -= arr[i];
            // 选择第i个节点  cv + arr[i] > number
            if(cv + arr[i] <= number){   // 剪枝操作
                cv += arr[i];
                x[i] = 1;
                func(arr, i+1, x);
                cv -= arr[i];
            }


            // 此处相当于回溯到i节点了，去往i节点的右子树了
            if(cv + r >= number){  // 剪枝操作
                x[i] = 0;
                func(arr, i+1, x);
            }

            // 又回溯到i节点了，继续往i节点的上面回溯
            r += arr[i];
        }
    }
}
