package com.fixbug;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 哈夫曼树的实现
 */
class HuffmanTree{
    // 统计字符的个数
    private Map<Character, Integer> charMap;
    // 小根堆存储Entry节点，通过权值比较节点大小
    private PriorityQueue<Entry> queue;
    // 指向哈夫曼树的根节点
    private Entry root;
    // 存储字符以及最终的哈夫曼编码
    private Map<Character, String> codeMap;

    public HuffmanTree() {
        charMap = new HashMap<>();
        // 生成优先级队列，自定义通过Entry节点的权值进行比较
        queue = new PriorityQueue<Entry>((a, b)->{
            return a.getCount().compareTo(b.getCount());
        });
        codeMap = new HashMap<>();
    }

    /**
     * 生成哈夫曼树
     * @param str
     */
    public void createHuffmanTree(String str) {
        // 1.统计字符串中字符出现的频率
        for(int i=0; i < str.length(); ++i){
            Integer cnt = charMap.get(str.charAt(i));
            if(cnt == null){
                charMap.put(str.charAt(i), 1);
            } else {
                charMap.put(str.charAt(i), cnt + 1);
            }
        }

        // 2.把带权值的节点都添加到优先级队列当中  小根堆
        charMap.forEach((ch, count)->{
            Entry entry = new Entry(ch, count);
            queue.offer(entry);
        });

        // 3.循环处理小根堆，每次拿两个权值最小的节点进行合并生成二叉树，不断重复
        while (queue.size() > 1){
            Entry entry1 = queue.poll();
            Entry entry2 = queue.poll();
            Entry entry = new Entry('\u0000', entry1.getCount()+entry2.getCount());
            entry.setLeft(entry1);
            entry.setRight(entry2);
            queue.offer(entry);
        }

        // 4.指向哈夫曼树的根节点
        this.root = queue.poll();
    }

    /**
     * 打印哈夫曼编码
     * @return
     */
    public void showCode() {
        String str = "";
        showCode(this.root, str);

        codeMap.forEach((ch, code)->{
            System.out.println(ch + " code: " + code);
        });
    }

    private void showCode(Entry root, String str) {
        if(root.getLeft() == null && root.getRight() == null){
            codeMap.put(root.getCh(), str);
            return;
        }

        showCode(root.getLeft(), str + "0");
        showCode(root.getRight(), str + "1");
    }

    /**
     * 根据生成的哈夫曼编码，把str原始序列的编码进行返回
     * @param str
     * @return
     */
    public String encode(String str) {
        StringBuilder sbuf = new StringBuilder();
        for(int i=0; i< str.length(); ++i){
            sbuf.append(codeMap.get(str.charAt(i)));
        }
        return sbuf.toString();
    }

    /**
     * encode是哈夫曼编码，根据上面的哈夫曼树，进行数据解码，返回
     * @param encode
     * @return
     */
    public String decode(String encode) {
        StringBuilder sbuilder = new StringBuilder();
        Entry cur = this.root;
        for(int i=0; i < encode.length(); ++i){
            if(encode.charAt(i) == '0'){
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }

            // 访问到根节点了
            if(cur.getLeft() == null && cur.getRight() == null){
                sbuilder.append(cur.getCh());
                cur = this.root;
            }
        }
        return sbuilder.toString();
    }

    /**
     * 哈夫曼树节点的类型
     */
    static class Entry{
        Character ch;
        Integer count;  // 权值
        Entry left;
        Entry right;

        public Entry(char ch, int count) {
            this.ch = ch;
            this.count = count;
            this.left = this.right = null;
        }

        public Character getCh() {
            return ch;
        }

        public void setCh(Character ch) {
            this.ch = ch;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Entry getLeft() {
            return left;
        }

        public void setLeft(Entry left) {
            this.left = left;
        }

        public Entry getRight() {
            return right;
        }

        public void setRight(Entry right) {
            this.right = right;
        }
    }
}

/**
 * Hello world!
 */
public class 哈夫曼编码
{
    public static void main( String[] args ) throws Exception{
        String str = "soiyufhsjadfkndgiuyeruwighjhjsgyeugyfwhjkhggyuewhjdb";
        HuffmanTree ht = new HuffmanTree();
        ht.createHuffmanTree(str);
        ht.showCode();

        String encode = ht.encode(str);
        System.out.println(encode);

        // 解码
        System.out.println(ht.decode(encode));
    }
}