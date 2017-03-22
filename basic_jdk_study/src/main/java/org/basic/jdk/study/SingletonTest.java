package org.basic.jdk.study;

public enum SingletonTest {
	
	INSTANCE;
	
	private Resource instance;
	
	private SingletonTest() {
		instance = new Resource();
	}
	
	
	public Resource getInstance() {
		return instance;
	}
}


class Resource {}