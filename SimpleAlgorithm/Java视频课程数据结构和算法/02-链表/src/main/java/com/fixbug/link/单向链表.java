package com.fixbug.link;

import org.junit.Test;

/**
 * 单链表实现 - 实现带头节点的单链表
 *
 * 为了提高单链表尾插法的时间复杂度 O(n) -> O(1)   last.setNext(new Entry(val, null))
 * 可以给下面的Link单链表再添加一个成员变量 Entry last用来指向单链表的末尾节点，
 * this.head = new Entry();
 * this.last = this.head;
 * 需要注意以下几点：
 * 1.如果头插法插入元素，那么需要更新last为第一个插入的节点，后面插入的节点就不用管了
 * 2.如果是尾插法插入元素，每一次在末尾插入新的节点，都要更新last
 * 3.删除末尾节点的话，需要更新last指向新的末尾节点
 * 4.当单链表中只有一个节点的话，删除以后，需要更新last指向head
 */
class Link{

    // 指向链表头节点的引用（指针，存储一个Entry节点对象的地址）
    Entry head;

    public Link() {
        // 生成链表的头节点
        this.head = new Entry();
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
        while(last.getNext() != null){  // O(n)
            last = last.getNext();
        }

        // last就指向了链表的最后一个节点，把new的新节点地址写入最后一个节点的地址域
        last.setNext(new Entry(val, null)); // O(1)
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

        while(cur != null){
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

        while(cur != null){
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

        while(cur != null){
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

        while(cur != null){
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
        return this.head.getNext() == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // cur指向链表的第一个节点
        Entry cur = this.head.getNext();

        while(cur != null){
            // 打印cur指向的当前节点的数据
            sb.append(cur.getData());
            sb.append(" ");
            // 让cur指向下一个节点
            cur = cur.getNext();
        }
        return sb.toString();
    }

    /**
     * 单链表逆置操作
     */
    public void reverse() {
        Entry next = null; // 指向cur的下一个节点
        Entry cur = this.head.next;

        // 清空头节点
        this.head.next = null;  // #1

        // 把原来链表中所有的节点，重新进行头插，插入this.head链表当中
        while(cur != null){
            next = cur.next; // #2

            cur.next = this.head.next; // #3
            this.head.next = cur;

            cur = next; // #4
        }
    }

    /**
     * 合并两个有序的单链表
     * @param link2
     */
    public void merge(Link link2) {
        // p用来遍历link1的所有节点
        Entry p = this.head.next;
        // q用来遍历link2的所有节点
        Entry q = link2.head.next;
        // last指向合并后的链表的末尾节点
        Entry last = this.head;

        while(p != null && q != null){
            if(p.data > q.data){
                // 把link2上的节点合并
                last.next = q;
                q = q.next;
                last = last.next;
            } else {
                // 把link1上的节点合并
                last.next = p;
                p = p.next;
                last = last.next;
            }
        }

        // 表示link1还有剩余节点
        if(p != null){
            last.next = p;
        }

        // 表示link2还有剩余节点
        if(q != null){
            last.next = q;
        }

        link2.head.next = null;
    }

    /**
     * 返回环的入口节点
     * @return
     */
    public int getLoopNode() {
        Entry slow = this.head.next; // 慢指针
        Entry fast = this.head.next; // 快指针

        while(slow != null && fast != null && fast.next != null){
            slow = slow.next; // 慢指针一次遍历一个节点
            fast = fast.next.next; // 快指针一次遍历两个节点
            if(slow == fast){

                Entry p = this.head.next; // p从链表的第一个节点开始
                Entry q = slow; // q从slow和fast相遇的节点开始

                for(;;){
                    p = p.next;
                    q = q.next;
                    if(p == q){
                        return p.data; // q和p都指向了环的入口节点
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 判断链表是否有环
     * @return
     */
    public boolean isLoop() {
        Entry slow = this.head.next; // 慢指针
        Entry fast = this.head.next; // 快指针

        while(slow != null && fast != null && fast.next != null){
            slow = slow.next; // 慢指针一次遍历一个节点
            fast = fast.next.next; // 快指针一次遍历两个节点
            if(slow == fast){
                // 表示有环存在
                return true;
            }
        }
        return false;
    }

    /**
     * 判断link2和当前链表是否相交
     * @param link2
     * @return
     */
    public boolean isCross(Link link2) {
        // 1.先求出两个链表的长度
        int size1 = 0;
        int size2 = 0;
        Entry cur = null;

        // 统计第一个链表的长度
        cur = this.head.next;
        while(cur != null){
            size1++;
            cur = cur.next;
        }

        // 统计第二个链表的长度
        cur = link2.head.next;
        while(cur != null){
            size2++;
            cur = cur.next;
        }

        Entry p = this.head.next;
        Entry q = link2.head.next;

        // 第一个链表长， p要先走size1 - size2个节点
        if(size1 > size2){
            int cnt = size1 - size2;
            while(cnt-- > 0){
                p = p.next;
            }
        } else if(size1 < size2){
            // 第二个链表长， q要先走size2 - size1个节点
            int cnt = size2 - size1;
            while(cnt-- > 0){
                q = q.next;
            }
        }

        while(p != null && q != null){
            if(p == q){ // 表示两个链表相交了
                return true;
            }
            p = p.next;
            q = q.next;
        }

        return false;
    }

    /**
     * 找单链表倒数第k个节点
     * @param k
     * @return
     */
    public int getLastOrder(int k) {
        Entry p = this.head.next; // p指向第一个节点
        Entry n = this.head;

        // 让n指向第k个节点
        while(k-- > 0){
            n = n.next;
            if(n == null){
                throw new IllegalArgumentException("invalid k:" + k);
            }
        }

        while(n.next != null){
            p = p.next;
            n = n.next;
        }

        // n已经指向了最后一个节点，此时p就落在倒数第k个节点上
        return p.data;
    }

    /**
     * 单链表的节点类型
     */
    static class Entry{
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
}

/**
 * 描述: 单向链表测试
 *
 * @Author shilei
 * @Date 2019/8/27
 */
public class 单向链表 {

    @Test
    public void test06(){
        Link link = new Link();
        for (int i = 0; i < 20; i++) {
            link.insertTail(i+1);
        }

        System.out.println(link);
        System.out.println(link.getLastOrder(21));
    }

    @Test
    public void test05(){
        Link link = new Link();
        Link.Entry node1 = new Link.Entry(32, null);
        link.head.setNext(node1);
        Link.Entry node2 = new Link.Entry(21, null);
        node1.setNext(node2);
        Link.Entry node3 = new Link.Entry(67, null);
        node2.setNext(node3);
        Link.Entry node4 = new Link.Entry(2, null);
        node3.setNext(node4);
        Link.Entry node5 = new Link.Entry(6, null);
        node4.setNext(node5);

        Link link2 = new Link();
        Link.Entry node6 = new Link.Entry(21, null);
        link2.head.setNext(node6);
        Link.Entry node7 = new Link.Entry(67, null);
        node6.setNext(node7);

        node7.setNext(node4);
        System.out.println(link.isCross(link2));
    }

    @Test
    public void test04(){
        Link link = new Link();
        Link.Entry node1 = new Link.Entry(34, null);
        link.head.setNext(node1);
        Link.Entry node2 = new Link.Entry(21, null);
        node1.setNext(node2);
        Link.Entry node3 = new Link.Entry(67, null);
        node2.setNext(node3);
        Link.Entry node4 = new Link.Entry(42, null);
        node3.setNext(node4);
        Link.Entry node5 = new Link.Entry(12, null);
        node4.setNext(node5);
        Link.Entry node6 = new Link.Entry(78, null);
        node5.setNext(node6);
        node6.setNext(node3);

        // 判断链表是否有环
        System.out.println(link.isLoop());
        int data = link.getLoopNode();
        System.out.println(data);

    }

    @Test
    public void test03(){

        int[] ar = {20,28,37,42,58,69};
        int[] br = {15,32,35,57};

        Link link1 = new Link();
        Link link2 = new Link();

        for(int v : ar){
            link1.insertTail(v);
        }

        for(int v : br){
            link2.insertTail(v);
        }

        System.out.println(link1);
        System.out.println(link2);

        // 1.把link2上的节点按序合并到link1上  2.把link2置成空链表 head.next = null
        link1.merge(link2); // 合并两个有序的单链表
        System.out.println(link1);
    }

    /**
     * 测试单链表逆置
     */
    @Test
    public void test02(){
        Link link = new Link();
        for (int i = 0; i < 10; i++) {
            link.insertTail(i+1);
        }
        System.out.println(link);

        link.reverse(); // 单链表的逆置方法
        System.out.println(link);
    }

    public void test01() {
        Link link = new Link();
        for (int i = 0; i < 10; i++) {
            //link.insertHead(i);
            link.insertTail(i);
        }

        link.insertTail(5);
        link.removeAll(5);
        link.show();
        System.out.println();

        System.out.println(link);
    }
}