package T02.stack;


/**
 * 描述: 链式栈
 * <p>
 * top(head) -> 23 -> 45 -> 67 -> 42 -> null
 * 看作栈顶元素
 * 入栈：单链表的头插法
 * 出栈：删除链表的第一个节点
 */
public class LinkStack {

    private Entry head;

    public LinkStack() {
        this.head = new Entry();
    }

    /**
     * 入栈
     */
    public void push(int val) {
        Entry entry = new Entry(val, this.head.next);
        this.head.next = entry;
    }

    public boolean isEmpty() {
        return this.head.next == null;
    }

    public int pop() {
        if (isEmpty()) {
            return -1;
        }
        Entry popEntry = this.head.next;
        this.head.next = this.head.next.next;
        return popEntry.data;
    }

    public int peek() {
        if (isEmpty()) {
            return -1;
        }
        return this.head.next.data;
    }


    /**
     * 链表的节点类型
     */
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
