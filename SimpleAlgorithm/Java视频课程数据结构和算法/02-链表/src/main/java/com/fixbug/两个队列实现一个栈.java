package com.fixbug;

import com.fixbug.queue.LinkQueue;
import sun.awt.image.ImageWatched;

/**
 * 描述:
 *
 * @Author shilei
 * @Date 2019/8/29
 */
public class 两个队列实现一个栈 {

    private LinkQueue queue1;
    private LinkQueue queue2;

    {
        this.queue1 = new LinkQueue();
        this.queue2 = new LinkQueue();
    }

    /**
     * 入栈操作
     * @param val
     */
    public void push(int val){
        if(!queue1.empty()){ // 元素都在队列1存放
            queue1.offer(val);
        } else if(!queue2.empty()){ // 元素都在队列2存放
            queue2.offer(val);
        } else {
            // 当前没有元素
            queue1.offer(val);
        }
    }

    /**
     * 出栈操作
     * @return
     */
    public Integer pop(){
        if(empty()){
            return null;
        }

        // 遍历有元素的那个队列，并且把队列的元素全部出队，入到另一个队列当中
        LinkQueue srcQue = this.queue1;
        LinkQueue destQue = this.queue2;
        if(srcQue.empty()){
            srcQue = this.queue2;
            destQue = this.queue1;
        }

        int data = 0;
        while(!srcQue.empty()){
            data = srcQue.peek();
            srcQue.poll();
            if(srcQue.empty()){
                break;
            }
            destQue.offer(data);
        }

        return data;
    }

    /**
     * 判空
     * @return
     */
    public boolean empty(){
        return this.queue2.empty() && this.queue1.empty();
    }

    public static void main(String[] args) {
        两个队列实现一个栈 s = new 两个队列实现一个栈();
        for (int i = 0; i < 20; i++) {
            s.push(i+1);
        }

        while(!s.empty()){
            int data = s.pop();
            System.out.print(data + " ");
        }
    }
}
