package org.erik.jdk8.demo.stream;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream流
 * http://www.cnblogs.com/java-zhao/p/5492122.html
 * Created by zhangtian on 2017/5/8.
 */
public class TestStream {
    // 创建Stream
    @Test
    public void testCreateStream(){
        List<String> strList = Arrays.asList("zhangsan", "lisi","nana", "wangwu", "zhaoliu", "xuqi", "nana") ;
        Stream<String> streamList = strList.stream() ;
        strList = streamList.distinct().filter(str->!str.equals("lisi")).sorted(String::compareTo).collect(Collectors.toList());
        strList.forEach(System.out::println);

        // 合并
        Arrays
                .asList("zhangsan", "lisi","nana", "wangwu", "zhaoliu", "xuqi", "nana")
                .stream()
                .distinct()
                .filter(str->!str.equals("zhangsan"))
                .sorted(String::compareTo)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("========================= 分隔符 ==========================");
        // 创建Stream
        /*
            集合-->Stream：stream()
            数组-->Stream：Stream.of(T t)或者Arrays.stream(T[] t)
            任意元素-->Stream：Stream.of(T... values)
         */
        strList = Arrays.asList("zhaojigang","nana","tianya","nana");
        streamList = strList.stream();// 集合转为stream

        String[] strArray = {"java", "c", "c++"} ;
        Stream<String> streamArray = Stream.of(strArray) ;// 数组转为Stream
        Stream<String> streamArray2 = Arrays.stream(strArray) ;// 数组转为Stream

        Stream<String> streamPartArray = Arrays.stream(strArray, 0, 2) ;// 转换部分数组，范围：[0,2)

        Stream<String> streamSelf = Stream.of("python", "java", ".net") ;// 任意元素
    }

    // Stream 2 array/collection/String/map
    @Test
    public void testStreamChange(){
        // 1、stream2array
        Stream<String> strStream = Stream.of("java","c++","c","python");
        Object[] objectArray = strStream.toArray();// 只能返回Object[]
        // 通过构造器引用（类似于方法引用），可以构造出具体类型的数组。
        // 这里stream已经转换为Object了，流已经关闭了，会报错，需要把转换为Object部分的代码注释掉
        strStream = Stream.of("java","c++","c","python");
        String[] strArray = strStream.toArray(String[]::new);// 构造器引用（类似于方法引用）,可以返回String[]
        System.out.println(objectArray);
        System.out.println(strArray);

        System.out.println("========================= 分隔符 ==========================");
        // 2、stream2collection
        strStream = Stream.of("java","c++","c","python");
        List<String> strList = strStream.collect(Collectors.toList());// 返回List
        System.out.println(strList);

        strStream = Stream.of("java","c++","c","python");
        Set<String> strSet = strStream.collect(Collectors.toSet());// 返回set
        System.out.println(strSet);

        strStream = Stream.of("java","c++","c","python");
        ArrayList<String> strArrayList = strStream.collect(Collectors.toCollection(ArrayList::new));// 收集到指定的List集合，例如收集到ArrayList
        System.out.println(strArrayList);

        System.out.println("========================= 分隔符 ==========================");
        // 3、将stream中的元素拼接起来（joining()、joining(",")）
        strStream = Stream.of("java","c++","c","python");
        String str = strStream.collect(Collectors.joining());// 将所有字符串拼接起来，结果：javac++cpython
        System.out.println(str);

        strStream = Stream.of("java","c++","c","python");
        String str2 = strStream.collect(Collectors.joining(","));//将所有字符串拼接起来，中间用","隔开，结果：java,c++,c,python
        System.out.println(str2);

        System.out.println("========================= 分隔符 ==========================");
        // 4、stream2map(toMap、toConcurrentMap)
        strStream = Stream.of("java","c++","c","python");
        // Function.identity()-->返回strStream中的元素，toMap方法的我两个参数都是Function接口型的，所以第二个参数即使只放0，也不能直接写作0，可以使用如下的方式进行操作
        Map<String, Integer> map1 = strStream.collect(Collectors.toMap(Function.identity(), (x)->0));
        // 如果key重复的话，这时就会出现问题"duplicate key"，采用如下方式解决（增加第三个参数）：
        // Map<String, Integer> map1 = strStream.collect(Collectors.toMap(Function.identity(), (x)->0, (existingValue, newValue)->existingValue));
        // 需要指定返回map的具体类型（增加第四个参数）。
        // Map<String, Integer> map1 = strStream.collect(Collectors.toMap(Function.identity(), (x)->0, (existingValue, newValue)->existingValue, TreeMap::new));

        // 注意：每一个toMap就会对应一个相应的toConcurrentMap
        for(String key : map1.keySet()) {
            System.out.println("key:"+key+", value:"+map1.get(key));
        }

        System.out.println("========================= 分隔符 ==========================");
        // 5、groupingBy partitioningBy
        Stream<Locale> localeStream = Stream.of(Locale.getAvailableLocales());
        Map<String, List<Locale>> country2localeList = localeStream.collect(Collectors.groupingBy(Locale::getCountry));// 根据国家分组，groupBy的参数是分类器
        List<Locale> locales = country2localeList.get("CH");
        System.out.println(locales);

        localeStream = Stream.of(Locale.getAvailableLocales());
        // 根据国家分组，groupBy的参数是分类器，返回set
        Map<String, Set<Locale>> country2localeSet = localeStream.collect(Collectors.groupingBy(Locale::getCountry, Collectors.toSet()));
        Set<Locale> localeSet = country2localeSet.get("CH");
        System.out.println(localeSet);

        localeStream = Stream.of(Locale.getAvailableLocales());
        //分成两组，一组为true（即语言是en的），一组为false（即语言不是en的）
        Map<Boolean, List<Locale>> country2locales = localeStream.collect(Collectors.partitioningBy(locale->locale.getLanguage().equals("en")));
        List<Locale> trueLocale = country2locales.get(true);
        System.out.println(true);
    }

    // filter(Predicate p)
    @Test
    public void testFilter() {
        // 注意：是选择而非过滤。
        Stream<String> streamSelf = Stream.of("python","basic","php");
        // stream也是可以foreach的，没必要一定要转化成集合再foreach
        streamSelf.filter(str->str.startsWith("p")).forEach(System.out::println);

        // 更好的写法可能是下边这种：将条件（通常是lambda表达式）抽取出来。这种方式在多个条件的情况下比较清晰。
        streamSelf = Stream.of("python","basic","php");
        Predicate<String> startCondition = str -> str.startsWith("p") ;
        streamSelf.filter(startCondition).forEach(System.out::println);

        // 注意：函数式接口 = lambda表达式 （即lambda表达式只能返回为函数式接口）
        Stream<String> s = Stream.of("java1","java3","java","php12");
        Predicate<String> condition1 = str->str.length()==5;// 条件1
        Predicate<String> condition2 = str->str.startsWith("j");// 条件2
        // 多条件运算：and or
        s.filter(condition1.and(condition2)).forEach(System.out::println);//and条件
    }

    // map(Function mapper)
    @Test
    public void testMap() {
        // 作用：对流中的每一个元素进行操作。
        Stream<String> streamSelf = Stream.of("python","basic","php");
        streamSelf.map(String::toUpperCase).forEach(System.out::println);
    }

    // reduce 以上是reduce的简单形式，即内联函数是(T,T)->T，即返回值和参数类型是一样的，返回值和参数类型不同的场景需要自己编写函数（用的较少）
    @Test
    public void testReduce(){
        // 作用：对stream中的每一个元素做聚合操作。
        Stream<Integer> reduceStream = Stream.of(1,2,3,4,5);
        // 计算1+2+3+4+5，即对元素中的元素进行聚合计算，而map是对元素中的每一个元素分别计算（注意：如果stream为null的话，就会产生无效的结果，需要使用Optional接收）
        Optional<Integer> sumOption = reduceStream.reduce((x, y)->x+y) ;
        System.out.println(sumOption.get());
        // Optional<Integer> sumOption = reduceStream.reduce(Integer::sum);//计算1+2+3+4+5，即对元素中的元素进行聚合计算，而map是对元素中的每一个元素分别计算

        reduceStream = Stream.of(1,2,3,4,5,6);
        Integer result = reduceStream.reduce(0, Integer::sum) ;// 0为标识值，即计算：0+1+2+。。+5，如果整个stream为null，就返回标识值。
        System.out.println(result);
    }

    /**
     * Optional
     * 两种用法：
     * ifPresent(xxx)：存在的就执行xxx，不存在就什么都不执行
     * orElse(xxx)：存在就返回存在的值，不存在就返回xxx（可以理解为是默认值）
     */
    @Test
    public void testOptional() {
        Stream<String> optionalStream = Stream.of("java","python","basic");
        Optional<String> optionValue = optionalStream.filter(str->str.startsWith("p")).findFirst() ;

        optionValue.ifPresent(str-> System.out.println(str));// if optionalValue为true，即str存在，则输出str，当然也可以使用如下

        String str = optionValue.orElse("xxx") ;// 如果optionValue为false，即不存在以p开头的字符串时，使用"xxx"来替代
        System.out.println(str);
    }

    // limit skip contact
    @Test
    public void testLimitSkipContact() {
        // 1、limit(long size) 作用：截取stream的前size个元素。
        System.out.println("========================= 分隔符 ==========================");
        Stream<String> streamSelf = Stream.of("python","basic","php");
        streamSelf.limit(2).forEach(System.out::println);//截取前两个

        System.out.println("========================= 分隔符 ==========================");
        // 2、skip(long size) 作用：跳过stream的前size个元素
        streamSelf = Stream.of("python","basic","php");
        streamSelf.skip(2).forEach(System.out::println);//跳过前两个

        System.out.println("========================= 分隔符 ==========================");
        // 3、contact(Stream<T>,Stream<T>)  作用：拼接两个stream
        streamSelf = Stream.of("python","basic","php");
        Stream streamSelf2 = Stream.of("python2","basic2","php2");
        Stream.concat(streamSelf, streamSelf2).forEach(System.out::println);
    }

    // 聚合函数 count max min findFirst findAny anyMatch allMatch noneMatch
    // optional的最佳用法：ifPresent()-->如果有就输出，如果没有，什么都不做
    // parallel()：将stream转为并行流，并行流的使用一定要注意线程安全
    @Test
    public void testCountMaxEsc() {
        Stream<String> streamSelf = Stream.of("python","basic","php","b");
        System.out.println(streamSelf.count());// 计算流中的元素个数

        // 注意：Optional的使用，该使用方法的是最差的一种形式，见"六"。
        streamSelf = Stream.of("python","basic","php","b");
        Optional<String> largest = streamSelf.max(String::compareToIgnoreCase) ;
        if(largest.isPresent()) {
            System.out.println(largest.get());
        }

        streamSelf = Stream.of("python","basic","php","b");
        Optional<String> firstMatch = streamSelf.filter(str->str.startsWith("b")).findFirst();// 寻找第一个符合条件的元素
        streamSelf = Stream.of("python","basic","php","b");
        Optional<String> anyMatch = streamSelf.parallel().filter(str->str.startsWith("b")).findAny();// 返回集合中符合条件的任意一个元素，对于并行处理非常好（因为多个线程只要有一个线程找到了，整个计算就会结束）
        if(anyMatch.isPresent()) {
            System.out.println(anyMatch.get());//这里的结果可能是b,有可能是basic
        }

        streamSelf = Stream.of("python","basic","php","b"); streamSelf = Stream.of("python","basic","php","b");
        boolean isAnyMatch = streamSelf.parallel().anyMatch(str->str.startsWith("c"));//集合中是否有一个满足条件
        System.out.println(isAnyMatch);

        Stream<String> streamSelf3 = Stream.of("basic","b");
        boolean isAllMatch = streamSelf3.parallel().allMatch(str->str.startsWith("b"));//集合中是否所有元素都满足条件
        System.out.println(isAllMatch);

        streamSelf = Stream.of("python","basic","php","b");
        boolean isAllNotMatch = streamSelf.parallel().noneMatch(str->str.startsWith("p"));//集合中是否没有一个元素满足条件
        System.out.println(isAllNotMatch);
    }
}
