package org.erik.easy.java.guava.primitives;

import com.google.common.primitives.Ints;

import java.util.List;

/**
 * Created by Erik_Yim on 2017/5/17.
 */
public class IntsDemo {

    //参考http://ifeve.com/google-guava-primitives/
    public static void main(String[] args) {
        System.out.println(Ints.compare(2,3));

        List<Integer> intList = Ints.asList(1,2,3,4,5,6);

        int[] intArray = Ints.toArray(intList);
        Ints.contains(intArray,5);
    }
}
