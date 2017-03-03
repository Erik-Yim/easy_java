package org.mutilpleThread.study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
	
	public static class MyTask implements Runnable {

		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + " ：Thread id :" + Thread.currentThread().getName());
			try{
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	

	public static void main(String[] args) {
		
		MyTask task = new MyTask();
//		ExecutorService  es = Executors.newFixedThreadPool(4);
		/*使用场景：1. 耗时较短的任务。2. 任务处理速度 > 任务提交速度 ，
		这样才能保证不会不断创建新的进程，避免内存被占满。
		取名为cached-threadpool的原因在于线程池中的线程是被线程池缓存了的，
		也就是说，线程没有任务要执行时，便处于空闲状态，
		处于空闲状态的线程并不会被立即销毁（会被缓存住），只有当空闲时间超出一段时间(默认为60s)后，
		线程池才会销毁该线程（相当于清除过时的缓存）。新任务到达后，线程池首先会让被缓存住的线程（空闲状态）去执行任务，如果没有可用线程（无空闲线程），
		便会创建新的线程。*/

/*		楼主只需要把各种创建线程池的根源：    public ThreadPoolExecutor(int corePoolSize,
                int maximumPoolSize,
                long keepAliveTime,
                TimeUnit unit,
                BlockingQueue<Runnable> workQueue,
                ThreadFactory threadFactory,
                RejectedExecutionHandler handler)
这个构造函数和他的参数全都吃透，就行了。不要纠结用哪个静态方法。
*/

		
		
		ExecutorService  es = Executors.newCachedThreadPool();
		for(int i = 0; i < 100; i++) {
			es.submit(task);
		}
	}

}
