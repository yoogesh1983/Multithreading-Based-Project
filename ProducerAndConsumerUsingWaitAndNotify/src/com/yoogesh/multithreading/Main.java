package com.yoogesh.multithreading;

import com.yoogesh.multithreading.database.MyQueue;
import com.yoogesh.multithreading.threads.Consumer;
import com.yoogesh.multithreading.threads.Producer;

public class Main {
	
public static void main(String[] args) throws InterruptedException {
	
	MyQueue mq = new MyQueue();
	Producer t1 = new Producer(mq);
	Consumer t2 = new Consumer(mq);
	
	t1.start();
	t2.start();
	
	
	//Main thread should wait until t1 and t2 complete their task. Only after that, the program can be terminated
	t1.join();
	t2.join();
	
	System.out.println("Main thread finally completes the execution and going to terminate the program safely.");
}
}
