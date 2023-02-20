package com.fixbug.link;

/**
 * 双向链表的定义
 */
class DoubleLink{

    // 指向头节点的引用
    private Entry head;

    /**
     * 初始化，生成链表的头节点
     */
    public DoubleLink(){
        this.head = new Entry();
    }

    /**
     * 实现头插法  O(1)
     * @param val
     */
    public void insertHead(int val){
        Entry node = new Entry(val, this.head.next, this.head);
        if(node.next != null){
            node.next.pre = node;
        }
        head.next = node;
    }

    /**
     * 实现尾插法   O(n)，如果不计算找尾节点的花费，插入操作本身是O(1)
     * @param val
     */
    public void insertTail(int val){
        Entry last = this.head;
        // while循环找尾节点，让last指向
        while(last.next != null){   // O(n)
            last = last.next; // 不要做last++
        }
        last.next = new Entry(val, null, last);   // O(1)
    }

    /**
     * 删除值为val的节点   删除操作本身是O(1)   但是删除之前的查询操作O(n)
     * @param val
     */
    public void remove(int val){
        // cur指向链表的第一个节点
        Entry cur = this.head.next;

        // 遍历双向链表，找val节点并删除
        while(cur != null){
            if(cur.data == val){
                cur.pre.next = cur.next;
                if(cur.next != null){
                    cur.next.pre = cur.pre;
                }
                return;
            }
            cur = cur.next;
        }
    }

    static class Entry{
        // 数据域
        int data;
        // 保存后一个节点的地址
        Entry next;
        // 保存前一个节点的地址
        Entry pre;

        public Entry() {
            this(0, null, null);
        }

        public Entry(int data, Entry next, Entry pre) {
            this.data = data;
            this.next = next;
            this.pre = pre;
        }
    }
}

/**
 * 描述: 双向链表代码测试
 *
 * @Author shilei
 * @Date 2019/8/28
 */
public class 双向链表 {
    public static void main(String[] args) {
        DoubleLink dl = new DoubleLink();
        for (int i = 0; i < 10; i++) {
            //dl.insertHead(i+1);
            dl.insertTail(i+1);
        }

        dl.remove(5);

        System.out.println();
    }
}