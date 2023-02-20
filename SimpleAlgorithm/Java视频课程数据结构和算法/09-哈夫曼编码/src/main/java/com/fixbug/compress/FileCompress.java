package com.fixbug.compress;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 文件压缩类  通过构建文件内容的哈夫曼编码进行文件数据压缩
 */
public class FileCompress {
    // 存储文件内容的频率
    private Map<Character, Integer> countMap = new HashMap<>();
    // 小根堆
    private PriorityQueue<Entry> queue = new PriorityQueue<Entry>((a, b)->{return a.getCount().compareTo(b.getCount());});
    // 指向哈夫曼树的根
    private Entry root;
    // 存储字符的哈夫曼编码
    private Map<Character, String> codeMap = new HashMap<>();

    /**
     * 压缩文件并输出压缩后的文件
     * @param file
     */
    public void compress(File file) throws Exception{
        // 1. 读取文件的每一个字节数，统计它们的频率
        FileInputStream in = new FileInputStream(file);
        int data = -1;
        while(-1 != (data = in.read())){
            Integer count = countMap.get((char)data);
            if(count == null){
                countMap.put((char)data, 1);
            } else {
                countMap.put((char)data, count+1);
            }
        }
        in.close();

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

        // 4.重新读取文件，获取相应字符的哈夫曼编码，进行压缩存储
        StringBuilder sbuilder = new StringBuilder();
        FileInputStream filein = new FileInputStream(file);
        DataOutputStream fileout = new DataOutputStream(new FileOutputStream(file.getName() + ".compress"));

        // 写文件头，把文件字符以及频率进行存储，用于解压缩
        fileout.write(countMap.keySet().size()); // 先写文件，记录字符的个数
        countMap.forEach((ch, count)->{
            try {
                fileout.write(ch); // 写字符内容
                fileout.writeInt(count); // 写字符频率
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 写文件具体的编码内容
        while(-1 != (data = filein.read())){
            String code = codeMap.get((char)data);
            sbuilder.append(code);
            while(sbuilder.length() >= 8){
                code = sbuilder.substring(0, 8);
                byte writeData = FormatUtil.binaryStringToByte(code);
                fileout.write(writeData);
                sbuilder.delete(0, 8);
            }
        }

        // sbuilderk可能还有剩余的编码 11010100      1100 => 1100 0000  4
        if(sbuilder.length() > 0){
            byte writeData = FormatUtil.binaryStringToByte(sbuilder.toString()); // 1100 0000
            writeData <<= (8 - sbuilder.length());
            fileout.write(writeData);
            fileout.write(sbuilder.length());
        }

        filein.close();
        fileout.close();
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
        FileCompress fc = new FileCompress();
        fc.compress(new File("D:\\代码\\Java Code\\20171107\\com\\sun\\corba\\se\\PortableActivationIDL\\_ActivatorStub.java"));
    }
}