package com.fixbug;

import java.util.Arrays;

/**
 * 描述:
 *
 * @Author shilei
 */
public class 最优装载问题 {

    static class Product implements Comparable<Product>{
        int w; // 物品的重量
        int id; // 物品的id

        public Product(int id, int w) {
            this.w = w;
            this.id = id;
        }

        @Override
        public int compareTo(Product o) {
            return ((Integer)w).compareTo(o.w);
        }
    }

    public static void main(String[] args) {
        Integer[] w = new Integer[10]; // i个物体，arr[i]就是第i个物体的重量wi
        for (int i = 0; i < w.length; i++) {
            w[i] = (int)(Math.random()*100);
        }
        int c = 100;
        System.out.println(Arrays.toString(w));

        Product[] products = new Product[w.length];
        for (int i = 0; i < w.length; i++) {
            products[i] = new Product(i, w[i]);
        }

        Arrays.sort(products);
        int weight = 0; // 记录选择的物品的重量
        int count = 0; // 记录选择的物品的个数
        for (int i = 0; i < products.length; i++) {
            if(weight < c){
                count++;
                c -= products[i].w;
                weight += products[i].w;
            } else {
                break;
            }
        }

        System.out.println(count);
        for (int i = 0; i < count; i++) {
            System.out.print(w[products[i].id] + " ");
        }
        System.out.println();

        /*
        Arrays.sort(w);  // 对于所有物品的重量进行升序排序

        int weight = 0; // 记录选择的物品的重量
        int count = 0; // 记录选择的物品的个数
        for (int i = 0; i < w.length; i++) {
            if(weight < c){
                count++;
                c -= w[i];
                weight += products[i].w;
            } else {
                break;
            }
        }

        System.out.println(count);*/
    }
}
