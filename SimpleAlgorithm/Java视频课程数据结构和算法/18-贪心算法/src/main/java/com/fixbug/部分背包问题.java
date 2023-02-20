package com.fixbug;

import java.util.Arrays;

/**
 * 描述:部分背包问题，有n个物体，第i个物体的重量为wi，价值为vi。在总重量不超过C的情况下让总价值尽量
 * 每一个物体都可以只取走一部分，价值和重量按比例计算。求最大总价值。
 *
 * @Author shilei
 */
public class 部分背包问题 {

    static class Product implements Comparable<Product>{
        int id; // 物品的id
        int w;  // 物品的重量
        int v;  // 物品的价值

        public Product(int id, int w, int v) {
            this.id = id;
            this.w = w;
            this.v = v;
        }

        // 返回物品的价值比
        public double getPrice(){
            return (double)v / w;
        }

        @Override
        public int compareTo(Product o) {
            double p1 = getPrice();
            double p2 = o.getPrice();
            if(p1 > p2){
                return -1;
            } else if(p1 < p2){
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] w = {8,6,4,2,5};
        int[] v = {6,4,7,8,6};
        int c = 12;
        int n = w.length;
        int[] x = new int[n];

        Product[] products = new Product[n];
        for (int i = 0; i < products.length; i++) {
            products[i] = new Product(i, w[i], v[i]);
        }

        Arrays.sort(products); // 按物品的价值比降序排列物品

        double bestv = 0; // 记录装入物品的价值
        for (int i = 0; i < products.length; i++) {
            if(products[i].w <= c){ // 说明第i个物品能够装入背包
                bestv += products[i].v;
                c -= products[i].w;
            } else { // 说明第i个物品无法全部装入背包，尝试装入一部分
                bestv = bestv + products[i].v *((double)c / products[i].w);
                x[products[i].id] = 1;
                break;
            }
            x[products[i].id] = 1;
        }
        // count

        System.out.println(bestv);
        System.out.println(Arrays.toString(x));
    }
}
