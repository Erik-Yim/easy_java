package org.erik.jdk8.demo.lambada;

/**
 * 当改造老的项目时，想在旧的接口中添加一些方法，但是又不想让该接口的旧的实现类去实现这些方法时，可以使用这个技巧。
 * Created by zhangtian on 2017/5/8.
 */
public class TestInterface implements LambdaInterface {
    public static void main(String[] args) {
        LambdaInterface test = new TestInterface() ;
        test.defaultMethod();// default方法测试

        LambdaInterface.staticMethod();// static方法测试
    }
}
