package T09_;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTreeTest {
    public static void main(String[] args) throws Exception {
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


/**
 * 哈夫曼树
 */
class HuffmanTree {
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
        queue = new PriorityQueue<Entry>((a, b) -> {
            return a.getCount().compareTo(b.getCount());
        });
        codeMap = new HashMap<>();
    }


    public void createHuffmanTree(String str) {
        for (int i = 0; i < str.length(); i++) {
            Integer count = charMap.get(str.charAt(i));
            if (count == null) {
                charMap.put(str.charAt(i), 1);
            } else {
                charMap.put(str.charAt(i), count + 1);
            }
        }

        charMap.forEach((key, val) -> {
            queue.offer(new Entry(key, val));
        });


        while (queue.size() > 1) {
            Entry entry1 = queue.poll();
            Entry entry2 = queue.poll();

            //注意：只有新节点才会设置left 和 right，原生节点没有left 和 right
            Entry entry = new Entry('\u0000', entry1.getCount() + entry2.getCount());
            entry.setLeft(entry1);
            entry.setRight(entry2);
            queue.offer(entry);
        }

        this.root = queue.poll();
    }


    /**
     * 打印哈夫曼编码
     */
    public void showCode() {
        String str = "";
        showCode(this.root, str);

        codeMap.forEach((key, val) -> {
            System.out.println(key + " code: " + val);
        });

    }

    public void showCode(Entry entry, String huffmanCode) {
        if (entry.left == null && entry.right == null) {
            codeMap.put(entry.getCh(), huffmanCode);
            return;
        }

        showCode(entry.left, huffmanCode + "0");
        showCode(entry.right, huffmanCode + "1");

    }


    /**
     * 压缩
     */
    public String encode(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(codeMap.get(str.charAt(i)));
        }
        return sb.toString();
    }

    /**
     * 解压
     */
    public String decode(String encode) {
        StringBuilder sb = new StringBuilder();
        Entry cur = this.root;

        for (int i = 0; i < encode.length(); i++) {
            if (encode.charAt(i) == '0') {
                cur = cur.left;
            } else {
                cur = cur.right;
            }

            if (cur.getLeft() == null && cur.getRight() == null) {
                sb.append(cur.getCh());
                cur = this.root;
            }
        }
        return sb.toString();

    }


    /**
     * 哈夫曼树节点的类型
     */
    static class Entry {
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