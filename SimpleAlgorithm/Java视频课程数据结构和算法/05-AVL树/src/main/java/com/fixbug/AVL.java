package com.fixbug;

/**
 * 描述: AVL树代码实现
 *
 * @Author shilei
 */
public class AVL<T extends Comparable<T>> {
    // 指向根节点
    private Entry<T> root;

    /**
     * AVL数的插入操作  =>   BST数的插入
     * 递归实现插入操作的时候，首先从根节点开始，先进行递归，直到找到节点是null的位置
     * @param val
     */
    public void insert(T val){
        this.root = insert(this.root, val);
    }

    /**
     * 以root为起始节点，找合适的位置插入val，并把子树的根节点进行返回
     * @param root
     * @param val
     * @return
     */
    private Entry<T> insert(Entry<T> root, T val) {
        if(root == null){
            return new Entry<>(val, 1);
        }

        if(root.data.compareTo(val) > 0){
            root.left = insert(root.left, val);
            // 递归回溯的时候，执行这里的代码   #1
            if(height(root.left) - height(root.right) > 1){
                if(height(root.left.left) >= height(root.left.right)){
                    // 左孩子的左子树太高， 做一个右旋转操作
                    root = rightRotate(root);
                } else {
                    // 左孩子的右子树太高了，做一个左-右旋转，左平衡曹祖
                    root = leftBalance(root);
                }
            }
        } else if(root.data.compareTo(val) < 0){
            root.right = insert(root.right, val);
            // 递归回溯的时候，执行这里的代码   #2
            if(height(root.right) - height(root.left) > 1){
                if(height(root.right.right) >= height(root.right.left)){
                    // 右孩子的右子树太高，做一个左旋转操作
                    root = leftRotate(root);
                } else {
                    // 右孩子的左子树太高，右-左旋转   右平衡操作
                    root = rightBalance(root);
                }
            }
        } else {
            ;
        }
        // #3 递归回溯的时候，更新插入节点的树枝上所有节点的高度值
        root.high = maxHeight(root.left, root.right) + 1;

        return root;
    }

    /**
     * AVL树删除操作
     * @param val
     */
    public void remove(T val){
        this.root = remove(this.root, val);
    }

    /**
     * 从root节点为起始节点，找值为val的节点进行删除，删除完成后，返回新的子树的根节点
     * @param root
     * @param val
     * @return
     */
    private Entry<T> remove(Entry<T> root, T val) {
        if(root == null){
            return null;
        }

        if(root.data.compareTo(val) > 0){
            root.left = remove(root.left, val);
            // 递归开始回溯，检测节点是否失衡
            if(height(root.right) - height(root.left) > 1){
                if(height(root.right.right) >= height(root.right.left)){
                    // 右孩子的右子树太高了   左旋转操作
                    root = leftRotate(root);
                } else {
                    // 右孩子的左子树太高了   右-左旋转
                    root = rightBalance(root);
                }
            }
        } else if(root.data.compareTo(val) < 0){
            root.right = remove(root.right, val);
            // 递归开始回溯，检测节点是否失衡
            if(height(root.left) - height(root.right) > 1){
                if(height(root.left.left) >= height(root.left.right)){
                    // 左孩子的左子树太高   右旋转
                    root = rightRotate(root);
                } else {
                    // 左孩子的右子树太高   左-右旋转  左平衡
                    root = leftBalance(root);
                }
            }
        } else {
            if(root.left != null && root.right != null){
                /**
                 * 为了检查旋转的次数，这里选择左子树高，删除前驱；右子树高，删除后继
                 */
                if(height(root.left) > height(root.right)){ // 左子树高，删前驱
                    // 删除有两个孩子的节点，找前驱节点的值覆盖当前删除节点的值，然后直接删除前驱节点就可以了
                    Entry<T> pre = root.left;
                    while(pre.right != null){
                        pre = pre.right;
                    }
                    // pre已经指向前驱节点了
                    root.data = pre.data;
                    // 直接递归删除前驱节点
                    root.left = remove(root.left, pre.data);
                } else {  // 右子树高，删后继
                    // 删除有两个孩子的节点，找后继节点的值覆盖当前删除节点的值，然后直接删除后继节点就可以了
                    Entry<T> post = root.right;
                    while(post.left != null){
                        post = post.left;
                    }
                    // post已经指向后继节点了
                    root.data = post.data;
                    // 直接递归删除后继节点
                    root.right = remove(root.right, post.data);
                }
            } else {
                // 删除节点有一个孩子，或者没有孩子
                if(root.left != null){
                    return root.left;
                } else if(root.right != null){
                    return root.right;
                } else {
                    return null;
                }
            }
        }

        // #3 递归回溯的时候，更新删除节点的树枝上所有节点的高度值
        root.high = maxHeight(root.left, root.right) + 1;

        return root;
    }

    /**
     * 以node为根节点，进行左旋转操作(节点失衡由于右孩子的右子树太高引起的)，
     * 左旋转以后，返回树的新的根节点
     * @param node
     * @return
     */
    private Entry<T> leftRotate(Entry<T> node){
        // 左旋转操作代码
        Entry<T> child = node.right;
        node.right = child.left;
        child.left = node;
        // 节点高度值更新
        node.high = maxHeight(node.left, node.right) + 1;
        child.high = maxHeight(child.left, child.right) + 1;
        return child;
    }

    /**
     * 以node为根节点，进行右旋转操作(节点失衡由于左孩子的左子树太高引起的)，
     * 右转以后，返回树的新的根节点
     * @param node
     * @return
     */
    private Entry<T> rightRotate(Entry<T> node){
        Entry<T> child = node.left;
        node.left = child.right;
        child.right = node;
        // 节点高度值更新
        node.high = maxHeight(node.left, node.right) + 1;
        child.high = maxHeight(child.left, child.right) + 1;
        return child;
    }

    /**
     * 节点失衡，由于左孩子的右子树太高 ，做左-右旋转操作，也叫左平衡操作
     * @param node
     * @return
     */
    public Entry<T> leftBalance(Entry<T> node){
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    /**
     * 节点失衡，由于右孩子的左子树太高 ，做右-左旋转操作，也叫右平衡操作
     * @param node
     * @return
     */
    public Entry<T> rightBalance(Entry<T> node){
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    /**
     * 返回更大的高度值
     * @param left
     * @param right
     * @return
     */
    private int maxHeight(Entry<T> left, Entry<T> right) {
        return height(left) > height(right) ? height(left) : height(right);
    }

    /**
     * 返回节点高度值
     * @param node
     * @return
     */
    private int height(Entry<T> node) {
        return node == null ? 0 : node.high;
    }

    /**
     * AVL树的节点类型的定义
     * @param <T>
     */
    static class Entry<T extends Comparable<T>>{
        private T data;
        private Entry<T> left;
        private Entry<T> right;
        private int high; // 节点的高度值

        public Entry() {
            this(null, null, null, 1);
        }

        public Entry(T data, int high) {
            this(data, null, null, 1);
        }

        public Entry(T data, Entry<T> left, Entry<T> right, int high) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.high = high;
        }
    }
}