package org.erik.jdk8.demo.annotation;

/**
 * Created by Erik_Yim on 2017/5/17.
 */

@FunctionalInterface
//使用函数式接口@FunctionalInterface表名这个接口中只能包含一个抽象方法的接口,
//编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的。但是默认接口是可以的
 interface TestCallBackInterface<F,T> {

    void callBackFunction(F f, T t);

    default void calllBack() {
        System.out.println("defalult callBack");
    };


    default void calllBack2() {
        System.out.println("defalult callBack2");
    };

}



@FunctionalInterface
//使用函数式接口@FunctionalInterface表名这个接口中只能包含一个抽象方法的接口,
//编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的。但是默认接口是可以的
interface TestCallBackInterface2{

    void callBackFunction(String f, Integer t);

    default void calllBack() {
        System.out.println("defalult callBack");
    };


    default void calllBack2() {
        System.out.println("defalult callBack2");
    };

}


public class TestFunctionInterface {

    public static void main(String[] args) {


        //模拟lambda表达式原理，每一个lambda表达式都会对应一个函数接口，之前没有回调函数之前
        //我们通过定义一个普通接口，然后通过匿名内部类来实现回调功能，有lambda后会更加简单
        //原来就是lambda对应一个函数式接口，其实lambda相当于语法糖，最终实现是代理的这个函数接口去完成的工作
        //之前匿名内部类的实现就定义在lambda表达式中，然后对应的接口就会吧lambda表达式当成接口实现来使用，这个蛮好用的
        TestCallBackInterface<Integer, Long> callBackInterface = (a, b) -> {
            System.out.println(a.getClass());
            System.out.println(b.getClass());
        };


        //这个用来测试lambda可以使用参数自动推导的功能
        //java.util.function 定义了很多函数接口给lambda使用
        TestCallBackInterface2 callBackInterface2 = (a, b) -> {
            System.out.println("demo" + a.getClass());
            System.out.println("demo"  + b.getClass());
        };



        callBackInterface.callBackFunction(10, 5L);


        callBackInterface2.callBackFunction("stringaaa", 22);//这边的值会映射到a和b
//        代码还可以通过静态方法引用来表示：
        //Converter<String, Integer> converter = Integer::valueOf;


    }
}
