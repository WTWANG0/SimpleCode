package T02.link;

/**
 * 描述:
 * 约瑟夫环是一个数学的应用问题：已知n个人（以编号1，2，3...n分别表示）围坐在一张圆桌周围，
 * 从编号为k的人开始报数，数到m的那个人出列，它的下一个人又从1开始报数，数到m的那个人又出列，
 * 依此规律重复下去，直到圆桌周围的人全部出列，输出人的出列顺序。
 * <p>
 * <p>
 * 分 带头节点的单向循环链表解决约瑟夫环的问题
 * 不带头节点的单向循环链表解决约瑟夫环的问题
 */
public class T04_ {
}


/**
 * entry节点
 */
class Entry {
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

//带头节点的单向循环链表解决约瑟夫环的问题
class Josephus_01 {
    private Entry head;

    public Josephus_01() {
        this.head = new Entry();
    }

    public void insertTail(int val) {
        Entry entry = this.head;
        while (entry.next != null) {
            entry = entry.next;
        }
        entry.next = new Entry(val, this.head);
    }

    /**
     * 打印约瑟夫环出列的顺序
     *
     * @param k 从第k个人开始报数
     * @param m 每次都是报m个数结束的人出列
     *          head-> 1,2,3,4,5,6,7,8,9,10
     */
    public void show(int k, int m) {
        Entry pre = this.head;
        Entry cur = this.head.next; //第一个有效的节点

        while (k-- > 1) {
            pre = cur;
            cur = cur.next;
        } //找到第K个人

        for (; ; ) {
            if (this.head == this.head.next) {
                return;
            }

            if (cur == this.head) { //过滤头节点
                pre = cur;
                cur = cur.next;
            }

            for (int i = 0; i < m; i++) {
                pre = cur;
                cur = cur.next;
                if (cur == this.head) { //过滤头节点
                    pre = cur;
                    cur = cur.next;
                }
            }

            System.out.println("exit ----" + cur.data);
            pre.next = cur.next;
            cur = cur.next;
        }

    }


}

//不带头节点的单向循环链表解决约瑟夫环的问题
class Josephus_02 {
    private Entry first;

    public Josephus_02() {
        this.first = null;//TODO: 从此不再是没有实际作用的head节点
    }

    public void insertTail(int val) {
        if (this.first == null) {
            this.first = new Entry(val, null);
            this.first.next = this.first;
            return;
        }

        Entry entry = this.first;
        while (entry.next != this.first) {
            entry = entry.next;
        }

        entry.next = new Entry(val, this.first);
    }

    /**
     * 打印约瑟夫环出列的顺序
     *
     * @param k 从第k个人开始报数
     * @param m 每次都是报m个数结束的人出列
     *          head-> 1,2,3,4,5,6,7,8,9,10
     */
    public void show(int k, int m) {
        Entry pre = this.first;
        Entry cur = this.first;

        while (k-- > 1) {
            pre = cur;
            cur = cur.next;
        }

        for (; ; ) {
            if (pre == cur) {
                return;
            }

            for (int i = 0; i < m; i++) {
                pre = cur;
                cur = cur.next;
            }
            System.out.println("exit ----" + cur.data);
            pre.next = cur.next;
            cur = cur.next;

        }


    }


}