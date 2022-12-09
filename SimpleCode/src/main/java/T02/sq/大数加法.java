package T02.sq;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 用链表实现两个大数的加法，输出加法的结果
 */
public class 大数加法 {
    public static void main(String[] args) {
        test2();
    }

    static void test() {
        String str1 = "3862957867832453463457";
        String str2 = "5678564534397685764";

        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        LinkedList<Integer> result = new LinkedList<>();

        for (int i = 0; i < str1.length(); i++) {
            list1.addFirst(str1.charAt(i) - '0');
        }

        for (int i = 0; i < str2.length(); i++) {
            list2.addFirst(str2.charAt(i) - '0');
        }

        Iterator<Integer> iterator1 = list1.iterator();
        Iterator<Integer> iterator2 = list2.iterator();

        boolean flag = false;
        while (iterator1.hasNext() && iterator2.hasNext()) {
            int left = iterator1.next();
            int right = iterator2.next();

            int ret = left + right;

            if (flag) { //之前有产生进位，处理进位
                ret += 1;
                flag = false;
            }

            if (ret >= 10) { //ret >= 10 处理进位
                ret %= 10;
                flag = true;
            }
            result.addFirst(ret);
        }


        while (iterator1.hasNext()) {
            int ret = iterator1.next();
            if (flag) {
                ret += 1;
                flag = false;
            }

            if (ret >= 10) {
                ret &= 10;
                flag = false;
            }
            result.addFirst(ret);
        }

        while (iterator2.hasNext()) {
            int ret = iterator2.next();
            if (flag) {
                ret += 1;
                flag = false;
            }

            if (ret >= 10) {
                ret &= 10;
                flag = false;
            }
            result.addFirst(ret);
        }

        if (flag) {
            result.addFirst(1);
        }

        for (int val : result) {
            System.out.print(val);
        }

    }

    static void test2() {
        char a = '8';
        char b = '9';

        System.out.println(a);
        System.out.println(a - 0);
        System.out.println(a - '0');
    }
}
