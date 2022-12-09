package T02.link;

public class T03_ {
}


/**
 * 双向链表
 * warm: entry.next.pre要预先判断(entry.next != null)
 */
class DoubleLink {
    private Entry head;

    public DoubleLink() {
        this.head = new Entry();
    }

    public void insertHead(int val) {
        Entry entry = new Entry(val, this.head.next, this.head);
        if (entry.next != null) {
            entry.next.pre = entry;
        }
        this.head.next = entry;
    }

    public void insertTail(int val) {
        //注意：不能用 this.head.next因为可能为null，那么就需要判断cur是否为null，处理更麻烦
        Entry cur = this.head;
        if (cur.next != null) {
            cur = cur.next;
        }

        Entry entry = new Entry(val, cur.next, cur);
        cur.next = entry;

    }

    public boolean remove(int val) {
        Entry cur = this.head.next;

        while (cur != null) {
            if (cur.data == val) {
                cur.pre.next = cur.next;
                if(cur.next != null){
                    cur.next.pre = cur.pre;
                }
                return true;
            } else {
                cur = cur.next;
            }

        }
        return false;
    }


    static class Entry {
        int data;
        Entry next;
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
