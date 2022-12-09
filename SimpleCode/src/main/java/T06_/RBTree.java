package T06_;

import com.sun.org.apache.xpath.internal.objects.XNodeSet;

/**
 * 节点颜色非黑即红
 * 根节点是黑色节点
 * 每个节点到叶子节点黑色节点数量相同
 * 红色节点不能连续
 * 空节点默认为黑色节点
 */
public class RBTree<T extends Comparable<T>> {
    private Entry<T> root;//根节点

    /**
     * insert
     */
    public void insert(T val) {
        if (root == null) {
            this.root = new Entry<>(val, Color.BLACK);
            return;
        }
        Entry<T> parent = null;
        Entry<T> cur = this.root;
        while (cur != null) {
            parent = cur;
            if (cur.data.compareTo(val) > 0) {
                cur = cur.left;
            } else if (cur.data.compareTo(val) < 0) {
                cur = cur.left;
            } else {
                return;
            }
        }

        Entry<T> entry = new Entry<>(val, Color.RED);
        entry.parent = parent;
        if (parent.data.compareTo(val) > 0) {
            parent.left = entry;
        } else {
            parent.right = entry;
        }

        if (parent.color == Color.RED) {
            fixAfterInsert(entry);
        }
    }

    /**
     * entry.color = RED
     */
    public void fixAfterInsert(Entry<T> entry) {
        while (color(parent(entry)) == Color.RED) { //entry.parent.color = RED
            //左子树出现2个连续红色的节点
            if (left(parent(parent(entry))) == parent(entry)) {
                //左子树不均衡
                Entry<T> uncle = right(parent(parent(entry))); //#3 ： 父节点同级另外一个节点--uncle节点
                if (color(uncle) == Color.RED) { //uncle节点颜色是红色，那么祖先节点必然是黑色
                    //将父节点和uncle节点设置成黑色，
                    setColor(parent(entry), Color.BLACK);
                    setColor(uncle, Color.BLACK);

                    setColor(parent(parent(entry)), Color.RED); //祖先的孩子节点由RED--> BLACK ,祖先节点由BLACK -> RED 局部平衡

                    entry = parent(parent(entry));//entry向上只想祖先节点，
                } else {  //uncle节点颜色是黑色，那么祖先节点必然是黑色if
                    if (right(parent(entry)) == entry) { //#2：先调整顺序
                        entry = parent(entry);
                        leftRotate(entry);
                    }

                    //#1：左子树的左节点出现连续红色，uncle节点黑色
                    setColor(parent(entry), Color.BLACK);
                    setColor(parent(parent(entry)), Color.RED);

                    rightRotate(parent(parent(entry)));
                    break;
                }

            } else { //右子树出现2个连续红色的节点
                Entry<T> uncle = left(parent(parent(entry)));
                if (color(uncle) == Color.RED) {
                    setColor(parent(entry), Color.BLACK);
                    setColor(uncle, Color.BLACK);

                    setColor(parent(parent(entry)), Color.RED);
                    entry = parent(parent(entry));
                } else { //uncle is black
                    if (left(parent(entry)) == entry) {
                        entry = parent(entry);
                        rightRotate(entry);
                    }

                    setColor(parent(entry), Color.BLACK);
                    setColor(parent(parent(entry)), Color.RED);
                    leftRotate(parent(entry));
                    break;
                }
            }
        }

        setColor(this.root, Color.BLACK);
    }

    public void remove(T val) {
        if (this.root == null) {
            return;
        }

        Entry<T> cur = this.root;
        while (cur != null) {
            if (cur.data.compareTo(val) > 0) {
                cur = cur.left;
            } else if (cur.data.compareTo(val) < 0) {
                cur = cur.right;
            } else { //FIND IT
                break;
            }
        }

        if (cur == null) {
            return;
        }

        if (cur.left != null && cur.right != null) {
            Entry<T> parent = cur;
            cur = cur.left;
            while (cur.right != null) {
                cur = cur.right;
            }
            parent.data = cur.data;
        }

        Entry<T> child = cur.left;
        if (child == null) {
            child = cur.right;
        }

        if (child != null) { //分 left right
            child.parent = cur.parent;
            if (child.parent == null) {
                this.root = child;
            } else {
                if (cur.parent.left == cur) { //left
                    cur.parent.left = child;
                } else { //right
                    cur.parent.right = child;
                }

                if (cur.color == Color.BLACK) { //删除红色节点无所谓，删除黑色节点会影响整体规则
                    fixAfterDelete(cur);
                }
            }
        } else { //child == null
            if (cur.parent == null) {
                this.root = null;
            } else {
                if (cur.color == Color.BLACK) {
                    fixAfterDelete(cur);
                }

                //parent
                if (cur.parent.left == cur) {
                    cur.parent.left = null;
                } else {
                    cur.parent.right = null;
                }

            }

        }

    }

    /**
     * todo：先别删cur，cur是黑色，现在只能从兄弟借调一个黑色过来了
     * 删除一个黑色节点处理
     * 1. 删除的黑色节点补充上来的孩子是红色，直接把红色孩子涂成黑色就完成了
     * 2. 自己这一路没办法补充黑色节点，而且兄弟那一路也没有办法补充黑色节点，只能把兄弟涂成红色，从父节点继续向上
     * 回溯，如果遇到红色节点，直接涂黑，调整完成
     */
    private void fixAfterDelete(Entry<T> entry) {
        while (color(entry) == Color.BLACK) {
            if (left(parent(entry)) == entry) {//删除的节点在左边
                Entry<T> sib = right(parent(entry));//去除兄弟节点
                if (color(sib) == Color.RED) { //TODO:
                    setColor(parent(entry), Color.RED);
                    setColor(sib, Color.BLACK);
                    leftRotate(parent(entry));
                    sib = right(parent(entry));//todo"
                }

                //sib --black
                if (color(left(sib)) == Color.BLACK && color(right(sib)) == Color.BLACK) {
                    setColor(sib, Color.RED); //整体减少一个黑色节点
                    entry = parent(entry);//TODO
                }else {
                    if(color(right(sib)) != Color.RED){

                    }


                }


            } else {
                //TODO:

            }


        }

    }


    private void rightRotate(Entry<T> entry) {
        Entry<T> child = entry.left;
        //parent
        child.parent = entry.parent;
        if (entry.parent == null) {
            this.root = child;
        } else {
            if (left(parent(entry)) == entry) {
                parent(entry).left = child;
            } else {
                parent(entry).right = child;
            }
        }

        //child
        entry.left = child.right;
        if (child.right != null) {
            child.right.parent = entry;
        }

        child.right = entry;
        entry.parent = child;
    }

    /**
     * 右子树不平衡
     */
    private void leftRotate(Entry<T> entry) {
        Entry<T> child = entry.right;
        //1:parent
        child.parent = entry.parent;
        if (entry.parent == null) {
            this.root = child;
        } else {
            if (left(parent(entry)) == entry) {
                parent(entry).left = child;
            } else {
                parent(entry).right = child;
            }
        }

        //2:child
        child.left = entry.right;
        if (child.left != null) {
            child.left.parent = entry;
        }

        //entry
        child.left = entry;
        entry.parent = child;
    }


    private Entry<T> parent(Entry<T> entry) {
        return entry.parent;
    }

    private Entry<T> left(Entry<T> entry) {
        return entry.left;
    }

    private Entry<T> right(Entry<T> entry) {
        return entry.right;
    }

    private Color color(Entry<T> entry) {
        return entry == null ? Color.BLACK : entry.color;
    }

    private void setColor(Entry<T> entry, Color color) {
        entry.color = color;
    }

    /**
     * 节点
     */
    static class Entry<T extends Comparable<T>> {
        private T data;
        private Entry<T> left;
        private Entry<T> right;
        private Entry<T> parent;
        private Color color;

        public Entry(T data, Color color) {
            this(data, null, null, null, color);
        }

        public Entry(T data, Entry<T> left, Entry<T> right, Entry<T> parent, Color color) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = color;
        }
    }

    /**
     * 颜色
     */
    static enum Color {
        BLACK, RED
    }
}
