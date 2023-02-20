package com.fixbug.link;

import java.util.Scanner;

/**
 * 带头节点的单向循环链表解决约瑟夫环的问题
 */
/*class Josephus{
    Josephus(){
        this.head = new Entry(0, null);
        this.head.next = this.head;
    }

    void insertTail(int val){
        Entry last = this.head;
        while(last.next != this.head){
            last = last.next;
        }
        last.next = new Entry(val, this.head);
    }

    *//**
     * 打印约瑟夫环出列的顺序
     * @param k 从第k个人开始报数
     * @param m 每次都是报m个数结束的人出列
     *         head-> 1,2,3,4,5,6,7,8,9,10
     *//*
    public void show(int k, int m) {
        Entry pre = this.head;
        Entry cur = this.head.next; // 1

        // 让cur指向第k个人
        while(k-- > 1){
            pre = cur;
            cur = cur.next;
        }

        // 循环进行报数，然后出列
        for(;;){
            if(this.head.next == this.head){
                // 链表为空，说明所有人都出列了
                return;
            }

            // 报数之前，检查一个cur，一定要指向一个有效的人
            if(cur == this.head){
                pre = cur;
                cur = cur.next;
            }

            // 报数m的过程
            for(int i=1; i<m; ++i){
                pre = cur;
                cur = cur.next;
                if(cur == this.head){
                    pre = cur;
                    cur = cur.next;
                }
            }

            // 删除cur节点
            System.out.println(cur.data + "出列!");
            pre.next = cur.next;
            cur = pre.next;
        }
    }

    static class Entry{
        int data;
        Entry next;

        public Entry(int data, Entry next) {
            this.data = data;
            this.next = next;
        }
    }

    Entry head;
}*/

/**
 * 不带头节点的单向循环链表解决约瑟夫环的问题
 */
class Josephus{
    Josephus(){
        this.first = null;
    }

    void insertTail(int val){
        // 因为链表不带头节点了，所以必须考虑链表为空的情况
        if(this.first == null){
            this.first = new Entry(val, null);
            this.first.next = this.first;
            return;
        }

        Entry last = this.first;
        while(last.next != this.first){
            last = last.next;
        }
        last.next = new Entry(val, this.first);
    }

    /**
     * 打印约瑟夫环出列的顺序
     * @param k 从第k个人开始报数
     * @param m 每次都是报m个数结束的人出列
     *         head-> 1,2,3,4,5,6,7,8,9,10
     */
    public void show(int k, int m) {
        Entry pre = this.first;
        Entry cur = this.first; // 1

        // 因为cur是第一个节点，那么pre就是最后一个节点
        while(pre.next != this.first){
            pre = pre.next;
        }

        // 让cur指向第k个人
        while(k-- > 1){
            pre = cur;
            cur = cur.next;
        }

        // 循环进行报数，然后出列
        for(;;){
            // cur和pre指向了同一个节点，意思就是链表剩余一个节点
            if(cur == pre){
                System.out.println(cur.data + "出列!");
                this.first = null;
                return;
            }

            // 报数m的过程
            for(int i=1; i<m; ++i){
                pre = cur;
                cur = cur.next;
            }

            // 删除cur节点
            System.out.println(cur.data + "出列!");
            pre.next = cur.next;
            cur = pre.next;  // cur == pre
        }
    }

    static class Entry{
        int data;
        Entry next;

        public Entry(int data, Entry next) {
            this.data = data;
            this.next = next;
        }
    }

    Entry first; // 指向链表的第一个节点
}

/**
 * 描述:
 *约瑟夫环是一个数学的应用问题：已知n个人（以编号1，2，3...n分别表示）围坐在一张圆桌周围，
 * 从编号为k的人开始报数，数到m的那个人出列，它的下一个人又从1开始报数，数到m的那个人又出列，
 * 依此规律重复下去，直到圆桌周围的人全部出列，输出人的出列顺序。
 * @Author shilei
 * @Date 2019/8/27
 */
public class 约瑟夫环问题 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("输入人的个数:");
        int n = in.nextInt();
        System.out.println("输入开始报数的人的序号:");
        int k = in.nextInt();
        System.out.println("输入每次报数的次数:");
        int m = in.nextInt();

        Josephus j = new Josephus();
        for (int i = 1; i <= n; i++) {
            j.insertTail(i);
        }
        j.show(k, m);
    }
}