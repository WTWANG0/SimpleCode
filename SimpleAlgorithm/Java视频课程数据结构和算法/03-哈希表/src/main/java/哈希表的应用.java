
import java.util.*;

/**
 * 描述: 哈希表的应用
 * 优势：查询 O(1)   劣势：占用内存比较大   64G的整数（int） 一个节点  data，next
 *                                 用HashSet存储64G的整数，整个哈希表将占用64*2 = 128G的内存
 * 大数据查重/去重（查询时间复杂度O(1)）  HashSet key
 * 统计重复次数 HashMap key,value
 *
 * @Author shilei
 * @Date 2019/8/30
 */
public class 哈希表的应用 {
    public static void main(String[] args) {
        /**
         * list里面有10万个整数，输出代码求第一个重复的数字、都有哪些数字重复了，第K个重复的数字
         */
        Random rd = new Random();
        ArrayList<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(rd.nextInt(60));
        }

        /**
         * 查询数字重复
         * 遍历原始的数据，在哈希表中进行查询，如果不存在，表示该数据第一次出现；
         * 如果该数据在哈希表中找到了，说明该数据重复了
         */
        /*HashSet<Integer> set = new HashSet<>();
        for(Integer val : list){
            if(set.contains(val)){ // O(1)
                System.out.println(val + "是第一个重复的数字!");
                return;
            } else {
                set.add(val); // O(1)
            }
        }
        System.out.println();*/

        /**
         * list里面有10万个整数，有的数字是重复的，让你把重复的数字过滤掉只剩下一个 => 去重的操作
         * 利用HashSet本身就不允许key值重复这么一个特征
         */
        /*HashSet<Integer> set = new HashSet<>();
        for(Integer val : list){
            set.add(val);
        }
        System.out.println(set.size());*/

        /**
         * list里面有10万个整数，让你统计重复的数字以及它们重复的次数
         *                                 key         value
         *                                 key:数字本身的值    value：该数字重复的次数
         */
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Integer key : list){
            Integer cnt = map.get(key);
            if(cnt == null){ // O(1)
                map.put(key, 1);
            } else {
                map.put(key, cnt+1);
            }
        }

        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, Integer> entry = it.next();
            // entry.getValue() > 1 表示数字重复了
            if(entry.getValue() > 1){
                System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
            }
        }
    }
}