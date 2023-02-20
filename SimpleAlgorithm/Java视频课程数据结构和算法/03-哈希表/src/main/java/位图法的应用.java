import java.util.ArrayList;
import java.util.Random;

/**
 * 描述:位图法的应用   只能查重   没办法去统计重复次数  0 1
 * 大数据查重复 - 位图法   区别于哈希表大的特点：占用内存空间小很多
 *
 * 问题：有10亿个整数，如何快速的找第一个重复的数字？哪些数字都重复了？
 *
 * 10亿 * 4 = 40亿（bytes）
 * 1亿   -   100M * 4 = 400M
 * 10亿  -   1G * 4 = 4G 数据本身占用的空间
 * 直接用哈希表   4G * 2 = 8G
 *
 * 用位图法（一定要直到数据序列里面的最大值是多少?）
 * 12   arr[12] = null 12
 * value 映射到  数据的某个位  0  1      假如上面的序列，最大值也是10亿
 * byte[] arr = new byte[10亿/8+1]  因为一个字节可以表示8个位  位图数组占用内存大约：400M-500M
 *
 * 位图法的两大缺点：
 * 1. 需要事先直到数列的最大值
 * 2. 1 2 3 10000000       数据的总数量和最大值比较接近!!!
 *    如果此时定义位图数组，就得用10000000数字来定义了，结果就是位图数组很大，但是只有4个位是有效的位，太浪费内存空间了
 *
 * @Author shilei
 * @Date 2019/8/31
 */
public class 位图法的应用 {
    public static void main(String[] args) {

        /**
         * list里面有10万个整数，输出代码求第一个重复的数字、都有哪些数字重复了，第K个重复的数字
         */
        Random rd = new Random();
        ArrayList<Integer> list = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            list.add(rd.nextInt(60000));
        }

        /**
         * 用位图法，找出第一个重复的数字，或者是所有重复的数字
         * 不知道数据里面的最大值，无法定义位图数组的大小！！！
         */
        int max = 0;
        for(Integer val : list){
            if(val > max){
                max = val;
            }
        }

        // 定义位图数组
        byte[] arr = new byte[max/8+1];
        for(Integer val : list){
            // 求出val在位图的哪个位置
            int index = val / 8;
            // 求出val在index号位置的哪一个位上
            int offset = val % 8;

            int state = arr[index] & (1 << offset);
            if(state == 0){ // 表示相应的位是0，该数字没有出现过
                arr[index] = (byte)(arr[index] | (1 << offset));
            } else {
                System.out.println("第一个重复的元素是:" + val);
                return;
            }
        }
    }
}