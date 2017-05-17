package org.erik.jdk8.demo.lambada;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * java8新特性之lambda表达式
 * http://www.cnblogs.com/java-zhao/p/5491078.html
 * Created by zhangtian on 2017/5/8.
 */
public class TestLambda {
    public static void main(String[] args) throws InterruptedException {
        // 处理匿名内部类  Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World...");
            }
        }).start();

        new Thread(()-> System.out.println("Hello Lambda!")).start();

        System.out.println("========================= 分隔符 ==========================");
        Thread.sleep(3000);
        // 处理匿名内部类  Comparator
        List<String> strList = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu", "xuqi") ;
        // 原来的方式
        Collections.sort(strList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        System.out.println(strList);

        // lambda
        Collections.sort(strList,(s1, s2) -> s2.compareTo(s1));
        System.out.println(strList);

        System.out.println("========================= 分隔符 ==========================");
        // 方法引用
        // lambda
        Collections.sort(strList, (s1, s2)->s1.compareTo(s2));
        // 方法引用
        Collections.sort(strList, String::compareTo);
        // lambda
        strList.forEach(x-> System.out.println(x));
        // 方法引用
        strList.forEach(System.out::println);

        System.out.println("========================= 分隔符 ==========================");
        // 局部变量 lambda操作的局部变量必须是final型的，即：在lambda表达式所使用到的局部变量（方法体内的变量或形参）只能被读取，不能被改变。

        System.out.println("========================= 分隔符 ==========================");
        // 接口的改变
    }
}
