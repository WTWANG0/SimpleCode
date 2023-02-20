package com.fixbug.expression;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 描述:四则运算表达式的求解 =》 求解后缀表达式（逆波兰表达式）的值
 *
 * @Author shilei
 * @Date 2019/8/30
 */
public class 四则运算表达式的求解 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("输入四则运算表达式:");
        String expression = in.nextLine();

        // 用来存储数字，因为可能出现多位数的数字，需要用字符串进行存储
        StringBuilder sb = new StringBuilder();
        // 存储后缀表达式的内容
        LinkedList<String> backExpression = new LinkedList<>();
        // 存储符号的栈
        LinkedList<Character> signStack = new LinkedList<>();

        // 开始遍历中缀表达式
        for(int i=0; i<expression.length(); ++i){
            if(Character.isDigit(expression.charAt(i))){
                // 主要考虑数字有可能是多位数，所以要缓存到sb字符串中
                sb.append(expression.charAt(i));
            } else {
                // 当前遍历到一个符号上了
                if(sb.length() > 0){
                    // 刚把一个数字遍历完，直接输出到后缀表达式中
                    backExpression.add(sb.toString());
                    sb.delete(0, sb.length());
                }

                // 处理当前遍历的符号了
                if(signStack.isEmpty() || (expression.charAt(i) == '(')){ // #1 #2
                    signStack.push(expression.charAt(i));
                } else {
                    if(expression.charAt(i) == ')'){ // #3
                        while(!signStack.isEmpty()){
                            char ch = signStack.pop();
                            if(ch == '('){
                                break;
                            }
                            backExpression.add(String.valueOf(ch));
                        }
                    } else {
                        // #4 #5 判断当前符号和栈顶符号的优先级   true   false
                        while(!signStack.isEmpty()
                                && !priority(expression.charAt(i), signStack.peek())){
                            backExpression.add(String.valueOf(signStack.pop()));
                        }
                        signStack.push(expression.charAt(i));
                    }
                }
            }
        }

        // 处理一个边界情况  12 - 98
        if(sb.length() > 0){
            backExpression.add(sb.toString());
        }

        while (!signStack.isEmpty()){
            backExpression.add(String.valueOf(signStack.pop()));
        }

        System.out.println(backExpression);

        // 开始计算后缀表达式的结果
        LinkedList<Integer> digitStack = new LinkedList<>();
        // 遍历后缀表达式
        for(String val : backExpression){
            if(isDigit(val)){ // 是数字
                digitStack.push(Integer.valueOf(val));
            } else {
                int right = digitStack.pop();
                int left = digitStack.pop();
                int ret = 0;
                switch (val){
                    case "+":
                        ret = left + right;
                        break;
                    case "-":
                        ret = left - right;
                        break;
                    case "*":
                        ret = left * right;
                        break;
                    case "/":
                        ret = left / right;
                        break;
                }
                digitStack.push(ret);
            }
        }

        System.out.println("表达式的结果是:" + digitStack.pop());
    }

    /**
     * 判断str字符串是否全部都是数字
     * @param str
     * @return
     */
    private static boolean isDigit(String str) {
        for(int i=0; i<str.length(); ++i){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断符号优先级
     * @param a  当前遍历的符号
     * @param b  栈顶符号
     * @return
     */
    private static boolean priority(char a, Character b) {
        switch (a){
            case '+':
            case '-':
                switch (b){
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        return false;
                    case '(':
                        return true;
                }
                break;
            case '*':
            case '/':
                switch (b){
                    case '*':
                    case '/':
                        return false;
                    case '+':
                    case '-':
                    case '(':
                        return true;
                }
                break;
        }
        return false;
    }
}