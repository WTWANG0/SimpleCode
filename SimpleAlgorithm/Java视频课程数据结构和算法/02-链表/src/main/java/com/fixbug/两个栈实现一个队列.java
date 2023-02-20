package com.fixbug;

import java.util.LinkedList;

/**
 * 描述:
 *
 * @Author shilei
 * @Date 2019/8/29
 */
public class 两个栈实现一个队列 {
    private LinkedList<Integer> s1;
    private LinkedList<Integer> s2;

    {
        s1 = new LinkedList<>();
        s2 = new LinkedList<>();
    }

    /**
     * 入队操作，都添加到s1中
     * @param val
     */
    public void offer(int val){
        this.s1.push(val);
    }

    /**
     * 出队列
     * @return
     */
    public Integer poll(){
        if(empty()){
            return null;
        }

        if(this.s2.isEmpty()){
            // 如果s2为空，则把s1的栈元素全部出栈放入s2中
            while(!this.s1.isEmpty()){
                this.s2.push(this.s1.pop());
            }
        }
        return this.s2.pop();
    }

    /**
     * 查看队头元素
     * @return
     */
    public Integer peek(){
        if(empty()){
            return null;
        }

        if(this.s2.isEmpty()){
            // 如果s2为空，则把s1的栈元素全部出栈放入s2中
            while(!this.s1.isEmpty()){
                this.s2.push(this.s1.pop());
            }
        }
        return this.s2.peek();
    }

    /**
     * 判空
     * @return
     */
    public boolean empty(){
        return this.s1.isEmpty() && this.s2.isEmpty();
    }

    public static void main(String[] args) {
        两个栈实现一个队列 que = new 两个栈实现一个队列();
        for (int i = 0; i < 20; i++) {
            que.offer(i+1);
        }

        while(!que.empty()){
            System.out.print(que.poll() + " ");
        }
    }
}