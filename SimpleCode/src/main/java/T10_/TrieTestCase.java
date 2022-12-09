package T10_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrieTestCase {
    public static void main(String[] args) {
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

     /*   System.out.println(trie.query("hello"));
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
        trie.showTrieTree();*/

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            list.add(i);
        }
    }
}


class TrieTree {
    private TrieNode root;

    public TrieTree() {
        this.root = new TrieNode('\u0000', 0, new HashMap<Character, TrieNode>());
    }


    public void add(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode child = cur.nodeMap.get(ch);
            if (child == null) {
                child = new TrieNode(ch, 0, new HashMap<Character, TrieNode>());
                cur.nodeMap.put(ch, child);
            }
            cur = child;
        }

        cur.freqs++;//增加结尾的评率
    }


    public void remove(String word) {
        TrieNode cur = root;
        TrieNode del = root;
        char delCh = word.charAt(0);

        for (int i = 0; i < word.length(); i++) {
            TrieNode child = cur.nodeMap.get(word.charAt(i));
            if (child == null) {
                return;
            }

            // 更新一下del和delch，del记录的是离cur最近的freqs不为0的字符节点
            // 或者del更新到最后一个下一级字符节点不为1的节点处
            if (cur.freqs > 0 || cur.nodeMap.size() > 1) {
                del = cur;
                delCh = word.charAt(i);
            }
            cur = child;
        }

        if (cur.nodeMap.size() == 0) { //cur是结尾字符，并且该字符没有分支数据
            del.nodeMap.remove(delCh);
        } else {
            cur.freqs--;
        }
    }


    static class TrieNode {
        char ch; //存储的字符
        int freqs;//以当前字符结尾的次数
        Map<Character, TrieNode> nodeMap; //节点下一层的信息

        public TrieNode(char ch, int freqs, Map<Character, TrieNode> nodeMap) {
            this.ch = ch;
            this.freqs = freqs;
            this.nodeMap = nodeMap;
        }
    }

}