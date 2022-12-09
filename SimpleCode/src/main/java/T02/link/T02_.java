package T02.link;


public class T02_ {
    public static void main(String[] args) {
        int a = 3;
        while (a-- > 0) {
            System.out.println("--------");
        }
    }
}

class Link {
    private Entry head;

    public Link() {
        this.head = new Entry();
    }

    public void insertHead(int val) {
        Entry entry = new Entry(val, null);
        entry.setNext(this.head.getNext());
        this.head.setNext(entry);
    }

    public void insertTail(int val) {
        Entry tail = this.head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        tail.setNext(new Entry(val, null));

    }

    private static class Entry {
        private int data;
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

    public void removeFirst(int val) {
        Entry pre = this.head;
        while (pre.getNext() != null) {
            if (pre.getNext().getData() == val) {
                pre.setNext(pre.getNext().getNext());
                return;
            } else {
                pre = pre.getNext();
            }
        }
    }

    public void removeAll(int val) {
        Entry pre = this.head;
        Entry cur = pre.getNext();
        while (cur != null) {
            if (cur.getData() == val) {
                pre.setNext(cur.getNext());
                cur = cur.getNext();
                pre.setNext(pre.getNext().getNext());
            } else {
                pre = cur;
                cur = cur.getNext();
            }
        }
    }

    public boolean query(int val) {
        Entry cur = this.head.getNext();

        while (cur != null) {
            if (cur.getData() == val) {
                return true;
            } else {
                cur = cur.getNext();
            }
        }
        return false;
    }

    public void reverse() {
        Entry cur = this.head.getNext();
        Entry next = null;

        this.head.setNext(null);

        while (cur != null) {
            next = cur.getNext(); //保存NEXT

            cur.setNext(this.head.getNext()); //CUR头插法，所以可以利用head.getNext得到当前cur.getNext
            this.head.setNext(cur);//head的next指向当前插入的cur

            cur = next; //cur --> next

        }
    }

    //合并有序链表
    public void merge(Link link2) {
        Entry entry1 = this.head.getNext();
        Entry entry2 = link2.head.getNext();

        Entry newEntry = this.head;//复用头部

        while (entry1 != null && entry2 != null) {
            if (entry1.getData() > entry2.getData()) {
                newEntry.setNext(new Entry(entry1.getData(), null));
                entry1 = entry1.getNext();
            } else {
                newEntry.setNext(new Entry(entry2.getData(), null));
                entry2 = entry2.getNext();
            }
            newEntry = newEntry.getNext();
        }


        while (entry1 != null) {
            newEntry.setNext(entry1);
        }

        while (entry2 != null) {
            newEntry.setNext(entry2);
        }

    }

    //判断是否
    public boolean isLoop(Link link2) {
        Entry entry1 = this.head.getNext();
        Entry entry2 = link2.head.getNext();

        int link1Size = 0;
        while (entry1 != null) {
            link1Size++;
            entry1 = entry1.getNext();
        }

        int link2Size = 0;
        while (entry2 != null) {
            link2Size++;
            entry2 = entry2.getNext();
        }

        int preSize = Math.abs(link1Size - link2Size);

        if (link1Size > link2Size) {
            while (preSize-- > 0) {
                entry1 = entry1.getNext();
            }
        } else {
            while (preSize-- > 0) {
                entry2 = entry2.getNext();
            }
        }

        while (entry1 != null && entry2 != null) {
            if (entry1.getData() == entry2.getData()) {
                return true;
            }
            entry1 = entry2.getNext();
            entry2 = entry2.getNext();
        }
        return false;
    }

    public int getLastOrder(int k) {
        Entry e1 = this.head;
        Entry e2 = this.head.next;

        while (k-- > 0) {
            e2 = e2.getNext();
            if (e2 == null) {
                throw new RuntimeException("error");
            }
        }

        while (e2.getNext() != null) {
            e2 = e2.getNext();
            e1 = e1.getNext();
        }
        return e1.getData();
    }

    public boolean isLoop() {
        Entry slow = this.head.getNext();
        Entry fast = this.head.getNext();
        while (slow != null && fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            if (slow.getData() == fast.getData()) {
                return true;
            }
        }
        return false;
    }

    //入口节点
    public int getLoopNode() {
        Entry slow = this.head.getNext();
        Entry fast = this.head.getNext();
        while (slow != null && fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            if (slow.getData() == fast.getData()) {

                Entry p = this.head.getNext();
                Entry q = slow;

                for (; ; ) {
                    p = p.getNext();
                    q = q.getNext();
                    if (p.getData() == q.getData()) {
                        return p.getData();
                    }
                }
            }
        }
        return -1;
    }


}