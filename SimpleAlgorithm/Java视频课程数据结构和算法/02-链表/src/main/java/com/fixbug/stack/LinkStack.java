package com.fixbug.stack;

import java.util.LinkedList;

/**
 * 描述: 链式栈
 *
 * top(head) -> 23 -> 45 -> 67 -> 42 -> null
 *          看作栈顶元素
 *          入栈：单链表的头插法
 *          出栈：删除链表的第一个节点
 *
 * @Author shilei
 * @Date 2019/8/29
 */
public class LinkStack {

    // 链表的头节点，直接作为栈顶使用
    private Entry top;

    /**
     * 栈的初始化
     */
    public LinkStack(){
        this.top = new Entry();
    }

    /**
     * 入栈操作
     * @param val
     */
    public void push(int val){
        Entry node = new Entry(val, this.top.next);
        this.top.next = node;
    }

    /**
     * 出栈操作
     * @return
     */
    public int pop() throws Exception{
        if(empty()){
            throw new Exception("stack is empty!");
        }
        Entry first = this.top.next;
        this.top.next = first.next;
        return first.data;
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public int peek() throws Exception{
        if(empty()){
            throw new Exception("stack is empty!");
        }
        return this.top.next.data;
    }

    /**
     * 判断栈空
     * @return
     */
    public boolean empty(){
        return this.top.next == null;
    }

    /**
     * 链表的节点类型
     */
    static class Entry{
        int data;
        Entry next;

        public Entry() {
            this(0, null);
        }

        public Entry(int data, Entry next) {
            this.data = data;
            this.next = next;
        }
    }

    public static void main(String[] args) throws Exception{
        LinkStack stack = new LinkStack();
        for (int i = 0; i < 20; i++) {
            stack.push(i+1);
        }

        while(!stack.empty()){
            int val = stack.peek();
            System.out.print(val + " ");
            stack.pop();
        }
    }
}