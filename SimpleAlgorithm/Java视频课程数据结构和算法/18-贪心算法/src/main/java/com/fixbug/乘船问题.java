package com.fixbug;

import java.util.Arrays;

/**
 * 描述:乘船问题，有n个人，第i个人重量为wi。每艘船的最大载重量均为C，且最多只能乘两个人。用最少的船装载所有人。求需要最少的船的数量
 *
 * @Author shilei
 */
public class 乘船问题 {
    public static void main(String[] args) {
        Integer[] w = new Integer[10]; // i个人，arr[i]就是第i个人的重量wi
        for (int i = 0; i < w.length; i++) {
            w[i] = (int)(Math.random()*100);
        }
        int c = 100;
        System.out.println(Arrays.toString(w));

        Arrays.sort(w);
        System.out.println(Arrays.toString(w));

        //[23, 33, 37, 52, 53, 54, 60, 60, 68, 99]
        int personCnt = w.length;
        int boatCount = 0;
        int p1 = 0;
        int p2 = w.length-1;

        while (personCnt > 0){
            if(w[p1] + w[p2] > c){
                boatCount++;
                personCnt--;
                p2--;
            } else {
                // p1和p2同坐一条船
                p1++;
                p2--;
                personCnt -= 2;
                boatCount++;
            }
        }
        System.out.println(boatCount);
    }
}
