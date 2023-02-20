package com.fixbug.stack;

import java.util.Arrays;

/**
 * 描述: 顺序栈结构的实现
 *
 * @Author shilei
 * @Date 2019/8/28
 */
public class SeqStack {
    // 定义一个数组用来存储顺序栈的元素
    private int[] stack;
    // top表示当前栈顶的位置
    private int top;

    /**
     * 栈的初始化操作，默认大小是10
     */
    public SeqStack() {
        this(10);
    }

    /**
     * 栈的初始化操作，用户自定义栈的初始大小
     * @param size
     */
    public SeqStack(int size){
        this.stack = new int[size];
        this.top = 0;
    }

    /**
     * 入栈操作
     * @param val
     */
    public void push(int val){
        if(full()){
            // 如果顺序栈满了，进行2被的扩容操作
            this.stack = Arrays.copyOf(this.stack, this.stack.length*2);
        }

        this.stack[this.top] = val;
        this.top++;
    }

    /**
     * 出栈操作
     */
    public void pop(){
        if(empty())
            return;
        this.top--;
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public int top(){
        return this.stack[this.top-1];
    }

    /**
     * 判断栈满
     * @return
     */
    public boolean full(){
        return this.top == this.stack.length;
    }

    /**
     * 判断栈空
     * @return
     */
    public boolean empty(){
        return this.top == 0;
    }

    public static void main(String[] args) {
        SeqStack stack = new SeqStack();
        for (int i = 0; i < 20; i++) {
            stack.push(i+1);
        }

        while(!stack.empty()){
            int val = stack.top();
            System.out.print(val + " ");
            stack.pop();
        }
    }
}