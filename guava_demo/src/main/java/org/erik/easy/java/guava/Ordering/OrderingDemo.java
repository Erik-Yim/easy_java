package org.erik.easy.java.guava.Ordering;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.Comparator;
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
        //onResultOf(Function)	对集合中元素调用Function，再按返回值用当前排序器排序。
        Ordering<String> stringOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<String, String>() {
            @Override
            public String apply(String filed) {
                return filed;
            }
        });


        //根据foo对象的sortedFiled的字段的值转成数字来判断排序
        Ordering<Foo> numberOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<Foo, String>() {
            @Override
            public String apply(Foo foo) {
                return foo.sortedFiled;
            }
        });


//      可以和jdkComparator合并使用
        Ordering<Foo> fromOrder = Ordering.from(new Comparator<Foo>() {
            //比较复杂的比较器可以用这个来构造，多个字段不同业务的
            @Override
            public int compare(Foo o1, Foo o2) {
//                System.out.println("o1:" + o1.sortedFiled + " o2:" + o2.sortedFiled);
              return  o1.sortedFiled.length()  - o2.sortedFiled.length();

            }
        }).compound(numberOrdering);//合成另一个比较器，以处理当前排序器中的相等情况。


        Ordering<Foo> fromOrder2 = new Ordering<Foo>() {

            @Override
            public int compare(Foo foo, Foo t1) {
                return Ints.compare(foo.sortedFiled.length(), t1.sortedFiled.length());
            }
        };





        List<String> list = Arrays.asList("5",null,"3","1","9");

        System.out.println(stringOrdering.sortedCopy(list));

        List<Foo> listFoo = Arrays.asList(new Foo("erik","5"),new Foo("tim","123"),new Foo("tim2","23"),new Foo("tim3","023"));
        System.out.println(ordering.sortedCopy(listFoo));
        System.out.println("fromOrder 如果只相等则在按照numberOrdering排序器处理" + fromOrder.sortedCopy(listFoo));
        System.out.println("fromOrder2" + fromOrder2.sortedCopy(listFoo));

//      返回迭代器中最小的元素。如果可迭代对象中没有元素，则抛出NoSuchElementException。
        System.out.println(fromOrder.min(listFoo));
//      获取最大的
        System.out.println(fromOrder.max(listFoo));
//        获取可迭代对象中最大的k个元素。
        System.out.println(fromOrder.greatestOf(listFoo, 2));
//        判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。
        System.out.println(fromOrder.isOrdered(listFoo));
        List<Foo> orderedListFoo = fromOrder.sortedCopy(listFoo);
        //判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。判断这个是不是集合是不是已经排序的
        //请注意这个isOrdered方法，他是判断是不是被同一个比较器或者不同比较器但是导致结果是一样的的集合判断是否排序，(一般使用时肯定是针对同一个排序来调用，不然也没有意义)
        //List<Foo> orderedListFoo = fromOrder2.sortedCopy(listFoo); 如果用不同的比较器导致排序结果不一样，再用这个排序器的isOrdered判断就是false
        System.out.println(fromOrder.isOrdered(orderedListFoo));
        System.out.println("111");
    }


}
