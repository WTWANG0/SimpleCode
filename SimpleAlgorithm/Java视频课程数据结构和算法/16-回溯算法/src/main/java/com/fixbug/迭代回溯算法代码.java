package com.fixbug;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 迭代回溯算法代码 {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        int[] x = new int[arr.length];
        int i = 0; // i代表解空间树的高度
        int choice = 0;

        while (i >= 0){ // 模拟递归回溯
            if(x[i] <= 1){ // 遍历第i层的节点
                x[i] += 1;
                if(x[i] == 2){
                    choice--;
                }
                if(x[i] == 1){ // 剪枝操作
                    if(choice < 2)
                        choice++;
                    else
                        x[i] += 1;
                }

                if(i == arr.length-1){
                    for (int j = 0; j < arr.length; j++) {
                        if(x[j] == 1){
                            System.out.print(arr[j] + " ");
                        }
                    }
                    System.out.println();
                } else {
                    i++;  // 还没有到叶子节点，继续向下推进一层
                }
            } else {
                x[i] = 0;
                i--;  // 第i层的节点遍历完了，往上回退一层
            }
        }
    }
}