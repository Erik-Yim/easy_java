package org.basic.jdk.study;

public class JumpFlorDemo {

	public static long count(long n) {
		if(n == 1) {
			return 1;
		} else if(n == 2) {
			return 2;
		} else {
			return count(n-1) + count(n-2);
		}
			
		
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(count(50));
	}
	
}
