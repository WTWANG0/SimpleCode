package com.fixbug;


import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 倒排项的实现
 */
class InvertTerm{
    private int docid; // 标识文档
    private int freqs; // 单词词频
    private List<Integer> locations; // 存储单词位置

    /**
     * 初始化
     * @param docid
     * @param freqs
     * @param location
     */
    public InvertTerm(int docid, int freqs, int location) {
        this.docid = docid;
        this.freqs = freqs;
        this.locations = new ArrayList<>();
        this.locations.add(location);
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
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

    /**
     * 通过docid比较两个倒排项是否相同
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return this.docid == ((InvertTerm)obj).docid;
    }

    public void increaseFreqs() {
        this.freqs++;
    }

    public void addLocation(int location) {
        this.locations.add(location);
    }
}

/**
 * 倒排列表定义 = 由单词的所有倒排项组成
 */
class InvertList{
    private List<InvertTerm> invertList; // 存储单词的所有倒排项

    public InvertList() {
        this.invertList = new ArrayList<>();
    }

    /**
     * 添加倒排项
     * @param docid
     * @param location
     */
    public void addTerm(int docid, int location){
        for (int i = 0; i < this.invertList.size(); i++) {
            InvertTerm term = this.invertList.get(i);
            if(docid == term.getDocid()){
                // docid对应的倒排项已经存在
                term.increaseFreqs(); // 增加词频
                term.addLocation(location); // 增加单词位置信息
                return;
            }
        }

        // 给单词添加一个倒排项
        InvertTerm term = new InvertTerm(docid, 1, location);
        this.invertList.add(term);
    }

    /**
     * 获取倒排项集合
     * @return
     */
    public List<InvertTerm> getInvertTermList() {
        return this.invertList;
    }
}

/**
 * 倒排索引的实现
 */
class InvertIndex{
    private Map<String, InvertList> invertIndex; // 倒排索引
    private List<File> fileList; // 存储所有待搜索的文件
    private String path; // 指定的文件搜索根路径
    private String keywords; // 用户输入的搜索关键字&句子

    public InvertIndex() {
        this.invertIndex = new HashMap<>();
        this.fileList = new ArrayList<>();
    }

    /**
     * 设置文件根路径
     * @param path
     */
    public void setSearchPath(String path) {
        this.path = path;

        // 搜索path路径下的所有文件
        searchFiles(new File(path));
        System.out.println(path + " 路径下的文件搜索完毕!");

        // 开始创建倒排索引
        createInvertIndex();
        System.out.println("倒排索引生成完毕!");
    }

    // 搜索文件
    private void searchFiles(File file) {
        File[] files = file.listFiles();
        if(files == null){
            return;
        }
        for(File f : files){
            if(f.isDirectory()){
                searchFiles(f);
            } else {
                if(f.getName().endsWith(".java")) {
                    this.fileList.add(f);
                }
            }
        }
    }

    /**
     * 开始创建倒排索引
     */
    private void createInvertIndex() {
        // 1. 读取所有的文件fileList
        String line = null;
        int number = 0;

        try {
            for (int i = 0; i < this.fileList.size(); i++) {
                File file = this.fileList.get(i);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                number = 0;
                while(null != (line = reader.readLine())){
                    String[] words = line.split(" ");
                    for (String word : words) {
                        word = word.trim(); // 去掉单词前后无效的空格
                        number++;
                        InvertList it = invertIndex.get(word);
                        if(it == null){
                            it = new InvertList(); // 创建单词word的倒排列表
                            it.addTerm(i, number);
                            this.invertIndex.put(word, it); // 把[单词，倒排列表]添加到倒排索引当中
                        } else {
                            it.addTerm(i, number);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于倒排索引提供搜索服务的接口
     * @param phrase
     */
    public void query(String phrase){
        // 把用户输入的句子进行单词分解
        List<String> wordsList = new ArrayList<>();
        String[] words = phrase.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].trim();
            wordsList.add(word);
        }

        if(wordsList.size() == 1){ // 搜索一个单词
            InvertList il = this.invertIndex.get(wordsList.get(0));
            if(il == null){
                System.out.println("未搜索到任何相关的文件!");
            } else {
                // 输出搜索的结果
                List<InvertTerm> termList = il.getInvertTermList();
                for (int i = 0; i < termList.size(); i++) {
                    File file = this.fileList.get(termList.get(i).getDocid());
                    System.out.println(file.getName() + " 词频:" + termList.get(i).getFreqs());
                }
            }
        } else { // 搜索多个单词
            // 创建存储多个单词倒排列表的数组
            InvertList[] invertLists = new InvertList[wordsList.size()];
            for (int i = 0; i < wordsList.size(); i++) {
                invertLists[i] = this.invertIndex.get(wordsList.get(i));
            }

            // 给上面的倒排列表数组中，所有的倒排项求交集
            List<InvertTerm> termsList = invertLists[0].getInvertTermList();
            for(int i=1; i < invertLists.length; ++i){
                List<InvertTerm> tl = invertLists[i].getInvertTermList();
                termsList.retainAll(tl);
            }

            if(termsList.isEmpty()){
                System.out.println("未搜索到任何相关的文件!");
            } else {
                // 输出搜索的结果
                for (InvertTerm invertTerm : termsList) {
                    File file = this.fileList.get(invertTerm.getDocid());
                    System.out.println(file.getName() + " 词频:" + invertTerm.getFreqs());
                }
            }
        }
    }
}

/**
 * 倒排索引代码测试
 */
public class InvertIndexTest
{
    public static void main( String[] args )
    {
        Scanner in = new Scanner(System.in);
        System.out.println("输入搜索根路径:");
        String path = in.nextLine();

        InvertIndex index = new InvertIndex();
        index.setSearchPath(path);

        for(;;){
            System.out.println("输入搜索的内容:");
            String words = in.nextLine();
            index.query(words);
        }
    }
}