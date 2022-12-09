package T02.queue;

/**
 * 循环队列
 * linked 链表 栈 队列
 * 栈：push pop peek
 * 队列：offer poll peek
 */
public class CircleQueue {

    private int[] que;//存储队列的所有元素;

    private int headIndex;//队列头位置

    private int tailIndex;//队列尾位置

    /**
     * 初始化队列，默认为10
     */
    public CircleQueue() {
        this(10);
    }

    public CircleQueue(int size) {
        this.que = new int[size];
        this.headIndex = this.tailIndex = 0;
    }

    public boolean isFull() {
        return (this.tailIndex + 1) % this.que.length == this.headIndex;
    }

    public boolean isEmpty() {
        return this.headIndex == this.tailIndex;
    }


    public void offer(int val) {
        if (isFull()) {
            int[] tmp = new int[this.que.length * 2];
            int index = 0;

            for (int i = this.headIndex; i != this.tailIndex; i = (i + 1) % this.que.length) {
                tmp[index++] = this.que[i];
            }
            this.headIndex = 0;
            this.tailIndex = index;

            this.que = tmp;
        }

        this.que[this.tailIndex] = val;
        this.tailIndex = (this.tailIndex + 1) % this.que.length;
    }

    public void pool() {
        if (isEmpty()) {
            return;
        }
        this.headIndex = (this.headIndex + 1) % this.que.length;
    }

    public int peak() {
        if (isEmpty()) {
            return 0;
        }
        return this.que[this.headIndex];
    }

}
