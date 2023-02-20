package com.fixbug;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 面试题：用链表实现两个大数的加法，输出加法的结果
 *
 * @Author shilei
 * @Date 2019/8/31
 */
public class 大数加法 {
    public static void main(String[] args) {
        String str1 = "3862957867832453463457";
        String str2 = "5678564534397685764";

        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        LinkedList<Integer> result = new LinkedList<>();

        for(int i=0; i<str1.length(); ++i){
            list1.addFirst(str1.charAt(i) - '0');
        }

        for(int i=0; i<str2.length(); ++i){
            list2.addFirst(str2.charAt(i) - '0');
        }

        Iterator<Integer> it1 = list1.iterator();
        Iterator<Integer> it2 = list2.iterator();

        // 保存进位
        boolean flag = false;
        while(it1.hasNext() && it2.hasNext()){
            int left = it1.next();
            int right = it2.next();

            int ret = left + right;
            if(flag){ // 表示有进位需要处理
                ret += 1;
                flag = false; // 把进位处理过了
            }
            if(ret >= 10){
                ret %= 10;
                flag = true; // 产生进位
            }
            result.addFirst(ret);
        }

        // 有可能长的数字还没有处理完
        while(it1.hasNext()){
            int ret = it1.next();
            if(flag){ // 表示有进位需要处理
                ret += 1;
                flag = false; // 把进位处理过了
            }
            if(ret >= 10){
                ret %= 10;
                flag = true; // 产生进位
            }
            result.addFirst(ret);
        }

        while(it2.hasNext()){
            int ret = it2.next();
            if(flag){ // 表示有进位需要处理
                ret += 1;
                flag = false; // 把进位处理过了
            }
            if(ret >= 10){
                ret %= 10;
                flag = true; // 产生进位
            }
            result.addFirst(ret);
        }

        if(flag){
            result.addFirst(1);
        }

        for(int val : result){
            System.out.print(val);
        }
    }
}