package com.fixbug;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 跳跃表的代码实现   java.util.concurrent Java并发包   ConcurrentSkipListSet ConcurrentSkipListMap
 * @param <T>
 */
class SkipList<T extends Comparable<T>>{

    // 指向跳跃表最上面一层链表的头节点
    private HeadNode<T> head;

    /**
     * 跳跃表的初始化，初始只有一层链表
     */
    public SkipList() {
        this.head = new HeadNode<>(null, null, null, null, null, 1);
    }

    /**
     * 把key添加到跳跃表当中   [key, value]
     * @param key
     */
    public void put(T key){
        // 1.在跳跃表中搜索key应该插入的位置
        Node<T> pre = this.head;
        Node<T> cur = pre.right;
        for(;;){
            if(cur != null){
                if(cur.data.compareTo(key) < 0){
                    pre = cur;
                    cur = cur.right;
                    continue; // 继续判断当前链表的右边节点，找第一个比key大的节点
                } else if(cur.data.compareTo(key) == 0) {
                    return; // 不会重复插入key
                }
            }

            // 已经是第一层链表了，那么key应该插入到pre节点的后面
            if(pre.down == null){
                break;
            }

            pre = pre.down; // pre走到下面一层链表
            cur = pre.right; // 更新cur指向pre节点的下一个节点
        }

        // 2.key应该插入到pre节点的后面。抛硬币决定key需要插入的层数k
        int level = getLevel();
        HeadNode<T> h = this.head;
        if(level > h.level){ // key添加的层数太多了，最多只增加一层
            level = h.level + 1;
            // 生成新的一层链表，链接到跳跃表当中
            h = new HeadNode<>(null, null, this.head, null, null, level);
            this.head.up = h;
            this.head = h;
        }

        // 3.层数k <= 跳跃表的层数 : 直接插入   层数k > 跳跃表的层数 : 只增长一层
        Node<T>[] nodeArr = new Node[level];
        Node<T> index = null;

        // 先链接up域
        for(int i=0; i < nodeArr.length; ++i){
            nodeArr[i] = index = new Node<>(key, index, null, null, null);
        }

        // 再链接down域
        for(int i=0; i < nodeArr.length-1; ++i){
            nodeArr[i].down = nodeArr[i+1];
        }

        // 插入key
        for(int i = nodeArr.length-1; i >= 0; --i){
            // 下面四句代码，类似双向链表的插入操作
            nodeArr[i].right = pre.right;
            nodeArr[i].left = pre;
            pre.right = nodeArr[i];
            if(nodeArr[i].right != null){
                nodeArr[i].right.left = nodeArr[i];
            }

            if(0 == i){ // 所有层数的链表数据都已经添加完成
                break;
            }

            while(pre.up == null){ // pre往当前链表的前边节点回退，找见第一个可以往上面链表走的节点
                pre = pre.left;
            }

            pre = pre.up;
        }
    }

    /**
     * 跳跃表的删除操作
     * @param key
     */
    public void remove(T key){
        // 1. 找到待删除的节点，让cur指向
        Node<T> pre = this.head;
        Node<T> cur = pre.right;
        for(;;){
            if(cur != null){
                if(cur.data.compareTo(key) < 0){
                    pre = cur;
                    cur = cur.right;
                    continue;
                } else if(cur.data.compareTo(key) == 0){
                    break;
                }
            }

            if(pre.down == null){
                break;
            }

            pre = pre.down;
            cur = pre.right;
        }

        // 2.删除cur指向的节点
        if(cur == null){
            return;
        }

        // 3.开始删除cur指向的节点
        while (cur != null){
            cur.left.right = cur.right;
            if(cur.right != null){
                cur.right.left = cur.left;
            }
            cur = cur.down;
        }

        // 4.删除完成后，从上往下检查，把空链表全部释放
        Node<T> h = this.head;
        while (h != null && h.down != null){
            if(h.right == null){ // 表示当前链表是个空链表
                this.head = (HeadNode<T>) h.down;
                h = h.down;
            } else {
                break;
            }
        }
    }

    /**
     * 跳跃表的查询操作
     * @param key
     * @return
     */
    public boolean query(T key){
        Node<T> pre = this.head;
        Node<T> cur = pre.right;
        for(;;){
            if(cur != null){
                if(cur.data.compareTo(key) < 0){
                    pre = cur;
                    cur = cur.right;
                    continue;
                } else if(cur.data.compareTo(key) == 0){
                    return true;
                }
            }

            if(pre.down == null){
                break;
            }

            pre = pre.down;
            cur = pre.right;
        }

        return false;
    }

    /**
     * 打印每一层链表的内容
     */
    public void show(){
        System.out.println("跳跃表的层数:" + this.head.level);
        Node<T> h = this.head;
        while(h != null){
            Node<T> cur = h.right;
            while(cur != null){
                System.out.printf("%-5d", cur.data);
                cur = cur.right;
            }
            System.out.println();
            h = h.down;
        }
    }

    /**
     * 模拟抛硬币，决定key插入的层数
     * @return
     */
    private int getLevel() {
        int k = 1;
        while(Math.random() >= 0.5){
            k++;
        }
        return k;
    }

    /**
     * 跳跃表节点的类型
     * @param <T>
     */
    static class Node<T extends Comparable<T>>{
        T data;
        Node<T> up;
        Node<T> down;
        Node<T> left;
        Node<T> right;

        public Node(T data, Node<T> up, Node<T> down, Node<T> left, Node<T> right) {
            this.data = data;
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 每一层链表，头节点的类型，用来记录层数
     * @param <T>
     */
    static class HeadNode<T extends Comparable<T>> extends Node<T>{
        int level; // 跳跃表每一层链表的头节点记录当前链表所处的层数

        public HeadNode(T data, Node<T> up, Node<T> down, Node<T> left, Node<T> right, int level) {
            super(data, up, down, left, right);
            this.level = level;
        }
    }
}

/**
 * 跳跃表代码测试
 */
public class SkipListTest
{
    public static void main( String[] args ) {
        long begin, end;

//        begin = System.currentTimeMillis();
        SkipList<Integer> list = new SkipList<>();
        for (int i = 0; i < 10; i++) {
            list.put((int)(Math.random() * 100));
        }
        list.put(18);
        list.show();

        System.out.println(list.query(18));

        System.out.println();
        list.remove(18);
        list.show();
        System.out.println(list.query(18));

//        end = System.currentTimeMillis();
//        System.out.println("skiplist time:" + (end-begin) + "ms");

//        begin = System.currentTimeMillis();
//        ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
//        for (int i = 0; i < 20000; i++) {
//            set.add((int)(Math.random() * 100));
//        }
//        end = System.currentTimeMillis();
//        System.out.println("ConcurrentSkipListSet time:" + (end-begin) + "ms");
    }
}