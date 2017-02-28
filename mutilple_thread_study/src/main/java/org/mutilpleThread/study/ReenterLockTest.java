package org.mutilpleThread.study;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockTest implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	public static int i = 0;

	@Override
	public void run() {
		for(int j = 0; j < 10000; j++) {
			lock.lock();
			try{
				i++;
				System.out.println(Thread.currentThread().getName());
			} finally {
				lock.unlock();
			}
		}

	}
	
	public static void main(String[] args) throws InterruptedException {
		ReenterLockTest tl = new ReenterLockTest();
	    Thread t1 = new Thread(tl);
		Thread t2 = new Thread(tl);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}

}
