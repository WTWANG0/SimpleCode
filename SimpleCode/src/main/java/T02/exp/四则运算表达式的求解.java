package T02.exp;


import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 描述:四则运算表达式的求解 =》 求解后缀表达式（逆波兰表达式）的值
 */
public class 四则运算表达式的求解 {
    public static void main(String[] args) {
        test();
    }

    static void test() {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入四则运算表达式");
        String expression = sc.nextLine();
        // 用来存储数字，因为可能出现多位数的数字，需要用字符串进行存储
        StringBuffer sb = new StringBuffer();
        // 存储后缀表达式的内容
        LinkedList<String> backExpression = new LinkedList<>();
        // 存储符号的栈


        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                sb.append(expression.charAt(i));
            } else {
                if (sb.length() > 0) {
                    backExpression.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }

        }

    }
}
