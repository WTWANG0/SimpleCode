package T02.sq;

import T02.queue.LinkQueue;

public class 两个队列实现一个栈 {
    private LinkQueue queue1;
    private LinkQueue queue2;

    {
        queue1 = new LinkQueue();
        queue2 = new LinkQueue();
    }

    public void push(int val) {
        if (!queue1.isEmpty()) {
            queue1.offer(val);
        } else if (!queue2.isEmpty()) {
            queue2.offer(val);
        } else {
            queue1.offer(val);
        }
    }

    public boolean isEmpty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }

    public int pop() {
        if (isEmpty()) {
            return -1;
        }

        LinkQueue srcQue = this.queue1.isEmpty() ? this.queue2 : this.queue1; //源目标：有数据
        LinkQueue destQue = this.queue1.isEmpty() ? this.queue1 : this.queue2;

        int data = 0;

        while (!srcQue.isEmpty()) {
            data = srcQue.peek();
            srcQue.poll();
            if (srcQue.isEmpty()) {
                break;
            }
            destQue.offer(data);
        }
        return data;
    }


    public static void main(String[] args) {
        两个队列实现一个栈 s = new 两个队列实现一个栈();
        for (int i = 0; i < 20; i++) {
            s.push(i+1);
        }
        System.out.println("-----------------------");
        while(!s.isEmpty()){
            int data = s.pop();
            System.out.print(data + " ");
        }
    }

}
