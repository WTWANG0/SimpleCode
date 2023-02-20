package com.fixbug;

/**
 * 红黑树的实现
 *
 */
public class RBTree<T extends Comparable<T>> {
    // 指向红黑树的入口节点
    private Entry<T> root;

    /**
     * 红黑树的插入操作
     * @param val
     */
    public void insert(T val){
        // 性质3：root根节点是黑色
        if(this.root == null){
            this.root = new Entry<>(val, Color.BLACK);
            return;
        }

        // 搜索合适的位置插入val
        Entry<T> parent = null;
        Entry<T> cur = this.root;
        while(cur != null){
            parent = cur;
            if(cur.data.compareTo(val) > 0){
                cur = cur.left;
            } else if(cur.data.compareTo(val) < 0){
                cur = cur.right;
            } else {
                return;
            }
        }

        // 把新节点插入到parent的孩子域当中
        Entry<T> node = new Entry<>(val, Color.RED);
        node.parent = parent; // 更新插入节点的parent
        if(parent.data.compareTo(val) > 0){
            parent.left = node;
        } else {
            parent.right = node;
        }

        // 如果新插入的红色节点的父节点也是红色，破坏了红黑树性质4，需要进行插入调整
        if(parent.color == Color.RED){
            fixAfterInsert(node);
        }
    }

    /**
     * 红黑树的插入调整过程
     * @param node
     */
    private void fixAfterInsert(Entry<T> node) {
        while(color(parent(node)) == Color.RED){
            // 表示插入的节点在祖先节点的左侧
            if(left(parent(parent(node))) == parent(node)){
                Entry<T> uncle = right(parent(parent(node)));
                if(color(uncle) == Color.RED){ // #3 叔叔的颜色是红色
                    // 把父亲和叔叔都调整成黑色
                    setColor(parent(node), Color.BLACK);
                    setColor(uncle, Color.BLACK);
                    // 把祖先节点调整成红色
                    setColor(parent(parent(node)), Color.RED);
                    // node指向祖先节点，继续向上检查红黑树性质是否满足
                    node = parent(parent(node));
                } else {
                    // 处理 #2的第一部分，其第二部分直接合并到下面统一处理
                    if(right(parent(node)) == node){
                        node = parent(node);
                        leftRotate(node);
                    }

                    // 处理#1
                    setColor(parent(node), Color.BLACK);
                    setColor(parent(parent(node)), Color.RED);
                    rightRotate(parent(parent(node)));
                    break;
                }
            } else {
                // 表示插入的节点在祖先节点的右侧
                Entry<T> uncle = left(parent(parent(node)));
                if(color(uncle) == Color.RED){ // #3 叔叔的颜色是红色
                    // 把父亲和叔叔都调整成黑色
                    setColor(parent(node), Color.BLACK);
                    setColor(uncle, Color.BLACK);
                    // 把祖先节点调整成红色
                    setColor(parent(parent(node)), Color.RED);
                    // node指向祖先节点，继续向上检查红黑树性质是否满足
                    node = parent(parent(node));
                } else {
                    // 处理 #2的第一部分，其第二部分直接合并到下面统一处理
                    if(left(parent(node)) == node){
                        node = parent(node);
                        rightRotate(node);
                    }

                    // 处理#1
                    setColor(parent(node), Color.BLACK);
                    setColor(parent(parent(node)), Color.RED);
                    leftRotate(parent(parent(node)));
                    break;
                }
            }
        }

        // 红黑树的性质，保证根节点是黑色
        setColor(this.root, Color.BLACK);
    }

    /**
     * 红黑树的删除操作
     * @param val
     */
    public void remove(T val){
        if(this.root == null){
            return;
        }

        Entry<T> cur = this.root;
        while(cur != null){
            if(cur.data.compareTo(val) > 0){
                cur = cur.left;
            } else if(cur.data.compareTo(val) < 0){
                cur = cur.right;
            } else{
                break;
            }
        }

        if(cur == null){ // 没找到值为val的节点
            return;
        }

        // 处理有两个孩子的待删除节点
        if(cur.left != null && cur.right != null){
            Entry<T> old = cur;
            cur = cur.left;
            while(cur.right != null){
                cur = cur.right;
            }
            // cur就指向前驱节点了
            old.data = cur.data;
        }

        // 统一删除cur指向的节点
        Entry<T> child = cur.left;
        if(child == null){
            child = cur.right;
        }

        if(child != null){
            child.parent = cur.parent;
            if(cur.parent == null){ // 说明cur是root根节点
                this.root = child;
            } else {
                if(cur.parent.left == cur){
                    cur.parent.left = child;
                } else {
                    cur.parent.right = child;
                }
            }

            if(cur.color == Color.BLACK){
                fixAfterDelete(child);
            }
        } else {
            if(cur.parent == null){
                this.root = null;
            } else {
                // 删除的就是一个叶子节点了
                if(cur.color == Color.BLACK){
                    fixAfterDelete(cur); // 先别删cur，cur是黑色，现在只能从兄弟借调一个黑色过来了
                }

                if(cur.parent.left == cur){
                    cur.parent.left = null;
                } else {
                    cur.parent.right = null;
                }
            }
        }
    }

    /**
     * 红黑树的删除调整
     * @param node
     */
    private void fixAfterDelete(Entry<T> node) {
        while(color(node) == Color.BLACK){
            if(left(parent(node)) == node){ // 说明删除的节点在左边
                Entry<T> sib = right(parent(node));
                if(color(sib) == Color.RED){  // #4
                    setColor(parent(node), Color.RED);
                    setColor(sib, Color.BLACK);
                    leftRotate(parent(node));
                    // 更新兄弟节点，继续向下处理
                    sib = right(parent(node));
                }

                if(color(left(sib)) == Color.BLACK
                    && color(right(sib)) == Color.BLACK){ // #3
                    setColor(sib, Color.RED);
                    node = parent(node);
                } else {
                    // #2
                    if(color(right(sib)) != Color.RED){
                        // 兄弟的右孩子不是红色
                        setColor(sib, Color.RED);
                        setColor(left(sib), Color.BLACK);
                        rightRotate(sib);
                        sib = right(parent(node));
                    }

                    // #1
                    setColor(sib, color(parent(node)));
                    setColor(parent(node), Color.BLACK);
                    setColor(right(sib), Color.BLACK);
                    leftRotate(parent(node));
                    break;
                }

            } else { // 说明删除的节点在右边
                Entry<T> sib = left(parent(node));
                if(color(sib) == Color.RED){  // #4
                    setColor(parent(node), Color.RED);
                    setColor(sib, Color.BLACK);
                    rightRotate(parent(node));
                    // 更新兄弟节点，继续向下处理
                    sib = left(parent(node));
                }

                if(color(left(sib)) == Color.BLACK
                        && color(right(sib)) == Color.BLACK){ // #3
                    setColor(sib, Color.RED);
                    node = parent(node);
                } else {
                    // #2
                    if(color(left(sib)) != Color.RED){
                        // 兄弟的右孩子不是红色
                        setColor(sib, Color.RED);
                        setColor(right(sib), Color.BLACK);
                        leftRotate(sib);
                        sib = left(parent(node));
                    }

                    // #1
                    setColor(sib, color(parent(node)));
                    setColor(parent(node), Color.BLACK);
                    setColor(left(sib), Color.BLACK);
                    rightRotate(parent(node));
                    break;
                }
            }
        }

        /**
         * 1. 删除的黑色节点补充上来的孩子是红色，直接把红色孩子涂成黑色就完成了
         * 2. 自己这一路没办法补充黑色节点，而且兄弟那一路也没有办法补充黑色节点，只能把兄弟涂成红色，从父节点继续向上
         * 回溯，如果遇到红色节点，直接涂黑，调整完成
         */
        setColor(node, Color.BLACK);
    }

    /**
     * 左旋转操作
     * @param node
     */
    public void leftRotate(Entry<T> node){
        Entry<T> child = node.right;//先把要替child的拿出来，让右边顶上，安排左边

        child.parent = node.parent;//child填上断层

        if(node.parent == null){
            // 说明node就是根节点
            this.root = child;
        } else {
            if(node.parent.left == node){
                // 更新父节点的左孩子域
                node.parent.left = child;
            } else {
                // 更新父节点的右孩子域
                node.parent.right = child;
            }
        }

        //给拆出来的这个node找一个位置，顶替child节点的left，那么原来child.left子节点会冲突，but 这两个都得放在child的left，
        // child.left > node,--> node.right - child.left，小的放在上面
        node.right = child.left;
        if(child.left != null){
            child.left.parent = node;
        }

        child.left = node; //完成最终的替换
        node.parent = child;
    }

    /**
     * 右旋转操作
     * @param node
     */
    public void rightRotate(Entry<T> node){
        Entry<T> child = node.left; //让左边顶上，安排右边
        child.parent = node.parent;

        if(node.parent == null){
            this.root = child;
        } else {
            if(node.parent.left == node){
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }

        //原本child.right位置被顶了，but node > child.right ---->放在node左侧
        node.left = child.right;
        if(child.right != null){
            child.right.parent = node;
        }

        child.right = node;
        node.parent = child;
    }

    private Entry<T> parent(Entry<T> node){
        return node.parent;
    }

    private Entry<T> left(Entry<T> node){
        return node.left;
    }

    private Entry<T> right(Entry<T> node){
        return node.right;
    }

    private Color color(Entry<T> node){
        return node == null ? Color.BLACK : node.color;
    }

    private void setColor(Entry<T> node, Color color){
        node.color = color;
    }

    /**
     * 节点颜色定义
     */
    static enum Color{
        BLACK,RED
    }

    /**
     * 红黑树节点定义
     * @param <T>
     */
    static class Entry<T extends Comparable<T>>{
        private T data;
        private Entry<T> left;
        private Entry<T> right;
        private Entry<T> parent; // 指向当前节点的父节点
        private Color color;

        public Entry(T data, Color color) {
            this(data, null, null, null, color);
        }

        public Entry(T data, Entry<T> parent, Entry<T> left, Entry<T> right, Color color) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }
    }
}
