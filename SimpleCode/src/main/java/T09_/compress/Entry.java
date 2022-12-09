package T09_.compress;


/**
 * 哈夫曼树节点
 */
public class Entry {
    private Character ch;//字符数据
    private Integer count;//字符频率
    private Entry left;
    private Entry right;

    public Entry(Character ch, Integer count) {
        this.ch = ch;
        this.count = count;
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
