package com.fixbug.compress;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 实现文件的解压缩操作
 */
public class FileDecompress {
    // 存储文件内容的频率
    private Map<Character, Integer> countMap = new HashMap<>();
    // 小根堆
    private PriorityQueue<Entry> queue = new PriorityQueue<Entry>((a, b)->{return a.getCount().compareTo(b.getCount());});
    // 指向哈夫曼树的根
    private Entry root;
    // 存储字符的哈夫曼编码
    private Map<Character, String> codeMap = new HashMap<>();

    /**
     * 文件的解压缩操作
     * @param file
     */
    public void decompress(File file) throws Exception{
        // 1.先读取文件头，获取字符以及频率存储到countMap中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        // 文件的第一个字节放的是字符的个数
        int size = in.read();
        // 开始读取字符以及频率
        for (int i = 0; i < size; i++) {
            Character ch = (char)in.read();
            Integer count = in.readInt();
            countMap.put(ch, count);
        }
        
        // 2.构建哈夫曼树
        // 2.生成权值节点，放入小根堆，构建哈夫曼树
        countMap.forEach((key, value)->{
            Entry entry = new Entry(key, value);
            queue.offer(entry);
        });

        while (queue.size() > 1){
            Entry entry1 = queue.poll();
            Entry entry2 = queue.poll();
            Entry entry = new Entry('\u0000', entry1.getCount() + entry2.getCount());
            entry.setLeft(entry1);
            entry.setRight(entry2);
            queue.offer(entry);
        }
        this.root = queue.poll();

        // 3.获取一下叶子节点（每一个字符）的哈夫曼编码
        String huffmanCode = "";
        getCode(this.root, huffmanCode);

        // 继续读取文件，然后解压缩内容，还原文件
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        fileName = fileName.substring(0, index);
        FileOutputStream out = new FileOutputStream(fileName);

        Entry cur = this.root;
        int data = -1;
        int length = 0;
        // 压缩文件的最后两个字节需要单独处理一下， 1100 0000  4
        while(-1 != (data = in.read())){
            String code = FormatUtil.byteToBinaryString((byte)data);
            length = code.length();
            if(in.available() == 1){ // 文件就剩最后一个字节了
                length = in.read(); // 读取最后一个字节，表示最后一个编码有效的哈夫曼编码的位数
            }
            for(int i=0; i < length; ++i){
                if(code.charAt(i) == '0'){
                    cur = cur.getLeft();
                } else {
                    cur = cur.getRight();
                }

                if(cur.getLeft() == null && cur.getRight() == null){
                    out.write(cur.getCh());
                    cur = this.root;
                }
            }
        }

        out.close();
        in.close();
    }

    /**
     * 获取叶子节点的哈夫曼编码
     * @param root
     * @param huffmanCode
     */
    private void getCode(Entry root, String huffmanCode) {
        if(root.getLeft() == null && root.getRight() == null){
            codeMap.put(root.getCh(), huffmanCode);
            return;
        }

        getCode(root.getLeft(), huffmanCode+"0");
        getCode(root.getRight(), huffmanCode+"1");
    }

    public static void main(String[] args) throws Exception{
        FileDecompress fd = new FileDecompress();
        fd.decompress(new File("_ActivatorStub.java.compress"));
    }
}
