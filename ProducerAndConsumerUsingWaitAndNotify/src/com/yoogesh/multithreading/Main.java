package com.yoogesh.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yoogesh.multithreading.database.MyQueue;
import com.yoogesh.multithreading.threads.Consumer;
import com.yoogesh.multithreading.threads.Producer;

public class Main {
	
public static void main(String[] args) throws InterruptedException {
	
	MyQueue mq = new MyQueue();
	CountDownLatch latch = new CountDownLatch(2);  /* This is a replacement of traditional join() method */
	
	Producer t1 = new Producer(mq, latch);
	Consumer t2 = new Consumer(mq, latch);
	
	/*
	t1.start();
	t2.start();
	
	//Main thread should wait until t1 and t2 complete their task. Only after that, the program can be terminated
	t1.join();
	t2.join();
	
	System.out.println("Main thread finally completes the execution and going to terminate the program safely.");
	*/
		
	startThreadInaModernWay(t1, t2, latch);

}

private static void startThreadInaModernWay(Producer t1, Consumer t2, CountDownLatch latch)
		throws InterruptedException {
	Thread[] jobs = { t1, t2 };
	ExecutorService service = Executors.newFixedThreadPool(2);
	for(Thread job : jobs)
	{
      service.submit(job);
    }
	service.shutdown();
	
	//Main thread should wait until t1 and t2 complete their task. Only after that, the program can be terminated.Since Join doesn't work here, so using countDownLatch
	latch.await();
	
	System.out.println("Main thread finally completes the execution and going to terminate the program safely.");
}
}
