package com.fixbug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 描述:链式哈希表的实现
 * 集合：key
 * 映射表：key，value
 *
 * @Author shilei
 * @Date 2019/8/30
 */
public class HashMap<K, V>{
    // 哈希表的定义
    private ArrayList<LinkedList<Entry<K,V>>> bucketsList;
    // 加载因子  已占用桶的个数 / 桶的总数量
    private double loadfactor = 0.75;
    // 记录已经使用的桶的个数
    private int usedBuckets = 0;
    // 素数表
    private static int[] primeTable = {3, 7, 23, 47, 97, 251, 443, 911, 1471, 42773};
    // 素数表的索引
    private int primeTableIdx = 0;

    /**
     * 哈希表的初始化操作
     */
    public HashMap(){
        this(0.75);
    }

    /**
     * 哈希表的初始化操作，自定义哈希表的加载因子
     */
    public HashMap(double loadfactor){
        this.bucketsList = new ArrayList<>(primeTable[primeTableIdx]); // 初始化哈希表的大小是3
        for (int i = 0; i < primeTable[primeTableIdx]; i++) {
            this.bucketsList.add(new LinkedList<Entry<K, V>>());
        }
        this.loadfactor = loadfactor;
    }

    /**
     * 增加一个key，value键值对
     * 不允许插入key：null元素
     * 不允许key重复
     * key存在，那么覆盖value
     * key不存在，增加一对新的key，value
     * @param key
     * @param value
     */
    public void put(K key, V value){

        double lf = this.usedBuckets * 1.0 / this.bucketsList.size();
        System.out.println("lf:" + lf + " " + this.bucketsList.size());
        if(lf > this.loadfactor){
            // 哈希表需要进行扩容操作
            resize();
        }

        // 先计算key的散列码
        int index = key.hashCode() % bucketsList.size();
        LinkedList<Entry<K,V>> list = bucketsList.get(index);
        if(list.isEmpty()){
            // index号位置的桶是空的
            list.add(new Entry<K, V>(key, value));
            this.usedBuckets++;  // 一个空桶被占用了，记录一下
        } else {
            for(Entry<K,V> entry : list){
                if(entry.key.equals(key)){
                    entry.value = value; // key存在，那么覆盖value
                    return;
                }
            }

            list.add(new Entry<K, V>(key, value)); // key不存在，增加一对新的key，value
        }
    }

    /**
     * 哈希表的扩容操作   transfer 转移节点的函数
     */
    private void resize() {
        // 用oldBucketsList指向原来的哈希表
        ArrayList<LinkedList<Entry<K,V>>> oldBucketsList = bucketsList;
        this.usedBuckets = 0;

        // 哈希表已经是最后一个素数的大小了
        if(primeTableIdx == this.primeTable.length){
            return;
        }

        // 哈希表扩容，开辟下一个素数指定的哈希表大小
        this.bucketsList = new ArrayList<>(primeTable[++primeTableIdx]);
        for (int i = 0; i < primeTable[primeTableIdx]; i++) {
            this.bucketsList.add(new LinkedList<Entry<K, V>>());
        }

        // 遍历原来的哈希表的所有桶中的链表数据，插入到新的哈希表中来
        for(LinkedList<Entry<K,V>> oldlist : oldBucketsList){
            // 说明当前的桶中有元素
            if(oldlist.size() > 0){
                for(Entry<K,V> entry : oldlist){
                    // 把key，value添加到新的哈希表当中
                    // 先计算key的散列码
                    int index = entry.key.hashCode() % bucketsList.size();
                    LinkedList<Entry<K,V>> list = bucketsList.get(index);
                    if(list.isEmpty()){
                        // index号位置的桶是空的 一个空桶被占用了，记录一下
                        this.usedBuckets++;
                    }
                    list.add(new Entry<K, V>(entry.key, entry.value));
                }
            }
        }
    }

    /**
     * 哈希表的删除操作
     * key不存在，返回null
     * key存在，返回对应的value值，并要做删除操作
     * @param key
     * @return
     */
    public V remove(K key){
        // 先计算key的散列码
        int index = key.hashCode() % bucketsList.size();
        LinkedList<Entry<K,V>> list = bucketsList.get(index);
        if(list.isEmpty()){
            return null; // key不存在，返回null
        }

        Iterator<Entry<K,V>> it = list.iterator();
        while(it.hasNext()){
            Entry<K,V> entry = it.next();
            if(entry.key.equals(key)){
                V val = entry.value;
                it.remove(); // 用迭代器直接删除元素
                return val; // key存在，返回对应的value值，并要做删除操作
            }
        }
        return null; // key不存在，返回null
    }

    /**
     * 哈希表的查询操作
     * @param key
     * @return
     */
    public V get(K key){
        // 先计算key的散列码
        int index = key.hashCode() % bucketsList.size();
        LinkedList<Entry<K,V>> list = bucketsList.get(index);
        if(list.isEmpty()){
            return null; // key不存在，返回null
        }

        Iterator<Entry<K,V>> it = list.iterator();
        while(it.hasNext()){
            Entry<K,V> entry = it.next();
            if(entry.key.equals(key)){
                return entry.value; // key存在，返回对应的value值
            }
        }
        return null; // key不存在，返回null
    }

    /**
     * 打包键值对类型
     */
    static class Entry<K, V>{
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "zhang san");
        map.put(2, "li si");
        map.put(3, "wang wu");
        map.put(4, "gao yang");
        map.put(5, "wang hui");
        map.put(56, "wang hui");
        map.put(78, "wang hui");
        map.put(89, "wang hui");

        System.out.println(map.get(4));
//        System.out.println(map.get(1));
//        System.out.println(map.get(2));
//        System.out.println(map.get(3));
//
//        map.put(1, "wang wu");
//        System.out.println(map.get(1));
//
//        map.remove(2);
//        System.out.println(map.get(2));
    }
}