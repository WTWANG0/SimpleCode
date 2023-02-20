package com.fixbug.queue;

import java.util.Arrays;

/**
 * 描述: 循环队列的实现
 *
 * LinkedList  链表 栈 队列
 * 模拟栈  push pop peek
 * 模拟队列 offer poll peek
 *
 * @Author shilei
 * @Date 2019/8/29
 */
public class CircleQueue {
    // 该数组存储队列的所有元素
    private int[] que;
    // 指向队头位置
    private int front;
    // 指向队尾位置
    private int rear;

    /**
     * 初始化队列，默认大小10
     */
    public CircleQueue(){
        this(10);
    }

    /**
     * 初始化队列，用户自定义队列大小
     * @param size
     */
    public CircleQueue(int size) {
        this.que = new int[size];
        this.front = this.rear = 0;
    }

    /**
     * 入队
     * @param val
     */
    public void offer(int val){
        if(full()){
            // 实现队列的扩容
            int[] tmp = new int[this.que.length*2];
            int index = 0;
            // 遍历旧的队列数组，从队头开始拷贝元素到新的数组tmp中
            for(int i=this.front; i != this.rear; i=(i+1)%this.que.length){
                tmp[index++] = this.que[i];
            }
            this.front = 0;
            this.rear = index;
            this.que = tmp;
        }

        this.que[this.rear] = val;
        this.rear = (this.rear + 1) % this.que.length;
    }

    /**
     * 出队
     */
    public void poll(){
        if(empty()){
            return;
        }
        this.front = (this.front + 1) % this.que.length;
    }

    /**
     * 查看队头元素   LinkedList 泛型写的   null
     * @return
     */
    public int peek() throws Exception{
        if(empty()){
            throw new Exception("queue is empty!");
        }
        return this.que[this.front];
    }

    /**
     * 判空
     * @return
     */
    public boolean empty(){
        return this.front == this.rear;
    }

    /**
     * 判满
     * @return
     */
    public boolean full(){
        return (this.rear + 1) % this.que.length == this.front;
    }

    public static void main(String[] args) throws Exception{
        CircleQueue cq = new CircleQueue();
        for (int i = 0; i < 20; i++) {
            cq.offer(i+1);
        }

        while(!cq.empty()){
            int val = cq.peek();
            System.out.print(val + " ");
            cq.poll();
        }
    }
}
