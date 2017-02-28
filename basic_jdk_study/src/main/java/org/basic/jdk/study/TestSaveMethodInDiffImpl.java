package org.basic.jdk.study;


/**
 * @author Erik_Yim
 *
 *一个类继承了两个接口，这两个接口中有相同的方法，则都可以运行
 */
public class TestSaveMethodInDiffImpl implements InterfaceOne, InterfaceTwo {

	@Override
	public void run(String abc) {
		
		System.out.println("this abc ccccc hhhh");

	}
	
	public static void main(String[] args) {
		
		InterfaceOne in1 = new TestSaveMethodInDiffImpl();
		InterfaceTwo in2 = new TestSaveMethodInDiffImpl();
		
		if(in1 instanceof InterfaceTwo) {
			in1.run("123");
		}
		
		if(in1 instanceof InterfaceOne) {
			in1.run("123");
		}
	
		
		
		
	}

}
