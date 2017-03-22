package org.basic.jdk.study;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

	private static CountDownLatch cl = new CountDownLatch(100);
	
	
	public static void main(String[] args) {
		System.out.println(cl.getCount());
	}
	
}
