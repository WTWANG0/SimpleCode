package T02.queue;

import com.sun.org.apache.regexp.internal.RE;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 链式队列·实现
 */
public class LinkQueue {
    private Entry head;
    private Entry tail;


    public LinkQueue() {
        this.head = this.tail = new Entry();
    }


    public void offer(int val) {
        Entry entry = new Entry(val, null);
        this.tail.next = entry;
        this.tail = entry;
    }


    public boolean isEmpty() {
        return this.head == this.tail;
    }

    public void poll() {
        if (isEmpty()) {
            return;
        }
        this.head.next = this.head.next.next;
        if (this.head.next == null) {
            this.head = this.tail;
        }
    }

    public int peek() {
        if (isEmpty()) {
            return 0;
        }
        return this.head.next.data;
    }


    static class Entry {
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


}
