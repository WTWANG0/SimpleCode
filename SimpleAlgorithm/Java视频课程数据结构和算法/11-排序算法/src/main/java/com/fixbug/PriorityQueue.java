package com.fixbug;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 描述: 优先级队列实现，默认实现成大根堆
 *
 * @Author shilei
 */
public class PriorityQueue<T> {
    // 存放队列的元素
    private T[] queue;
    // 有效元素的个数
    private int size;
    // 自定义比较器对象
    private Comparator<T> comp;
    // 定义一个默认的队列初始大小
    private static final int DEFAULT_SIZE = 10;

    public PriorityQueue(Comparator<T> comp) {
        this.comp = comp;
        this.queue = (T[])new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * 入堆操作
     * @param data
     */
    public void offer(T data){
        if(size == queue.length){
            queue = Arrays.copyOf(queue, queue.length*2);
        }

        if(size == 0){
            // 如果堆原来是空的，直接把data放入堆顶位置，不用做入堆上浮调整
            queue[0] = data;
        } else {
            // 堆不为空，插入元素后要进行上浮调整
            siftUp(size, data);
        }

        size++;
    }

    /**
     * 出堆操作
     * @return
     */
    public T poll(){
        if(size == 0){
            return null;
        }

        --size;
        T result = queue[0]; // 出堆的元素值
        T data = queue[size]; // 堆的最后一个元素
        queue[size] = null;
        if(size > 0){
            // 堆删除堆顶元素后，还有元素，进行元素的下沉调整
            siftDown(0, data);
        }
        return result;
    }

    /**
     * 判断堆是否为空
     * @return
     */
    public boolean empty(){
        return this.size == 0;
    }

    /**
     * 堆的上浮调整操作
     * @param i
     * @param data
     */
    private void siftUp(int i, T data) {
        while(i > 0){
            int father = (i-1)/2; // 父节点的下标
            T value = queue[father]; // 父节点的值
            if(comp.compare(data, value) > 0){
                // 进行上浮调整
                queue[i] = value;
                i = father;
            } else {
                break;
            }
        }
        queue[i] = data;
    }

    /**
     * 堆的下沉调整操作
     * @param i
     * @param data
     */
    private void siftDown(int i, T data) {
        // 最多下沉到第一个非叶子节点就可以了
        while(i < size/2){  // size/2
            // child需要记录值最大的孩子的下标
            int child = 2*i + 1;
            if(child+1 < size &&
                comp.compare(queue[child+1], queue[child]) > 0){
                child = child + 1; // child记录更大的右孩子节点的下标
            }

            if(comp.compare(queue[child], data) > 0){
                // 孩子的值更大
                queue[i] = queue[child];
                i = child;
            } else {
                break;
            }
        }
        queue[i] = data;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> que = new PriorityQueue<Integer>((a,b)->{
            return a.compareTo(b);
        });

        for (int i = 0; i < 20; i++) {
            que.offer((int)(Math.random()*100));
        }

        while (!que.empty()){
            System.out.print(que.poll() + " ");
        }
    }
}
