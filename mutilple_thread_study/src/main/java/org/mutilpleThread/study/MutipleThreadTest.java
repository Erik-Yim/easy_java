package org.mutilpleThread.study;

public class MutipleThreadTest {

	public static class TreadTest implements Runnable {
		
		int status = 0;
		
		public TreadTest(int status) {
			this.status = status;
		}
		
		
		@Override
		public void run() {
			for(; status < 50; status++) {
				System.out.println(Thread.currentThread().getName() + " status = " + status);
			}
			
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new Thread(new TreadTest(0), "t1");
		Thread t2 = new Thread(new TreadTest(3), "t2");
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

}
