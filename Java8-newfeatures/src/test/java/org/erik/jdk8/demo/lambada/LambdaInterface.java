package org.erik.jdk8.demo.lambada;

/**
 * Created by zhangtian on 2017/5/8.
 */
public interface LambdaInterface {
    // default 方法
    default void defaultMethod() {
        System.out.println("xxxxx");
    }

    // static 方法
    static void staticMethod(){
        System.out.println("xxxxx");
    }
}
