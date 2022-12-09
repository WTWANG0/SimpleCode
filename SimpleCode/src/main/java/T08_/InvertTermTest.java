package T08_;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * 倒排索引
 */
public class InvertTermTest {

    /**
     * 测试代码
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("输入搜索根路径:");
        String path = in.nextLine();

        InvertIndex index = new InvertIndex();
        index.setSearchPath(path);

        for (; ; ) {
            System.out.println("输入搜索的内容:");
            String words = in.nextLine();
            index.query(words);
        }
    }
}


/**
 * 一份文档的记录数据
 */
class InvertTerm {
    private int docId; //表示文档
    private int freqs; //单词词频
    private List<Integer> locations; //存储单词的位置


    /**
     * 初始化
     */
    public InvertTerm(int docId, int freqs, int location) {
        this.docId = docId;
        this.freqs = freqs;
        this.locations = new ArrayList<>();
        this.locations.add(location);
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getFreqs() {
        return freqs;
    }

    public void setFreqs(int freqs) {
        this.freqs = freqs;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }


    @Override
    public boolean equals(Object obj) {
        return this.docId == ((InvertTerm) obj).docId;
    }

    public void increaseFreqs() {
        this.freqs++;
    }

    public void addLocation(int location) {
        this.locations.add(location);
    }
}


/**
 * 倒排列表定义 == 由于单词组成的倒排索引
 * 所有文档的记录数据
 */
class InvertList {
    private List<InvertTerm> invertList; //存储单词的所有排项

    public InvertList() {
        this.invertList = new ArrayList<>();
    }

    /**
     * 添加倒排索引项
     */
    public void addTerm(int docId, int location) {
        for (int i = 0; i < this.invertList.size(); i++) {
            InvertTerm term = this.invertList.get(i);
            if (term.getDocId() == docId) {
                //doc对应的倒排索引已存在，增加词频和位置信息
                term.increaseFreqs(); //词频
                term.addLocation(location); //位置
                return;
            }
        }

        InvertTerm term = new InvertTerm(docId, 1, location);
        this.invertList.add(term);
    }

    /**
     * 获取倒排项集合
     */
    public List<InvertTerm> getInvertTermList() {
        return this.invertList;
    }

}


/**
 * 倒排索引的实现
 */
class InvertIndex {
    private Map<String, InvertList> invertIndex;//倒排索引
    private List<File> fileList;//存储所有存储的文件
    private String path;//指定文件的搜索路径

    public InvertIndex() {
        this.invertIndex = new HashMap<>();
        this.fileList = new ArrayList<>();
    }

    /**
     * 设置文件根路劲
     */
    public void setSearchPath(String path) {
        this.path = path;

        //搜索path目录下所有的文件
        searchFiles(new File(path));
        System.out.println(path + "路径下的文件搜索完毕!");

        //创建倒排索引
        createInvertIndex();
        System.out.println("倒排索引生成完毕!");
    }

    /**
     * 搜索文件
     */
    public void searchFiles(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }

        for (File f : files) {
            if (f.isDirectory()) { //目录
                searchFiles(f);
            } else { //文件
                if (f.getName().endsWith(".java")) {
                    this.fileList.add(f); //添加到fileList
                }
            }
        }
    }

    /**
     * 开始创建倒排索引
     */
    public void createInvertIndex() {
        String line = null; //每一行读取的数据
        int number = 0; //当前文档第N个词

        try {
            for (int i = 0; i < this.fileList.size(); i++) {
                File file = this.fileList.get(i);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                number = 0;
                while (null != (line = reader.readLine())) { //可读
                    String[] words = line.split(" ");//去掉空格
                    for (String word : words) {
                        word = word.trim();//去掉空格
                        number++; //位置：第几个词

                        InvertList invertList = invertIndex.get(word);
                        if (invertList == null) { //没有，创建
                            invertList = new InvertList();
                            invertList.addTerm(i, number);
                            this.invertIndex.put(word, invertList);
                        } else {
                            invertList.addTerm(i, number);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询
     */
    public void query(String phrase) {
        List<String> wordsList = new ArrayList<>();
        String[] words = phrase.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].trim();
            wordsList.add(word);
        }

        if (wordsList.size() == 1) {
            InvertList invertList = this.invertIndex.get(wordsList.get(0));
            if (invertList == null) {
                System.out.println("未搜索到任何相关的文件!");
            } else {
                List<InvertTerm> termList = invertList.getInvertTermList();
                for (int i = 0; i < termList.size(); i++) {
                    File file = this.fileList.get(termList.get(i).getDocId()); //找出文件
                    System.out.println(file.getName() + "  词频：" + termList.get(i).getFreqs());
                }
            }

        } else { //多个单词

            //1：统计包含word的所有文档
            InvertList[] invertLists = new InvertList[wordsList.size()];
            for (int i = 0; i < wordsList.size(); i++) {
                invertLists[i] = this.invertIndex.get(wordsList.get(i)); //给每个单词创建一个InvertList
            }

            //2：先取第一个word文档
            List<InvertTerm> termList = invertLists[0].getInvertTermList();

            for (int i = 1; i < invertLists.length; i++) {
                List<InvertTerm> t1 = invertLists[i].getInvertTermList();
                termList.retainAll(t1);//对每次集合进行交集处理
            }

            //3：交集中必然包含所有词
            if (termList.isEmpty()) {
                System.out.println("未搜索到任何相关的文件!");
            } else {
                for (InvertTerm term : termList) {
                    File file = this.fileList.get(term.getDocId());
                    System.out.println(file.getName() + " 词频:" + term.getFreqs());
                }
            }
        }
    }


}

