package T05_;

import com.sun.xml.internal.fastinfoset.util.ValueArrayResourceException;
import org.omg.CORBA.NO_IMPLEMENT;

import java.util.ArrayList;
import java.util.List;

public class AVL<T extends Comparable<T>> {
    private Entry<T> root;

    public void insert(T val) {
        this.root = insert(this.root, val);
    }

    public Entry<T> insert(Entry<T> root, T val) {
        if (root == null) {
            return new Entry<>(val, 1);
        }

        if (root.data.compareTo(val) > 0) {
            root.left = insert(root.left, val);
            //插入到左子树，可能导致左子树破坏规则
            if (height(root.left) - height(root.right) > 1) {
                //左子树的左子树高 > 左子树的右子树高
                if (height(root.left.left) > height(root.left.right)) {
                    root = rightRotate(root);
                } else {    //左子树的左子树高 < 左子树的右子树高
                    root = leftBalance(root);
                }
            }
        } else if (root.data.compareTo(val) < 0) {
            root.right = insert(root.right, val);
            //插入到右子树，可能导致右子树破坏规则
            if (height(root.right) - height(root.left) > 1) {

                if (height(root.right.right) > height(root.right.left)) {
                    root = leftRotate(root);
                } else {
                    root = rightBalance(root);
                }
            }


        } else {

        }

        //avl 一定要注意更新树高
        root.high = Math.max(root.left.high, root.right.high) + 1;

        return root;

    }


    public void remove(T val) {
        this.root = remove(this.root, val);
    }

    public Entry<T> remove(Entry<T> root, T val) {
        if (root == null) {
            return null;
        }

        if (root.data.compareTo(val) > 0) {
            root.left = remove(root.left, val);
            if (height(root.right) - height(root.left) > 1) {
                if(height(root.right.left) >= height(root.right.left)){
                    root = leftRotate(root);
                }else {
                    root = rightBalance(root);

                }
            }
        } else if (root.data.compareTo(val) < 0) {
            root.right = remove(root.right, val);
        } else {
            //find
            if (root.left != null && root.right != null) {
                if (height(root.left) > height(root.right)) {
                    Entry<T> pre = root.left;
                    while (pre.right != null) {
                        pre = pre.right;
                    }
                    root.data = pre.data;
                    root.left = remove(root.left, pre.data);
                } else {
                    Entry<T> pre = root.right;
                    while (pre.left != null) {
                        pre = pre.left;
                    }
                    root.data = pre.data;
                    root.left = remove(root.right, pre.data);
                }

            } else {
                if (root.left != null) {
                    return root.left;
                } else if (root.right != null) {
                    return root.right;
                } else {
                    return null;
                }
            }
        }
        return root;
    }


    public Entry<T> rightBalance(Entry<T> node) {
        root.right = rightRotate(root.right);
        return leftRotate(root);
    }


    public Entry<T> leftBalance(Entry<T> node) {
        root.left = leftRotate(root.left);
        return rightBalance(root);
    }


    /**
     * 右旋--> 该node的height(left) > height(right)
     * 把左子树顶上
     */
    public Entry<T> rightRotate(Entry<T> node) {
        Entry<T> child = node.left;
        node.left = child.right;//node的left child上位，那么node的left用child的right
        child.right = node;//child的right就是node

        //更新高度
        node.high = maxHeight(node.left, node.right) + 1;
        child.high = maxHeight(child.left, child.right) + 1;
        return child;
    }

    /**
     * 把右子树顶上
     */
    public Entry<T> leftRotate(Entry<T> node) {
        Entry<T> child = node.right;
        node.right = child.left;
        child.left = node;

        //更新高度
        node.high = maxHeight(node.left, node.right) + 1;
        child.high = maxHeight(child.left, child.right) + 1;
        return child;
    }


    private int maxHeight(Entry<T> left, Entry<T> right) {
        return height(left) > height(right) ? height(left) : height(right);
    }

    public int height(Entry<T> entry) {
        return entry == null ? 0 : entry.high;
    }

    /**
     * Entry节点
     */
    static class Entry<T extends Comparable> {
        private T data;
        private Entry<T> left;
        private Entry<T> right;
        private int high;

        public Entry() {
            this(null, null, null, 0);
        }

        public Entry(T data, int high) {
            this(data, null, null, high);
        }

        public Entry(T data, Entry<T> left, Entry<T> right, int high) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.high = high;
        }
    }
}
