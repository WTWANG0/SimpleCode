package com.fixbug.queue;

/**
 * 描述: 链式队列实现
 *
 * @Author shilei
 * @Date 2019/8/29
 */
public class LinkQueue {

    // 指向链表的头
    private Entry front;
    // 指向链表的末尾节点
    private Entry rear;

    /**
     * 初始化，front和rear都指向头节点
     */
    public LinkQueue(){
        this.front = this.rear = new Entry();
    }

    /**
     * 入队操作
     * @param val
     */
    public void offer(int val){
        Entry node = new Entry(val, null);
        // 把新的节点插入到队尾
        this.rear.next = node;
        // 让rear指向新的队尾元素
        this.rear = node;
    }

    /**
     * 出队操作
     */
    public void poll(){
        if(empty()){
            return;
        }

        this.front.next = this.front.next.next;
        // 如果删除队列的最后一个节点，需要把rear指向队头
        if(this.front.next == null){
            this.rear = this.front;
        }
    }

    /**
     * 返回队头元素
     * @return
     */
    public int peek(){
        return this.front.next.data;
    }

    /**
     * 判空
     * @return
     */
    public boolean empty(){
        return this.front == this.rear;
    }

    /**
     * 链表的节点类型
     */
    static class Entry{
        int data; // 数据域
        Entry next; // 地址域

        public Entry() {
            this(0, null);
        }

        public Entry(int data, Entry next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkQueue cq = new LinkQueue();
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
