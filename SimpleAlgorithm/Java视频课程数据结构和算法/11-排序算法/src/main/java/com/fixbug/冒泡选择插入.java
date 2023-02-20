package com.fixbug;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Hello world!
 */
public class 冒泡选择插入
{
    public static void main( String[] args )
    {
        /*Random rd = new Random();
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = rd.nextInt(100) + 1;
        }*/

        /*System.out.println(Arrays.toString(array));
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
        bubbleSort(array, (a, b)->{return Integer.valueOf(a).compareTo(b);});
        System.out.println(Arrays.toString(array));
        bubbleSort(array, (a, b)->{return -Integer.valueOf(a).compareTo(b);});
        System.out.println(Arrays.toString(array));*/

        //choiceSort(array);
        //System.out.println(Arrays.toString(array));

        //insertSort(array);
        //System.out.println(Arrays.toString(array));

        //shellSort(array);
        //System.out.println(Arrays.toString(array));

        final int N = 10000;
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*N);
        }

        long begin, end;
        int[] testarr = Arrays.copyOf(arr, N);

        begin = System.currentTimeMillis();
        bubbleSort(testarr);
        end = System.currentTimeMillis();
        System.out.println("bubble sort time:" + (end - begin) + "ms");

        testarr = Arrays.copyOf(arr, N);
        begin = System.currentTimeMillis();
        choiceSort(testarr);
        end = System.currentTimeMillis();
        System.out.println("choice sort time:" + (end - begin) + "ms");

        testarr = Arrays.copyOf(arr, N);
        begin = System.currentTimeMillis();
        insertSort(testarr);
        end = System.currentTimeMillis();
        System.out.println("insert sort time:" + (end - begin) + "ms");

        testarr = Arrays.copyOf(arr, N);
        begin = System.currentTimeMillis();
        shellSort(testarr);
        end = System.currentTimeMillis();
        System.out.println("shell sort time:" + (end - begin) + "ms");
    }

    /**
     * 希尔排序 - 多路的插入排序 - 先将数据进行分组，然后插排，使整个序里局部趋于有序，那么
     * 整体序列也就趋于有序，插入排序效率最高
     * @param array
     */
    private static void shellSort(int[] array) {
        int n = array.length;
        // 数据分组，设置增量gap是数据量的一半，然后按一半进行缩减
        for(int gap=n/2; gap > 0; gap/=2){
            // 假设0号位元素已经是有序的了
            for(int i=gap; i < n; ++i){  // O(n)
                int val = array[i];
                int j=i-gap;
                for( ;j >= 0; j=j-gap){   // O(n) * O(n) = O(n^2)
                    if(val <= array[j]){
                        array[j+gap] = array[j];
                    } else {
                        break;
                    }
                }
                // 把val值插入到j+1的位置
                array[j+gap] = val;
            }
        }
    }

    /**
     * 实现插入排序的代码
     * @param array
     */
    private static void insertSort(int[] array) {
        int n = array.length;

        // 假设0号位元素已经是有序的了
        for(int i=1; i < n; ++i){  // O(n)
            int val = array[i];
            int j=i-1;
            for( ;j >= 0; --j){   // O(n) * O(n) = O(n^2)
                if(val <= array[j]){
                    array[j+1] = array[j];
                } else {
                    break;
                }
            }
            // 把val值插入到j+1的位置
            array[j+1] = val;
        }
    }

    /**
     * 选择排序
     * @param array
     */
    private static void choiceSort(int[] array) {
        int n = array.length;
        int min = 0; // 记录最小值
        int k = 0; // 记录最小值的下标

        // 处理的趟数 n-1趟
        for(int i=0; i < n-1; ++i){  // O(n)
            min = array[i];
            k = i;
            for(int j=i+1; j < n; ++j){ // O(n) * O(n) = O(n^2)
                if(array[j] < min){
                    min = array[j];
                    k = j;
                }
            }

            // 一趟比较完成，把最小值放在首位 i
            if(k != i){
                int tmp = array[i];
                array[i] = array[k];
                array[k] = tmp;
            }
        }
    }

    /**
     * 冒泡排序代码实现
     * @param array
     */
    private static void bubbleSort(int[] array) {
        int n = array.length; // 10
        boolean flag = false;
        for(int i=0; i < n-1; ++i){ // i表示趟数 0-9 循环9趟   O(n)
            flag = false; // 初始化false，用来记录当前一趟的冒泡排序是否交换过元素
            // 处理每一趟，两两元素比较，小的上浮，大的下沉
            for(int j=0; j < n-i-1; ++j){   // O(n)*O(n) = O(n^2)
                if(array[j] > array[j+1]){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    flag = true;
                }
            }

            // 这一趟下来，没有任何元素进行过交换，已经有序，直接返回
            if(!flag){
                return;
            }
        }
    }

    /**
     * 冒泡排序代码实现
     * @param array
     */
    private static void bubbleSort(int[] array, Comparator<Integer> comp) {
        int n = array.length; // 10
        boolean flag = false;
        for(int i=0; i < n-1; ++i){ // i表示趟数 0-9 循环9趟
            flag = false; // 初始化false，用来记录当前一趟的冒泡排序是否交换过元素
            // 处理每一趟，两两元素比较，小的上浮，大的下沉
            for(int j=0; j < n-i-1; ++j){
                if(comp.compare(array[j], array[j+1]) > 0){
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    flag = true;
                }
            }

            // 这一趟下来，没有任何元素进行过交换，已经有序，直接返回
            if(!flag){
                return;
            }
        }
    }
}