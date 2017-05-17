package org.erik.easy.java.guava.Ordering;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Erik_Yim on 2017/5/17.
 */



class Foo {

    String name;

    String sortedFiled;

    public Foo() {}
    public Foo(String name, String sortedFiled) {
        this.name = name;
        this.sortedFiled = sortedFiled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"sortedFiled\":\"")
                .append(sortedFiled).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
//http://ifeve.com/google-guava-ordering/

public class OrderingDemo {

    public static void main(String[] args) {
        Ordering<Foo> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Foo, String>() {
            @Override
            public String apply(Foo foo) {
                return foo.sortedFiled;
            }
        });



        //当阅读链式调用产生的排序器时，应该从后往前读。
        // 上面的例子中，排序器首先调用apply方法获取sortedBy值，并把sortedBy为null的元素都放到最前面，
        // 然后把剩下的元素按sortedBy进行自然排序。之所以要从后往前读，是因为每次链式调用都是用后面的方法包装了前面的排序器。
        Ordering<String> stringOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<String, String>() {
            @Override
            public String apply(String filed) {
                return filed;
            }
        });

        List<String> list = Arrays.asList("5",null,"3","1","9");

        System.out.println(stringOrdering.sortedCopy(list));

        List<Foo> listFoo = Arrays.asList(new Foo("erik","5"),new Foo("tim","123"));
        System.out.println(ordering.sortedCopy(listFoo));

        System.out.println("111");
    }



}
