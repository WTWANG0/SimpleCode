package T09_.compress;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 文件压缩：
 * 通过构建文件内容的哈弗曼编码进行文件数据压缩
 */
public class FileCompress {

    //统计文件内容频率
    private Map<Character, Integer> countMap = new HashMap<>();

    //小根堆
    private PriorityQueue<Entry> queue = new PriorityQueue<>((a, b) -> {
        return a.getCount() - b.getCount();
    });

    //哈夫曼树的根
    private Entry root;

    //存放字符的哈弗曼编码
    private Map<Character, String> codeMap = new HashMap<>();


    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        FileCompress fc = new FileCompress();
        //new File(FilePath)
        fc.compress(new File("D:\\代码\\Java Code\\20171107\\com\\sun\\corba\\se\\PortableActivationIDL\\_ActivatorStub.java"));
    }

    /**
     * 压缩文件并输出压缩后的文件
     */
    public void compress(File file) throws Exception {

        //1:统计每一个字符的数量
        FileInputStream in = new FileInputStream(file);
        int data = -1;
        while (-1 != (data = in.read())) { //有数据可读
            //读取字符编码
            Integer count = countMap.get((char) data);
            if (count == null) {
                countMap.put((char) data, 1);
            } else {
                countMap.put((char) data, count + 1);
            }
        }
        in.close();

        //2: 填充小根堆树
        countMap.forEach((key, value) -> {
            Entry entry = new Entry(key, value);
            queue.offer(entry);
        });

        //3: 两个entry合并成一个entry -- 》生成一个新的小根堆树
        while (queue.size() > 1) {
            Entry entry1 = queue.poll();
            Entry entry2 = queue.poll();

            //'\u0000' --表示一个空格
            Entry entry = new Entry('\u0000', entry1.getCount() + entry2.getCount());
            entry.setLeft(entry);
            entry.setRight(entry2);
            queue.offer(entry);
        }

        this.root = queue.poll(); //更新root

        //4:获取一下叶子节点（每一个字符）的哈夫曼编码
        String huffmanCode = "";
        getCode(this.root, huffmanCode);

        //5:
        StringBuilder sb = new StringBuilder();
        FileInputStream fileIn = new FileInputStream(file);
        DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(file.getName() + ".compress"));


        //6:写文件头，把文件字符以及频率进行存储，用于压缩
        fileOut.writeInt(countMap.keySet().size()); //先写文件，记录字符个数
        countMap.forEach((ch, count) -> {
            try {
                fileOut.write(ch); //写字符内容
                fileOut.writeInt(count); //写字符频率
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //7:写文件的编码内容
        while (-1 != (data = fileIn.read())) {
            String code = codeMap.get((char) data);
            sb.append(code);
            byte writeData = FormatUtil.binaryStringToByte(code);
            fileOut.write(writeData);
            sb.delete(0, 8); //重置SB
        }

        fileIn.close();
        fileOut.close();
    }


    /**
     * 获取叶子节点的哈夫曼编码
     */
    public void getCode(Entry root, String huffmanCode) {
        if (root.getLeft() == null && root.getRight() == null) {
            codeMap.put(root.getCh(), huffmanCode);
        }
        //左0，右1
        getCode(root.getLeft(), huffmanCode + "0");
        getCode(root.getRight(), huffmanCode + "1");

    }


}
