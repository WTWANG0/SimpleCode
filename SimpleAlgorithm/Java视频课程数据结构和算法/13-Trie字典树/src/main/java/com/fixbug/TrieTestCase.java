package com.fixbug;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 字典树代码实现
 */
class TrieTree{

    // 字典树的根节点类型
    private TrieNode root;

    public TrieTree() {
        // 创建Trie树的根节点
        this.root = new TrieNode('\u0000', 0, new HashMap<Character, TrieNode>());
    }

    /**
     * Trie树的增加元素
     * @param word
     */
    public void add(String word){
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode child = cur.nodeMap.get(ch);
            if(child == null){ // 表示cur节点的下一级节点，没有ch字符节点
                child = new TrieNode(ch, 0, new HashMap<Character, TrieNode>());
                cur.nodeMap.put(ch, child);
            }

            cur = child;
        }
        // cur就指向了word串的最后一个字符节点了
        cur.freqs++;
    }

    /**
     * Trie树的删除操作
     * @param word
     */
    public void remove(String word){
        TrieNode cur = root;
        TrieNode del = root;
        char delch = word.charAt(0);

        for (int i = 0; i < word.length(); i++) {
            TrieNode child = cur.nodeMap.get(word.charAt(i));
            if(child == null){
                return;
            }

            // 更新一下del和delch，del记录的是离cur最近的freqs不为0的字符节点
            // 或者del更新到最后一个下一级字符节点不为1的节点处
            if(cur.freqs > 0 || cur.nodeMap.size() > 1){
                del = cur;
                delch = word.charAt(i);
            }

            cur = child;
        }

        if(cur.nodeMap.size() == 0){ // 后面没有其它字符节点了
            del.nodeMap.remove(delch);
        } else {
            cur.freqs = 0; // 后面还有其它字符节点，此处不删除任何节点，只把次数写成0，代表删除操作
        }
    }

    /**
     * Trie树的查询操作
     * @param word
     * @return  找到word，返回串的次数，否则返回0
     */
    public int query(String word){
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode child = cur.nodeMap.get(ch);
            if(child == null){
                return 0;
            }
            cur = child;
        }
        return cur.freqs;
    }

    /**
     * 前缀查找
     * @param prefix
     */
    public void queryPrefix(String prefix){
        TrieNode cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            TrieNode child = cur.nodeMap.get(ch);
            if(child == null){
                return;
            }
            cur = child;
        }

        // cur就指向了前缀的最后一个字符了
        String word = new String(prefix.substring(0, prefix.length()-1));
        preOrder(cur, word);
    }

    /**
     * 打印Trie树，相当于是进行了串排序
     */
    public void showTrieTree(){
        String word = new String();
        preOrder(root, word);
    }

    /**
     * Trie树的前序遍历代码
     * @param root
     * @param word
     */
    private void preOrder(TrieNode root, String word) {
        if(root == null){
            return;
        }

        if(root != this.root){
            word += root.ch;
            if(root.freqs > 0){
                System.out.println(word);
            }
        }

        for(Map.Entry<Character, TrieNode> entry : root.nodeMap.entrySet()){
            if(entry.getValue() != null){
                preOrder(entry.getValue(), word);
            }
        }
    }

    /**
     * 字典树的节点类型
     */
    static class TrieNode{
        char ch; // 节点存储的字符
        int freqs; // 在每一个串的末尾字符+1，记录这个串出现的次数
        Map<Character, TrieNode> nodeMap; // 存储当前节点下一层节点的信息

        public TrieNode(char ch, int freqs, Map<Character, TrieNode> nodeMap) {
            this.ch = ch;
            this.freqs = freqs;
            this.nodeMap = nodeMap;
        }
    }
}

/**
 * 字典树代码测试
 *
 */
public class TrieTestCase
{
    public static void main( String[] args )
    {
        TrieTree trie = new TrieTree();
        trie.add("hello");
        trie.add("hello");
        trie.add("hel");
        trie.add("hel");
        trie.add("hel");
        trie.add("china");
        trie.add("ch");
        trie.add("ch");
        trie.add("heword");
        trie.add("hellw");

        System.out.println(trie.query("hello"));
        System.out.println(trie.query("hel"));
        System.out.println(trie.query("china"));
        System.out.println(trie.query("ch"));

        trie.showTrieTree();
        System.out.println("搜索前缀字符串:");
        trie.queryPrefix("he");
        trie.queryPrefix("ch");

        System.out.println("-------------------------");
        trie.remove("hello");
        System.out.println(trie.query("hello"));
        System.out.println(trie.query("hel"));
        System.out.println("-------------------------");
        trie.showTrieTree();

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            list.add(i);
        }
    }
}
