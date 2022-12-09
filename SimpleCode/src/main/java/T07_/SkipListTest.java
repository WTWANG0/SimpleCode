package T07_;


class SkipList<T extends Comparable<T>> {
    //指向
    private HeadNode<T> head;

    public SkipList() {
        this.head = new HeadNode<>(null, null, null, null, null, 1);
    }

    /**
     * 把KEY添加到跳跃表中
     */
    public void put(T key) {
        Node<T> pre = this.head;
        Node<T> cur = pre.right;
        for (; ; ) {
            if (cur != null) {
                if (cur.data.compareTo(key) < 0) {
                    pre = cur;
                    cur = cur.right;
                    continue;//找到第一个大于key的节点的cur，注意：pre < key
                } else if (cur.data.compareTo(key) == 0) {
                    return;//重复元素
                }
            }

            //第一层，无法继续向下找了，跳出循环
            if (pre.down == null) {
                break;
            }

            pre = pre.down; //pre向下走一层
            cur = pre.right;//更新cur为当前pre的下一个节点
        }

        //1： head
        int level = getLevel();
        HeadNode<T> h = this.head;
        if (level > h.level) {
            level = h.level + 1; //最多比最高层+1

            //因为要增长一层，需要创建一个新的HeadNode,up是之前的head，level是新的level
            h = new HeadNode<>(null, null, this.head, null, null, level);
            this.head = h;
        }

        //2:
        Node<T>[] nodeArr = new Node[level]; //创建长度为level的Node数组
        Node<T> index = null;
        //4：先创建当前节点和下一个节点数据
        for (int i = 0; i < nodeArr.length; i++) {
            //此处用到的index是上一轮的index，所以可以确定up节点数据
            nodeArr[i] = index = new Node<>(key, index, null, null, null);
        }

        //3：将上一个节点和下一个节点连接在一起，nodeArr[i]的down节点就是nodeArr[i + 1]
        for (int i = 0; i < nodeArr.length - 1; i++) {
            nodeArr[i].down = nodeArr[i + 1];
        }


        //4：开始整体链接
        //注意这是nodeArr.length -1，也就是最底层，从下向上填充
        for (int i = nodeArr.length - 1; i >= 0; i--) {
            nodeArr[i].right = pre.right; //right
            nodeArr[i].left = pre;      //left

            pre.right = nodeArr[i];
            if (nodeArr[i].right != null) {
                nodeArr[i].right.left = nodeArr[i];
            }

            if (i == 0) { //最顶层
                break;
            }

            //5：pre往当前链表的前边节点回退，找见第一个可以往上面链表走的节点
            while (pre.up == null) { //pre.up == null left节点无法向上查找，需要找left节点，找到一个可以向上的节点
                pre = pre.left;
            }
            pre = pre.up;
        }


    }

    /**
     * delete
     */
    public void remove(T key) {
        Node<T> pre = this.head;
        Node<T> cur = pre.right;
        for (; ; ) {

            if (cur.data.compareTo(key) < 0) {
                pre = cur;
                cur = cur.right;
                continue;
            } else if (cur.data.compareTo(key) == 0) {
                break;//find it
            }

            //cur.data > key, pre.data < key
            if (pre.down == null) {
                break;
            }

            //向下走一层
            pre = pre.down;
            cur = pre.right;
        }

        if (cur == null) {
            return;
        }

        //将每一层cur.left 与 cur.right 关联
        while (cur != null) {
            cur.left.right = cur.right;
            if (cur.right != null) {
                cur.right.left = cur.left;
            }
            cur = cur.down;
        }

        //从上到下检查，
        Node<T> h = this.head;
        while (h != null && h.down != null) {
            if (h.right == null) { //右侧节点没数据
                this.head = (HeadNode<T>) h.down; //削减一层
                h = h.down;
            } else {
                break;
            }
        }
    }

    public boolean query(T key) {
        Node<T> pre = this.head;
        Node<T> cur = pre.right;

        for (; ; ) {
            if (cur != null) {
                if (cur.data.compareTo(key) < 0) {
                    pre = cur;
                    cur = cur.right;
                    continue;
                } else {
                    return true;
                }
            }

            if (pre.down == null) {
                return false;
            }

            pre = pre.down;
            cur = pre.right;
        }

    }


    public void show() {
        System.out.println("跳跃表的层数:" + this.head.level);
        Node<T> h = this.head; // h 不变，等当前层遍历结束，向下走一层
        while (h != null) {
            Node<T> cur = h.right; //第一次指向当前层计算数据
            while (cur != null) {
                System.out.printf("%-5d", cur.data);
                cur = cur.right;
            }
            System.out.println();
            h = h.down;
        }
    }


    /**
     * 获取层数
     */
    private int getLevel() {
        int k = 1;
        while (Math.random() >= 0.5) {
            k++;
        }
        return k;
    }

    /**
     * Node节点信息
     */
    static class Node<T extends Comparable<T>> {
        T data;
        Node<T> up;
        Node<T> down;
        Node<T> left;
        Node<T> right;

        public Node(T data, Node<T> up, Node<T> down, Node<T> left, Node<T> right) {
            this.data = data;
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 头结点信息，用于记录层数
     */
    static class HeadNode<T extends Comparable<T>> extends Node<T> {
        int level;

        public HeadNode(T data, Node<T> up, Node<T> down, Node<T> left, Node<T> right, int level) {
            super(data, up, down, left, right);
            this.level = level;
        }
    }

}


public class SkipListTest {
    public static void main(String[] args) {

    }
}
