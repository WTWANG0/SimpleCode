package T02.link;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.font.TextHitInfo;

public class T01_ {
    public static void main(String[] args) {

    }
}

/**
 * 单项循环链表
 */
class CircleLink {

    private Entry head;//头节点

    public CircleLink() {
        this.head = new Entry();
        this.head.setNext(this.head);
    }

    /**
     * 带头节点的头插法
     */
    public void insertHead(int val) {
        Entry entry = new Entry(val, null);
        entry.setNext(this.head.getNext());
        this.head.setNext(entry);
    }


    /**
     * 尾插法
     */
    public void insertTail(int val) {
        Entry tail = this.head.getNext();
        while (tail.getNext() != this.head) {
            tail = tail.getNext();
        }
        tail.setNext(new Entry(val, tail.getNext()));
    }

    /**
     * 删除一个
     */
    public void removeEntry(int val) {
        Entry pre = this.head;
        Entry cur = this.head.getNext();

        while (cur != this.head) {
            if (cur.getData() == val) {
                pre.setNext(cur.getNext());
                return;
            } else {
                pre = cur;
                cur = cur.getNext();
            }
        }
    }

    public void removeAll(int val) {
        Entry pre = this.head;
        Entry cur = this.head.getNext();

        while (cur != this.head) {
            if (cur.getData() == val) {
                pre.setNext(cur.getNext());
                cur = cur.getNext();
            } else {
                pre = cur;
                cur = cur.getNext();
            }
        }
    }

    public boolean query(int val) {
        Entry cur = this.head.getNext();
        while (cur != this.head) {
            if (cur.getData() == val) {
                return true;
            } else {
                cur = cur.getNext();
            }
        }
        return false;
    }

    public void show() {
        Entry cur = this.head.getNext();
        while (cur != this.head) {
            System.out.println("---" + cur.getData());
            cur = cur.getNext();
        }
    }

    public boolean isEmpty() {
        return this.head.getNext() == this.head;
    }

    /**
     * 节点数据
     */
    private static class Entry {
        private int data; //当前节点数据
        private Entry next;//下一个节点

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
