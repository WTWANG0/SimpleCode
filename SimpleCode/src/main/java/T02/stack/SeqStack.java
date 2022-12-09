package T02.stack;

/**
 * 顺序栈结构
 */
public class SeqStack {

    private int[] stack;
    public int topIndex;

    public SeqStack() {
        this(10);
    }

    public SeqStack(int topIndex) {
        this.stack = new int[topIndex];
        this.topIndex = 0;
    }

    public boolean idFull() {
        return this.topIndex == this.stack.length;
    }

    public boolean isEmpty() {
        return this.topIndex == 0;
    }

    /**
     * 查看栈顶元素
     */
    public int top() {
        return this.stack[this.topIndex - 1];
    }

    public void pop() {
        if (isEmpty()) {
            return;
        }
        this.topIndex--;
    }

    public void push(int val) {
        if(idFull()){
            return;
        }
        this.stack[this.topIndex++] = val;
    }


}
