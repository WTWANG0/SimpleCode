package com.fixbug;


import org.junit.Test;

import java.util.LinkedList;

/**
 * 实现BST树（二叉搜索树）
 * @param <T>
 */
class BSTree<T extends Comparable<T>>{
    // 指向树的根节点
    Entry<T> root;

    /**
     * BST树初始化
     */
    public BSTree() {
        this.root = null;
    }

    /**
     * BST树的非递归插入操作
     * @param val
     */
    public void non_insert(T val){
        // 插入树的根节点
        if(this.root == null){
            this.root = new Entry<>(val, null, null);
            return;
        }

        /**
         * 树不为空，那么从root节点开始寻找合适的位置进行插入
         */
        Entry<T> parent = null;
        Entry<T> cur = this.root;
        while(cur != null){
            parent = cur;
            if(cur.data.compareTo(val) > 0){
                cur = cur.left;
            } else if(cur.data.compareTo(val) < 0){
                cur = cur.right;
            } else {
                return; // 不插入重复的元素
            }
        }

        // cur == null
        // 把新插入的叶子节点写入父节点相应的地址域中
        if(parent.data.compareTo(val) > 0){
            parent.left = new Entry<>(val, null, null);
        } else {
            parent.right = new Entry<>(val, null, null);
        }
    }

    /**
     * 非递归实现BST树的查询操作
     * @param val
     * @return
     */
    public boolean non_query(T val){
        if(this.root == null){
            return false;
        }

        Entry<T> cur = this.root;
        while(cur != null){
            if(cur.data.compareTo(val) > 0){
                cur = cur.left;
            } else if(cur.data.compareTo(val) < 0){
                cur = cur.right;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 实现BST非递归的删除操作
     * @param val
     */
    public void non_remove(T val){
        if(this.root == null){
            return;
        }

        Entry<T> parent = null;
        Entry<T> cur = this.root;
        while(cur != null){
            if(cur.data.compareTo(val) > 0){
                parent = cur;
                cur = cur.left;
            } else if(cur.data.compareTo(val) < 0){
                parent = cur;
                cur = cur.right;
            } else {
                break;
            }
        }

        // 表示没有找到val节点
        if(cur == null){
            return;
        }

        // 找到了待删除节点，cur指向了
        // 先处理#3 有两个孩子的节点的删除情况
        if(cur.left != null && cur.right != null){
            // 找前驱节点代替删除节点元素的值，然后直接删除前驱节点就可以(让cur指向前驱节点)
            Entry<T> old = cur;
            parent = cur;
            cur = cur.left;
            while(cur.right != null){
                parent = cur;
                cur = cur.right;
            }

            // cur就指向了前驱节点
            old.data = cur.data;
        }

        // 统一删除cur指向的节点 #1  #2
        // 让child指向待删除节点不为空的孩子  child也可能直接为null
        Entry<T> child = cur.left;
        if(child == null){
            child = cur.right;
        }

        if(parent == null){ // 删除的是root根节点
            this.root = child;
        } else if(parent.left == cur){
            parent.left = child;
        } else{
            parent.right = child;
        }
    }

    /**
     * 递归实现BST插入操作
     * @param val
     */
    public void insert(T val){
        this.root = insert(this.root, val);
    }

    /**
     * 从root指向的节点开始，找合适的位置插入值为val的节点
     * @param root
     * @param val
     * @return
     */
    private Entry<T> insert(Entry<T> root, T val) {
        if(root == null){
            return new Entry<>(val, null, null);
        }

        if(root.data.compareTo(val) > 0){
            root.left = insert(root.left, val);
        } else if(root.data.compareTo(val) < 0){
            root.right = insert(root.right, val);
        } else {
            ;
        }
        return root;
    }

    /**
     * 递归实现BST树的查询操作
     * @param val
     * @return
     */
    public boolean query(T val){
        return query(this.root, val) != null;
    }

    /**
     * 从root指定的节点开始，找值为val的节点并且返回
     * @param root
     * @param val
     * @return
     */
    private Entry<T> query(Entry<T> root, T val) {
        if(root == null){
            return null;
        }

        if(root.data.compareTo(val) > 0){
            return query(root.left, val);
        } else if(root.data.compareTo(val) < 0){
            return query(root.right, val);
        } else { // root.data.compareTo(val) == 0
            return root;
        }
    }

    /**
     * 递归实现BST树的删除操作
     * @param val
     */
    public void remove(T val){
        this.root = remove(this.root, val);
    }

    /**
     * 以root节点为起始节点，找值为val的节点进行删除，删除完成后，把新的子树的根节点进行返回
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
        } else if(root.data.compareTo(val) < 0){
            root.right = remove(root.right, val);
        } else{
            // 找到值为val的节点了   #3
            if(root.left != null && root.right != null){
                // 找前驱节点进行值替换，然后删除前驱节点
                Entry<T> pre = root.left;
                while(pre.right != null){
                    pre = pre.right;
                }
                // pre就指向了前驱节点
                root.data = pre.data;
                // 然后递归删除前驱节点
                root.left = remove(root.left, pre.data);
            } else {
                if(root.left != null){ // 待删除节点root的左孩子不为空
                    return root.left;
                } else if(root.right != null){ // 待删除节点root的右孩子不为空
                    return root.right;
                } else { // 演删除的节点root是叶子节点
                    return null;
                }
            }
        }

        return root;
    }

    /**
     * 递归实现前序遍历   VLR
     * if    =>  递归结束的条件
     * else   =>  问题n和问题n-1之间的关系
     */
    public void preOrder(){
        System.out.print("递归实现前序遍历：");
        preOrder(this.root);
        System.out.println();
    }

    /**
     * 一定要搞清楚递归函数做什么事情
     * 以root指向的节点为起始节点进行前序遍历访问
     * @param root
     */
    private void preOrder(Entry<T> root) {
        if(root == null){
            return;
        }
        System.out.print(root.data + " ");
        preOrder(root.left);   // L
        preOrder(root.right);  // R
    }

    /**
     * 中序遍历实现 LVR
     */
    public void inOrder(){
        System.out.print("递归实现中序遍历：");
        inOrder(this.root);
        System.out.println();
    }

    /**
     * 以root指向的节点为起始节点，进行中序遍历
     * @param root
     */
    private void inOrder(Entry<T> root) {
        if(root == null){
            return;
        }

        inOrder(root.left); // L
        System.out.print(root.data + " "); // V
        inOrder(root.right); // R
    }

    /**
     * 后序遍历实现 LRV
     */
    public void postOrder(){
        System.out.print("递归实现后序遍历：");
        postOrder(this.root);
        System.out.println();
    }

    /**
     * 以root指向的节点为起始节点，进行后序遍历
     * @param root
     */
    private void postOrder(Entry<T> root) {
        if(root == null){
            return;
        }

        postOrder(root.left); // L
        postOrder(root.right); // R
        System.out.print(root.data + " "); // V
    }

    /**
     * BST树的层序遍历
     */
    public void levelOrder(){
        System.out.print("递归实现层序遍历：");
        int l = level();
        for (int i = 0; i < l; i++) {
            levelOrder(this.root, i);
        }
        System.out.println();
    }

    /**
     * 以root指定的节点为起始节点，进行深度遍历，遍历过程中，打印指定的i层的节点
     * @param root
     * @param i
     */
    private void levelOrder(Entry<T> root, int i) {
        if(root == null){
            return;
        }

        if(i == 0){
            System.out.print(root.data + " ");
            return;
        }

        levelOrder(root.left, i-1);
        levelOrder(root.right, i-1);
    }

    /**
     * 输出BST树节点的个数
     * @return
     */
    public int number(){
        return number(this.root);
    }

    /**
     * 以root指向的节点开始，计算树的节点的个数
     * @param node
     * @return
     */
    private int number(Entry<T> node) {
        if(node == null){
            return 0;
        } else {
            return number(node.left) + number(node.right) + 1;
        }
    }

    /**
     * 输出BST树的层数
     * @return
     */
    public int level(){
        return level(this.root);
    }

    /**
     * 以root指向的节点为起始节点，计算二叉树的层数
     * @param root
     * @return
     */
    private int level(Entry<T> root) {
        if(root == null){
            return 0;
        } else {
            int left = level(root.left);
            int right = level(root.right);
            return left > right ? left+1 : right+1;
        }
    }

    /**
     * BST树的镜像
     */
    public void mirror(){
        mirror(this.root);
    }

    /**
     * 以root为起始节点遍历二叉树的所有节点
     * @param root
     */
    private void mirror(Entry<T> root) {
        if(root == null){
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
     * 利用中序遍历  小 -》 大
     * @param v1
     * @param v2
     */
    public void printSectionDatas(T v1, T v2){
        printSectionDatas(this.root, v1, v2);
    }

    /**
     * 以root指向的节点为起始节点，进行树的中序遍历，找满足区间[v1, v2]的元素
     * @param root
     * @param v1
     * @param v2
     */
    private void printSectionDatas(Entry<T> root, T v1, T v2) {
        if(root == null){
            return;
        }

        if(root.data.compareTo(v1) > 0){
            printSectionDatas(root.left, v1, v2); // 递归访问当前节点的左孩子   root.data <= v1
        }

        // root.data
        if(v1.compareTo(root.data) <= 0
            && v2.compareTo(root.data) >= 0){
            System.out.print(root.data + " ");
        }

        if(root.data.compareTo(v2) < 0){
            printSectionDatas(root.right, v1, v2); // 递归访问当前节点的右孩子  root.data > v2
        }
    }

    /**
     * 判断一颗二叉树是不是BST树
     * BST ： 首先是二叉树     其次： left < father < right
     */
    public boolean isBSTree(){
        boolean ret = isBSTree(this.root);
        lastInOrderVal = null;
        return ret;
    }

    /**
     * 以root为起始节点，判断二叉树中的每一个节点和其不为空的左右孩子是否满足关系：left < father < right
     * 二叉树的中序遍历   是一个有序的序列   数据从小到大排序的这么一个性质
     * @param root
     * @return
     */
    T lastInOrderVal = null;  // 记录中序遍历元素，上一个元素的值
    private boolean isBSTree(Entry<T> root) {
        if(root == null){
            return true;
        }

        if(!isBSTree(root.left)) {// L  左子树判断已经不是BST树，就不用再往下判断了
            return false;
        }
        // 处理当前V节点   拿当前root节点和中序遍历的上一个节点进行树值比较
        if(lastInOrderVal != null && root.data.compareTo(lastInOrderVal) <= 0){
            return false;
        }
        lastInOrderVal = root.data; // 更新当前中序遍历，记录下当前中序的值

        return isBSTree(root.right);   // R

//        // 写不满足条件的代码判断
//        if(root.left != null && root.left.data.compareTo(root.data) >= 0){
//            return false;
//        }
//
//        if(root.right != null && root.right.data.compareTo(root.data) <= 0){
//            return false;
//        }
//
//        return isBSTree(root.left) && isBSTree(root.right);
    }

    /**
     * 判断参数传入的tree是不是当前BST树的一颗子树结构
     * @param tree
     * @return
     */
    public boolean isChildTree(BSTree<T> tree){
        // 先在当前的BST树种，查找tree的根节点是否存在
        if(tree.root == null){
            return true;
        }

        Entry<T> cur = this.root;
        while(cur != null){
            if(cur.data.compareTo(tree.root.data) > 0){
                cur = cur.left;
            } else if(cur.data.compareTo(tree.root.data) < 0){
                cur = cur.right;
            } else {
                break;
            }
        }

        // tree的根节点在当前BST树种找不到
        if(cur == null){
            return false;
        }

        return isChildTree(cur, tree.root);
    }

    /**
     * 比较father和child为起始节点的子树
     * @param father
     * @param child
     * @return
     */
    private boolean isChildTree(Entry<T> father, Entry<T> child) {
        if(father == null && child == null){ // 递归结束的条件#1
            return true;
        }

        if(father == null){ // 递归结束的条件#2
            return false;
        }

        if(child == null){ // 递归结束的条件#3
            return true;
        }

        // 判断错误的子树
        if(father.data.compareTo(child.data) != 0){
            return false;
        }

        return isChildTree(father.left, child.left) && isChildTree(father.right, child.right);
    }

    /**
     * 获取两个节点的最近公共祖先节点的值并返回
     * @param v1
     * @param v2
     * @return
     */
    public T getLCA(T v1, T v2){
        return getLCA(this.root, v1, v2);
}

    /**
     * 从root指向的节点为起始节点开始，找一个节点，其值在v1和v2之间
     * @param root
     * @param v1
     * @param v2
     * @return
     */
    private T getLCA(Entry<T> root, T v1, T v2) {
        if(root == null){
            return null;
        }

        if(root.data.compareTo(v1) > 0 && root.data.compareTo(v2) > 0){
            return getLCA(root.left, v1, v2);
        } else if(root.data.compareTo(v1) < 0 && root.data.compareTo(v2) < 0){
            return getLCA(root.right, v1, v2);
        } else {
            return root.data;  // root.data在v1和v2区间之间，所以root就是最近公共祖先节点
        }
    }

    /**
     * 判断当前二叉树是不是一颗平衡的树
     * 平衡树：任意节点的左右子树的高度差，不超过1
     * @return
     */
    public boolean isBalance(){
        //return isBalance(this.root);
        int level = 0;
        boolean[] result = new boolean[1];
        result[0] = true;
        isBalance(this.root, level, result);
        return result[0];
    }

    /**
     * 返回root层节点的高度level
     * @param root
     * @param level
     * @return
     */
    private int isBalance(Entry<T> root, int level, boolean[] result) {
        if(root == null){
            return level;
        }

        int left = isBalance(root.left, level+1, result);  // L
        if(!result[0]){
            return left;
        }
        int right = isBalance(root.right, level+1, result); // R
        if(!result[0]){
            return right;
        }
        if(Math.abs(left - right) > 1){
            result[0] = false;
        }

        return Math.max(left, right);
    }

    /**
     * 以root指向的节点为根节点，判断节点左右子树是否平衡
     * @param root
     * @return
     */
    private boolean isBalance(Entry<T> root) {
        if(root == null){
            return true;
        }

        int left = level(root.left);  // 计算了root节点左子树的高度
        int right = level(root.right); // 计算了root节点右子树的高度
        if(Math.abs(left - right) > 1){ // 节点的左右子树高度差超过1了，子树就不平衡了
            return false;
        }

        return isBalance(root.left) && isBalance(root.right);
    }

    /**
     * 返回中序遍历倒数第K个节点的值
     * @param k
     * @return
     */
    public T getInOrderLastKVal(int k){
        return getInOrderLastKVal(this.root, k);
    }

    /**
     * 从root指定的节点开始，进行反向的中序遍历，然后求其正数第K个节点
     * @param root
     * @param k
     * @return
     */
    int index = 0;
    private T getInOrderLastKVal(Entry<T> root, int k) {
        if(root == null){
            return null;
        }

        // 反向的中序遍历 RVL  相当于就是求整数第K个中序节点了
        T ret1 = getInOrderLastKVal(root.right, k); // R
        if(ret1 != null){
            return ret1;
        }

        // V
        if(++index == k){ // 表示找见反向中序的第K个节点了
            return root.data;
        }

        return getInOrderLastKVal(root.left, k); // L
    }

    /**
     * 重建二叉树
     * @param pre
     * @param in
     */
    public void rebuild(T[] pre, T[] in) {
        this.root = rebuild(pre, 0, pre.length-1, in, 0, in.length-1);
    }


    private Entry<T> rebuild(T[] pre, int i, int j, T[] in, int m, int n) {
        if(i > j || m > n){
            return null;
        }

        Entry<T> root = new Entry<>(pre[i]); // 以前序遍历数组的首元素，创建当前子树的根节点
        for(int k=m; k<=n; ++k){ // 拿着pre[i]在中序遍历中找根节点出现的位置
            if(pre[i].compareTo(in[k]) == 0){
                root.left = rebuild(pre, i+1, i+(k-m), in, m, k-1);
                root.right = rebuild(pre, i+(k-m)+1, j, in, k+1, n);
                break;
            }
        }
        return root;
    }

    /**
     * 非递归实现前序遍历  VLR
     */
    public void non_preOrder(){
        if(this.root == null){
            return;
        }
        LinkedList<Entry<T>> stack = new LinkedList<>();
        stack.push(this.root);

        while(!stack.isEmpty()){
            Entry<T> top = stack.pop();
            System.out.print(top.data + " ");

            if(top.right != null){
                stack.push(top.right);
            }

            if(top.left != null){
                stack.push(top.left);
            }
        }
        System.out.println();
    }

    /**
     * 非递归实现中序遍历   LVR
     */
    public void non_inOrder(){
        if(this.root == null){
            return;
        }

        LinkedList<Entry<T>> stack = new LinkedList<>();
        Entry<T> cur = this.root;
        while(!stack.isEmpty() || cur != null){
            if(cur != null){ // L 证明当前节点（左孩子）还不为空，一直入栈
                stack.push(cur);
                cur = cur.left; // 一直深度优先把节点的左孩子入栈
            } else {
                // V
                Entry<T> top = stack.pop();
                System.out.print(top.data + " ");

                // R
                cur = top.right;
            }
        }
    }

    /**
     * 非递归实现后序遍历   LRV
     */
    public void non_postOrder(){
        if(this.root == null){
            return;
        }

        LinkedList<Entry<T>> stack1 = new LinkedList<>();
        LinkedList<Entry<T>> stack2 = new LinkedList<>();
        stack1.push(this.root);

        while(!stack1.isEmpty()){
            Entry<T> top = stack1.pop();

            if(top.left != null){
                stack1.push(top.left);
            }

            if(top.right != null){
                stack1.push(top.right);
            }

            stack2.push(top);
        }

        while (!stack2.isEmpty()){
            System.out.print(stack2.pop().data + " ");
        }
        System.out.println();
    }

    /**
     * 非递归实现层序遍历
     */
    public void non_levelOrder(){
        if(this.root == null){
            return;
        }

        LinkedList<Entry<T>> queue = new LinkedList<>();
        queue.offer(this.root);

        while (!queue.isEmpty()){
            Entry<T> front = queue.poll();
            System.out.print(front.data + " ");

            if(front.left != null){
                queue.offer(front.left);
            }

            if(front.right != null){
                queue.offer(front.right);
            }
        }
    }

    /**
     * BST树的节点类型
     * @param <T>
     */
    static class Entry<T extends Comparable<T>>{
        T data; // 数据域
        Entry<T> left; // 左孩子域
        Entry<T> right; // 右孩子域

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

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * 测试非递归实现中序遍历
     */
    @Test
    public void test16(){
        int[] arr = {63,23,92,12,35,98,18,46,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.levelOrder();
        bst.non_levelOrder();
    }

    /**
     * 测试非递归实现后序遍历
     */
    @Test
    public void test15(){
        int[] arr = {63,23,92,12,35,98,18,46,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.postOrder();
        bst.non_postOrder();
    }

    /**
     * 测试非递归实现中序遍历
     */
    @Test
    public void test14(){
        int[] arr = {63,23,92,12,35,98,18,46,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.inOrder();
        bst.non_inOrder();
    }

    /**
     * 测试非递归实现前序遍历
     */
    @Test
    public void test13(){
        int[] arr = {63,23,92,12,35,98,18,46,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.preOrder();
        bst.non_preOrder();
    }

    /**
     * 测试已知前序和中序，重建二叉树的问题
     */
    @Test
    public void test12(){
        Integer[] pre = {63, 23, 12, 18, 35, 46, 92, 98, 102}; // 二叉树的前序
        Integer[] in = {12, 18, 23, 35, 46, 63, 92, 98, 102}; // 二叉树的中序

        BSTree<Integer> bst = new BSTree<>();
        bst.rebuild(pre, in);
        bst.preOrder();
        bst.inOrder();
    }

    /**
     * 测试中序遍历倒数第K个节点的问题
     */
    @Test
    public void test11(){
        int[] arr = {63,23,92,12,35,98,18,46,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.inOrder();
        System.out.println(bst.getInOrderLastKVal(4));
    }

    /**
     * 测试平衡树的问题
     */
    @Test
    public void test10(){
        int[] arr = {63,23,92,12,35,98,18,46,102,80};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        System.out.println(bst.isBalance());
    }

    /**
     * 测试LCA问题
     */
    @Test
    public void test09(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        System.out.println(bst.getLCA(79, 82));
    }


    /**
     * 测试子树问题
     */
    @Test
    public void test08(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        BSTree<Integer> bst2 = new BSTree<>();
        bst2.insert(92);
        bst2.insert(79);
        bst2.insert(98);
        bst2.insert(82);
        bst2.insert(60);

        System.out.println(bst.isChildTree(bst2));
    }


    /**
     * 测试一颗二叉树是否是BST树
     */
    @Test
    public void test07(){
        BSTree.Entry<Integer> e1 = new BSTree.Entry<Integer>(40);
        BSTree.Entry<Integer> e2 = new BSTree.Entry<Integer>(20);
        BSTree.Entry<Integer> e3 = new BSTree.Entry<Integer>(80);
        BSTree.Entry<Integer> e4 = new BSTree.Entry<Integer>(60);
        BSTree.Entry<Integer> e5 = new BSTree.Entry<Integer>(90);
        BSTree.Entry<Integer> e6 = new BSTree.Entry<Integer>(30);

        BSTree<Integer> bst = new BSTree<>();
        bst.root = e1;
        e1.left = e2;
        e1.right = e3;
        e3.left = e4;
        e3.right = e5;

        System.out.println(bst.isBSTree());

        e2.right = e6;
        System.out.println(bst.isBSTree());
    }


    /**
     * 测试BST区间元素打印
     */
    @Test
    public void test06(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.inOrder();
        bst.printSectionDatas(23, 82);
    }

    /**
     * 测试BST镜像
     */
    @Test
    public void test05(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.inOrder();
        bst.mirror();
        bst.inOrder();
    }

    /**
     * 测试递归的增删查
     */
    @Test
    public void test04(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.insert(v);
        }

        bst.inOrder();
        System.out.println(bst.query(63));
        System.out.println(bst.query(43));
        bst.remove(82);
        bst.remove(98);
        bst.remove(63);
        bst.inOrder();
    }

    /**
     * 测试前中后序遍历
     */
    @Test
    public void test03(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.non_insert(v);
        }

        bst.preOrder();
        bst.inOrder();
        bst.postOrder();
        bst.levelOrder();
    }

    /**
     * 测试非递归的删除操作
     */
    @Test
    public void test02(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.non_insert(v);
        }
        bst.non_insert(25);
        bst.non_remove(46);
        bst.non_remove(79);
        bst.non_remove(63);
    }

    /**
     * 测试非递归插入和查询操作
     */
    @Test
    public void test01(){
        int[] arr = {63,23,92,12,35,79,98,18,46,82,102};
        BSTree<Integer> bst = new BSTree<>();
        for(int v : arr){
            bst.non_insert(v);
        }

        System.out.println(bst.non_query(25));
        bst.non_insert(25);
        System.out.println(bst.non_query(25));
    }
}
