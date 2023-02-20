package com.fixbug.expression;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 描述:中缀表达式转后缀表达式
 * 思想：遍历中缀表达式，遇到数字直接输出，遇见符号要进行相应处理再输出
 * 1. 如果栈为空，直接入栈运算符
 * 2. 如果是(括号，直接入栈
 * 3. 如果遇见)括号，连续出栈直到(括号出栈为止
 * 4. 当前符号的优先级 > 栈顶符号，直接入栈
 * 5. 当前符号的优先级 <= 栈顶符号，栈顶符号一直出栈
 *
 * @Author shilei
 * @Date 2019/8/29
 */
public class 中缀表达式转后缀表达式 {
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