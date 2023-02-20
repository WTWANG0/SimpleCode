package com.fixbug.link;

/**
 * 单向循环链表的代码实现
 */
class CircleLink{
    public CircleLink() {
        // 生成链表的头节点
        this.head = new Entry();
        // 让头节点的next地址域存储自己的地址
        this.head.setNext(this.head);
    }

    /**
     * 单链表的头插法
     * @param val
     */
    public void insertHead(int val){
        // 生成值为val的新节点
        Entry node = new Entry(val, null);
        // 把新节点的地址域设置成原来链表中第一个节点的地址
        node.setNext(this.head.getNext());
        // 把新节点的地址写入头节点的地址域中
        this.head.setNext(node);
    }

    /**
     * 单链表的尾插法
     * @param val
     */
    public void insertTail(int val){
        // 先寻找尾节点 last
        Entry last = this.head;
        // 末尾节点的特征是，地址域存储的是头节点head的地址
        while(last.getNext() != this.head){  // O(n)
            last = last.getNext();
        }

        // last就指向了链表的最后一个节点，把new的新节点地址写入最后一个节点的地址域
        last.setNext(new Entry(val, this.head)); // O(1)
    }

    /**
     * 删除第一个值为val的节点
     * @param val
     */
    public void removeFirst(int val){
        // pre指向待删除节点的前驱节点
        Entry pre = this.head;
        // cur指向待删除节点
        Entry cur = this.head.getNext();

        while(cur != this.head){
            if(cur.getData() == val){
                // 把删除节点的后一个节点的地址(可能是null)，写入前一个节点的地址域中
                pre.setNext(cur.getNext());
                return;
            } else {
                pre = cur;
                cur = cur.getNext();
            }
        }
    }

    /**
     * 删除所有值为val的节点
     * @param val
     */
    public void removeAll(int val){
        // pre指向待删除节点的前驱节点
        Entry pre = this.head;
        // cur指向待删除节点
        Entry cur = this.head.getNext();

        while(cur != this.head){
            if(cur.getData() == val){
                // 把删除节点的后一个节点的地址(可能是null)，写入前一个节点的地址域中
                pre.setNext(cur.getNext());
                // 更新cur，继续寻找val节点进行删除
                cur = pre.getNext();
            } else {
                pre = cur;
                cur = cur.getNext();
            }
        }
    }

    /**
     * 查询val元素是否存在
     * @param val
     * @return
     */
    public boolean query(int val){
        // 让cur指向单链表的第一个节点
        Entry cur = this.head.getNext();

        while(cur != this.head){
            if(cur.getData() == val){
                return true; // 找到val节点
            }
            cur = cur.getNext(); // 禁止cur++
        }

        return false; // 没有找到val节点
    }


    /**
     * 单链表打印
     */
    public void show(){
        // cur指向链表的第一个节点
        Entry cur = this.head.getNext();

        while(cur != this.head){
            // 打印cur指向的当前节点的数据
            System.out.print(cur.getData() + " ");
            // 让cur指向下一个节点
            cur = cur.getNext();
        }
    }

    /**
     * 判断链表是否为空
     * @return
     */
    public boolean isEmpty(){
        return this.head.getNext() == this.head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // cur指向链表的第一个节点
        Entry cur = this.head.getNext();

        while(cur != this.head){
            // 打印cur指向的当前节点的数据
            sb.append(cur.getData());
            sb.append(" ");
            // 让cur指向下一个节点
            cur = cur.getNext();
        }
        return sb.toString();
    }

    /**
     * 单链表的节点类型
     */
    private static class Entry{
        // 存储数据
        private int data;
        // 存储下一个节点的地址
        private Entry next;

        public Entry() {
            this(0, null);
        }

        public Entry(int data, Entry next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }
    }

    // 指向链表头节点的引用（指针，存储一个Entry节点对象的地址）
    private Entry head;
}

/**
 * 描述: 单向循环链表测试
 *
 * @Author shilei
 * @Date 2019/8/27
 */
public class 单向循环链表 {
    public static void main(String[] args) {
        CircleLink cl = new CircleLink();
        for (int i = 0; i < 10; i++) {
            cl.insertHead(i);
        }

        for (int i = 8; i < 20; i++) {
            cl.insertTail(i);
        }

        cl.removeFirst(8); // 8
        cl.removeAll(9); //

        System.out.println(cl);
    }
}