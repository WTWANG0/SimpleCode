package T09_.compress;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 文件的解压操作
 */
public class FileDecompress {
    // 存储文件内容的频率
    private Map<Character, Integer> countMap = new HashMap<>();
    // 小根堆
    private PriorityQueue<Entry> queue = new PriorityQueue<Entry>((a, b) -> {
        return a.getCount().compareTo(b.getCount());
    });
    // 指向哈夫曼树的根
    private Entry root;
    // 存储字符的哈夫曼编码
    private Map<Character, String> codeMap = new HashMap<>();


    /**
     * test
     */
    public static void main(String[] args) throws Exception {
        FileDecompress fd = new FileDecompress();
        fd.decompress(new File("_ActivatorStub.java.compress"));
    }


    /**
     * 解压操作
     */
    private void decompress(File file) throws Exception {
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        //1:先读单词个数
        int size = in.readInt();

        //2:读字符 和 频率
        for (int i = 0; i < size; i++) {
            Character ch = (char) in.read();
            Integer count = in.readInt();
            countMap.put(ch, count);
        }

        //3:构建哈夫曼树，
        countMap.forEach((key, val) -> {
            queue.offer(new Entry(key, val));
        });

        while (queue.size() > 1) {
            Entry entry1 = queue.poll();
            Entry entry2 = queue.poll();
            Entry entry = new Entry('\u0000', entry1.getCount() + entry2.getCount());
            entry.setLeft(entry1);
            entry.setRight(entry2);
            queue.offer(entry);
        }
        this.root = queue.poll();

        //3：获取一下叶子节点的哈夫曼编码
        String huffmanCode = "";
        getCode(root, huffmanCode);

        //4:读取剩下内容，进行解压
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index); //把.compress去掉
        FileOutputStream out = new FileOutputStream(fileName);

        Entry cur = this.root;
        int data = -1;
        int length = 0;

        while (-1 != (data = in.read())) {
            String code = FormatUtil.binaryStringToString((byte) data);
            length = code.length();
            if (in.available() == 1) { //剩下一个字节
                length = in.read();// 读取最后一个字节，表示最后一个编码有效的哈夫曼编码的位数
            }

            for (int i = 0; i < length; i++) {
                if (code.charAt(i) == '0') {
                    cur = cur.getLeft();
                } else {
                    cur = cur.getRight();
                }
                if (cur.getLeft() == null && cur.getRight() == null) {
                    out.write(cur.getCh());
                    cur = this.root;
                }
            }
        }

        in.close();
        out.close();
    }


    /**
     * 获取叶子节点的哈夫曼编码
     */
    private void getCode(Entry root, String huffmanCode) {
        if (root.getLeft() == null && root.getRight() == null) {
            codeMap.put(root.getCh(), huffmanCode);
            return;
        }

        getCode(root.getLeft(), huffmanCode + "0");
        getCode(root.getRight(), huffmanCode + "1");
    }


}
