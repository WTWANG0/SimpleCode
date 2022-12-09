package T02.sq;


import java.util.LinkedList;

public class 两个栈实现一个队列 {
    private LinkedList<Integer> s1;
    private LinkedList<Integer> s2;

    {
        s1 = new LinkedList<>();
        s2 = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.s1.isEmpty() && this.s2.isEmpty();
    }

    public void offer(int val) {
        if (this.s2.isEmpty()) { //S2为空，S1直接丢入
            this.s1.offer(val);
        }
    }


    public int poo() {
        if (isEmpty()) {
            return -1;
        }

        //把S1全部丢进S2
        if (this.s2.isEmpty()) {
            while (!this.s1.isEmpty()) {
                this.s2.push(this.s1.pop());
            }
        }
        Integer pop = this.s2.pop();
        doExchange(); //drain S2 TO S1
        return pop;
    }

    public int peek() {
        if (isEmpty()) {
            return -1;
        }

        //把S1全部丢进S2
        if (this.s2.isEmpty()) {
            while (!this.s1.isEmpty()) {
                this.s2.push(this.s1.pop());
            }
        }

        int ret = this.s2.peek();
        doExchange();
        return ret;
    }

    /**
     * ensure s1 have all data
     * s2 empty
     */
    public void doExchange() {
        while (!this.s2.isEmpty()) {
            this.s1.offer(this.s2.pop());
        }
    }


}
