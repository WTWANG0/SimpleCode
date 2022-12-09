package T04_;

class BSTree<T extends Comparable<T>> {

    Entry<T> root;//根结点

    //初始化
    public BSTree() {
        this.root = null;
    }

    /**
     * 非递归插入
     */
    public void non_insert(T val) {
        if (this.root == null) {
            this.root = new Entry<>(val, null, null);
            return;
        }

        Entry<T> parent = null;
        Entry<T> cur = this.root;
        while (cur != null) {
            parent = cur;
            if (cur.data.compareTo(val) > 0) {

            } else if (cur.data.compareTo(val) < 0) {

            }else {
                return;
            }
        }


    }


    static class Entry<T extends Comparable> {
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

}


public class Test04 {
}
