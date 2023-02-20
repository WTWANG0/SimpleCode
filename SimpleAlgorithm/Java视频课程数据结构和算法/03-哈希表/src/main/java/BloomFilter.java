import org.apache.commons.codec.digest.DigestUtils;

/**
 * 描述: 布隆过滤器介绍   大数查重   查询指定的数据是否存在
 * 优点：集中了哈希表和位图的优势       缺点：会产生误报率，判断不存在100%  判断存在，有误差
 *         查询快O(1) 内存占用量小
 *
 * @Author shilei
 * @Date 2019/8/31
 */
public class BloomFilter {

    // 位数组
    private byte[] bits;
    // 位数的大小
    private int bitsize;

    /**
     * Bloom Filter初始化
     * @param size 指定位的大小
     */
    public BloomFilter(int size){
        this.bitsize = size;
        this.bits = new byte[this.bitsize/8+1]; // size*8个bit位
    }

    /**
     * Bloom Filter添加元素
     * @param url
     */
    public void put(String url){
        int h1 = hash1(url);
        int h2 = hash2(url);
        int h3 = hash3(url);

        int index, offset;

        index = h1 / 8;
        offset = h1 % 8;
        bits[index] |= (1 << offset);

        index = h2 / 8;
        offset = h2 % 8;
        bits[index] |= (1 << offset);

        index = h3 / 8;
        offset = h3 % 8;
        bits[index] |= (1 << offset);
    }

    /**
     * Bloom Filter查询元素
     * @param url
     * @return
     */
    public boolean get(String url){
        int h1 = hash1(url);
        int h2 = hash2(url);
        int h3 = hash3(url);

        int index, offset;

        index = h1 / 8;
        offset = h1 % 8;
        if((bits[index] & (1 << offset)) == 0){
            return false;
        }

        index = h2 / 8;
        offset = h2 % 8;
        if((bits[index] & (1 << offset)) == 0){
            return false;
        }

        index = h3 / 8;
        offset = h3 % 8;
        if((bits[index] & (1 << offset)) == 0){
            return false;
        }

        return true;
    }

    /**
     * hash1 - 除留余数法
     */
    private int hash1(String url){
        return Math.abs(url.hashCode() % this.bitsize);
    }

    /**
     * hash2 - md5结果
     * @param url
     * @return
     */
    private int hash2(String url){
        byte[] digest = DigestUtils.md5(url);
        /* 每四个字节构成一个32位整数，
	        将四个32位整数相加得到instr的hash值（可能溢出） */
        int hash = 0;
        for(int i = 0; i < 4; i++)
        {
            hash += ((long)(digest[i*4 + 3]&0xFF) << 24)
                    | ((long)(digest[i*4 + 2]&0xFF) << 16)
                    | ((long)(digest[i*4 + 1]&0xFF) <<  8)
                    | ((long)(digest[i*4 + 0]&0xFF));
        }
        return Math.abs(hash) % this.bitsize;
    }

    /**
     * hash3 - sha结果
     * @param url
     * @return
     */
    private int hash3(String url){
        byte[] digest = DigestUtils.sha1(url);
        /* 每四个字节构成一个32位整数，
	        将四个32位整数相加得到instr的hash值（可能溢出） */
        int hash = 0;
        for(int i = 0; i < 4; i++)
        {
            hash += ((long)(digest[i*4 + 3]&0xFF) << 24)
                    | ((long)(digest[i*4 + 2]&0xFF) << 16)
                    | ((long)(digest[i*4 + 1]&0xFF) <<  8)
                    | ((long)(digest[i*4 + 0]&0xFF));
        }
        return Math.abs(hash) % this.bitsize;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        BloomFilter bf = new BloomFilter(121);
        // 添加黑名单
        bf.put("http://www.sina.com");
        bf.put("http://www.google.com");
        bf.put("http://www.baidu.com");
//        bf.put("http://www.csdn.com");
//        bf.put("http://www.taobao.com");

        // 过滤URL
        System.out.println(bf.get("http://www.sina.com"));
        System.out.println(bf.get("http://www.google.com"));
        System.out.println(bf.get("http://www.baidu.com"));
        System.out.println(bf.get("http://www.csdn.com"));
    }
}