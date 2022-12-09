package T03_;

import javafx.scene.chart.ValueAxis;
import org.junit.Test;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.font.TextHitInfo;
import java.util.LinkedList;

public class BSTree<T extends Comparable<T>> {

    Entry<T> root;//根节点

    public BSTree() {
        this.root = null;
    }


    //region non opr
    public void non_insert(T val) {
        if (this.root == null) { //tree is null
            this.root = new Entry<>(val, null, null);
            return;
        }

        //tree is not null
        Entry<T> parent = null;
        Entry<T> cur = this.root;
        while (cur != null) {
            parent = cur;
            if (cur.data.compareTo(val) > 0) {
                cur = cur.left;
            } else if (cur.data.compareTo(val) < 0) {
                cur = cur.right;
            } else {
                return;//can't insert the same entry
            }
        }

        if (parent.data.compareTo(val) > 0) {
            parent.left = new Entry<>(val, null, null);
        } else {
            parent.right = new Entry<>(val, null, null);
        }
    }


    public boolean nom_query(T val) {
        if (this.root == null) {
            return false;
        }

        Entry<T> cur = this.root;
        if (cur.data.compareTo(val) > 0) {
            cur = cur.left;
        } else if (cur.data.compareTo(val) < 0) {
            cur = cur.right;
        } else {
            return true;
        }
        return false;
    }

    public void non_remove(T val) {
        if (this.root == null) {
            return;
        }

        Entry<T> parent = null;
        Entry<T> cur = this.root;

        while (cur != null) {
            parent = cur;
            if (cur.data.compareTo(val) > 0) {
                cur = cur.left;
            } else if (cur.data.compareTo(val) < 0) {
                cur = cur.right;
            } else {
                break;
            }
        }

        if (cur == null) {
            return;
        }


        if (cur.left != null && cur.right != null) {
            Entry<T> old = cur; //固定要删除的节点
            parent = cur;
            while (cur.left != null) {
                parent = cur;
                cur = cur.left;
            }
            old.data = cur.data;//将前驱节点值赋值给被删除的节点，接下来就是处理被删除的节点
        }

        //找到一个可用的节点，前驱节点删左节点，可能还有右节点，后缀节点相反
        Entry<T> child = cur.left;
        if (child == null) {
            child = cur.right;
        }

        if (parent == null) {
            this.root = child;
        } else if (parent.left == cur) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }

    //endregion

    //region
    public Entry<T> insert(Entry<T> root, T val) {
        if (this.root == null) {
            return this.root = new Entry<>(val, null, null);
        }

        if (root.data.compareTo(val) > 0) {
            return insert(root.left, val);
        } else if (root.data.compareTo(val) < 0) {
            return insert(this.root, val);
        } else {
            return null;
        }
    }

    public boolean query(T val) {
        return query(this.root, val) != null;
    }

    public Entry<T> query(Entry<T> root, T val) {
        if (this.root == null) {
            return null;
        }

        if (this.root.data.compareTo(val) > 0) {
            return query(this.root.left, val);
        } else if (this.root.data.compareTo(val) < 0) {
            return query(this.root.right, val);
        } else {
            return this.root;
        }
    }

    public void remove(T val) {
        this.root = remove(this.root, val);
    }

    public Entry<T> remove(Entry<T> root, T val) {
        if (this.root == null) {
            return null;
        }

        if (this.root.data.compareTo(val) > 0) {
            this.root.left = remove(this.root.left, val);

        } else if (this.root.data.compareTo(val) < 0) {
            this.root.right = remove(this.root.right, val);
        } else { //FIND
            if (root.left != null && root.right != null) {
                Entry<T> pre = this.root;
                while (pre.left != null) {
                    pre = pre.left;
                }

                this.root.data = pre.data;
                root.left = remove(root.left, pre.data);
            } else if (root.left != null) {
                return root.left;
            } else if (root.right != null) {
                return root.right;
            } else {
                return null;
            }
        }
        return this.root;
    }


    //endregion

    //region

    /**
     * VLR
     */
    public void preOrder() {
        System.out.print("递归实现前序遍历：");
        preOrder(this.root);
        System.out.print("over");
    }

    public void preOrder(Entry<T> root) {
        if (root == null) {
            return;
        }

        System.out.println(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
     * LVR
     */
    public void inOrder() {
        System.out.print("递归实现中序遍历：");
        inOrder(this.root);
        System.out.print("over");
    }

    public void inOrder(Entry<T> root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);
        System.out.println(root.data + " ");
        inOrder(root.right);
    }


    /**
     * LRV
     */
    public void postOrder() {
        System.out.print("递归实现后序遍历：");
        postOrder(this.root);
        System.out.print("over");
    }

    public void postOrder(Entry<T> root) {
        if (root == null) {
            return;
        }

        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.data + " ");
    }

    public void levelOrder() {
        System.out.print("递归实现层序遍历：");
        int level = level(this.root);
        for (int i = 0; i < level; i++) {
            levelOrder(this.root, i);
        }

        System.out.print("over");
    }

    public void levelOrder(Entry<T> root, int i) {
        if (this == null) {
            return;
        }

        if (i == 0) {
            System.out.println(root.data + " ");
            return;
        }
        levelOrder(root.left, i + 1);
        levelOrder(root.right, i + 1);
    }

    //endregion


    public int level(Entry<T> root) {
        if (root == null) {
            return 0;
        } else {
            int left = level(this.root.left);
            int right = level(this.root.right);
            return Math.max(left, right) + 1;
        }
    }

    public int number(Entry<T> root) {
        if (root == null) {
            return 0;
        } else {
            return number(this.root.left) + number(this.root.right) + 1;
        }
    }


    //region
    public void mirror() {
        mirror(this.root);
    }

    public void mirror(Entry<T> root) {
        if (root == null) {
            return;
        }
        Entry<T> tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        mirror(root.left);
        mirror(root.right);
    }


    /**
     * 把BST树中在[v1, v2]区间的所有元素打印出来
     * LVR
     */
    public void printSectionDatas(T v1, T v2) {
        printSectionDatas(this.root, v1, v2);
    }

    public void printSectionDatas(Entry<T> root, T v1, T v2) {
        if (root.data == null) {
            return;
        }

        if (root.data.compareTo(v1) > 0) {
            printSectionDatas(root.left, v1, v2);
        }

        if (v1.compareTo(root.data) <= 0 && v2.compareTo(root.data) >= 0) {
            System.out.print(root.data + " ");
        }

        if (root.data.compareTo(v2) < 0) {
            printSectionDatas(root.right, v1, v2);
        }

    }


    /**
     * 判断是否是BSTree
     * left < father < right
     */
    public boolean isBSTree() {
        boolean ret = isBSTree(this.root);
        lastOrderVal = null;
        return ret;

    }

    T lastOrderVal = null;//记录中序遍历元素，上一个元素的值

    /**
     * LVR打印的顺序就是有序
     */
    public boolean isBSTree(Entry<T> root) {
        if (root == null) {
            return true;
        }

        if (!isBSTree(root.left)) {
            return false;
        }

        if (lastOrderVal != null && root.data.compareTo(lastOrderVal) < 0) {
            return false;
        }
        lastOrderVal = root.data;

        return isBSTree(root.right);
    }

    /**
     *
     */
    public boolean isChildTree(BSTree<T> tree) {
        if (tree.root == null) {
            return true;
        }
        Entry<T> cur = this.root;
        while (cur != null) {
            if (cur.data.compareTo(tree.root.data) > 0) {
                cur = cur.left;
            } else if (cur.data.compareTo(tree.root.data) < 0) {
                cur = cur.right;
            } else {
                break;
            }
        }

        if (cur == null) {
            return false;
        }

        return isChildTree(cur, tree.root);
    }

    public boolean isChildTree(Entry<T> father, Entry<T> child) {
        if (father == null && child == null) {
            return true;
        }

        if (father == null) {
            return false;
        }

        if (child == null) {
            return true;
        }

        if (father.data.compareTo(child.data) != 0) { //not equal
            return false;
        }

        return isChildTree(father.left, child.left) && isChildTree(father.right, child.right);
    }


    /**
     * 获取两个节点的最近公共祖先节点的值并返回
     */
    public T getLCA(T v1, T v2) {
        return getLCA(this.root, v1, v2);
    }


    /**
     * 从root指向的节点为起始节点开始，找一个节点，其值在v1和v2之间
     */
    public T getLCA(Entry<T> root, T v1, T v2) {
        if (root == null) {
            return null;
        }

        if (root.data.compareTo(v1) > 0 && root.data.compareTo(v2) > 0) {
            return getLCA(root.left, v1, v2);
        } else if (root.data.compareTo(v1) < 0 && root.data.compareTo(v2) < 0) {
            return getLCA(root.right, v1, v2);
        } else {
            return root.data;
        }

    }

    /**
     * 左右孩子高度差 <= 1
     */
    public boolean isBalance() {
        int level = 0;
        boolean[] ret = new boolean[1];
        ret[0] = true;
        isBalance(this.root, level, ret);
        return ret[0];
    }

    /**
     * 返回树高
     */
    public int isBalance(Entry<T> root, int level, boolean[] ret) {
        if (root == null) {
            return level;
        }

        int left = isBalance(root.left, level + 1, ret);
        if (!ret[0]) {
            return left;
        }

        int right = isBalance(root.right, level + 1, ret);
        if (!ret[0]) {
            return right;
        }

        if (Math.abs(left - right) > 1) {
            ret[0] = false;
        }
        return Math.max(left, right);
    }


    /**
     * 以root指向的节点为根节点，判断节点左右子树是否平衡
     */
    public boolean isBalance(Entry<T> root) {
        if (root == null) {
            return true;
        }

        int left = level(root.left);
        int right = level(root.right);
        if (Math.abs(left - right) > 1) {
            return false;
        }
        return isBalance(root.left) && isBalance(root.right);
    }


    /**
     * 返回中序遍历倒数第K个节点的值
     */
    public T getInOrderLastKVal(int k) {
        return getInOrderLastKVal(this.root, k);
    }

    /**
     * 从root指定的节点开始，进行反向的中序遍历，然后求其正数第K个节点
     * <p>
     * LVR  --> RVL
     */
    int index = 0;

    private T getInOrderLastKVal(Entry<T> root, int k) {
        if (root == null) {
            return null;
        }

        T ret1 = getInOrderLastKVal(root.right, k);
        if (ret1 != null) {
            return ret1;
        }

        if (++index == k) {
            return root.data;
        }

        return getInOrderLastKVal(root.left, k);
    }


    //endregion

    //region

    /**
     * 非递归实现前序遍历  VLR
     */
    public void non_preOrder() {
        if (this.root == null) {
            return;
        }

        LinkedList<Entry<T>> stack = new LinkedList<>();
        stack.push(this.root);

        while (!stack.isEmpty()) {

            Entry<T> top = stack.pop();
            System.out.println(top.data + " ");

            if (top.right != null) {
                stack.push(top.right);
            }

            if (top.left != null) {
                stack.push(top.left);
            }
        }
        System.out.println("  ");
    }


    /**
     * 非递归实现中序遍历   LVR
     */
    public void non_inOrder() {
        if (this.root == null) {
            return;
        }

        LinkedList<Entry<T>> stack = new LinkedList<>();
        Entry<T> cur = this.root;

        while (!stack.isEmpty() || cur != null) {
            if (cur != null) { //一直压栈左孩子
                stack.push(cur);
                cur = cur.left;
            } else {
                Entry<T> top = stack.pop();//左孩子到底了，开始V
                System.out.print(top.data + " ");

                cur = cur.right;
            }
        }
    }

    /**
     * 非递归实现后序遍历   LRV
     * LRV -- > VRL --> 取出来
     */
    public void non_postOrder() {
        if (this.root == null) {
            return;
        }

        LinkedList<Entry<T>> stack1 = new LinkedList<>();
        LinkedList<Entry<T>> stack2 = new LinkedList<>();//VRL存储
        stack1.push(this.root);

        while (!stack1.isEmpty()) {
            Entry<T> pop = stack1.pop();

            if (pop.left != null) {
                stack1.push(pop.left);
            }

            if (pop.right != null) {
                stack1.push(pop.right);
            }
            stack2.push(pop);
        }

        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().data + " ");
        }
        System.out.println();
    }

    /**
     * 非递归实现层序遍历
     */
    public void non_levelOrder() {
        if (this.root == null) {
            return;
        }

        LinkedList<Entry<T>> queue = new LinkedList<>();//TODO :QUEUE
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            Entry<T> poll = queue.poll();
            System.out.print(poll.data + " ");

            //每层，每个节点塞塞最多2个节点
            if (poll.left != null) {
                queue.offer(poll.left);
            }

            if (poll.right != null) {
                queue.offer(poll.right);
            }

        }


    }


    //endregion


    /**
     * Entry节点
     */
    static class Entry<T extends Comparable<T>> {
        T data;
        Entry<T> left;
        Entry<T> right;

        public Entry() {
            this(null, null, null);
        }

        public Entry(T data) {
            this(data, null, null);
        }

        public Entry(T data, Entry<T> left, Entry<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }


    public void rebuild(T[] pre, T[] in) {
        this.root = rebuild(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    private Entry<T> rebuild(T[] pre, int i, int j, T[] in, int m, int n) {
        if (i > j || m > n) {
            return null;
        }

        Entry<T> root = new Entry<>(pre[i]);
        for (int k = m; k < n; k++) { //拿着pre[i]在中序遍历中的找出根节点的位置
            if (pre[i].compareTo(in[k]) == 0) {
                root.left = rebuild(pre, i + 1, i + (k - m), in, m, k - 1);
                root.right = rebuild(pre, i + (k - m) + 1, j, in, k + 1, n);
                break;
            }
        }
        return root;
    }


    @Test
    public void test01() {
        int[] arr = {63, 23, 92, 12, 35, 79, 98, 18, 46, 82, 102};
        BSTree<Integer> bst = new BSTree<>();
        for (int v : arr) {
            bst.non_insert(v);
        }

        bst.preOrder();
       //bst.inOrder();
    }

}
