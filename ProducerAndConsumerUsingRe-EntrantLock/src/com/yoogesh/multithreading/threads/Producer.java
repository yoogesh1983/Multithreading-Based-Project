package com.yoogesh.multithreading.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import com.yoogesh.multithreading.database.MyQueue;

public class Producer extends Thread {
	
	private static final int MAX = 100;
	private MyQueue mq;
	private CountDownLatch latch;
	private ReentrantLock lock;
	
	public Producer(MyQueue mq, ReentrantLock lock, CountDownLatch latch) {
		this.mq = mq;
		this.latch = latch;
		this.lock = lock;
	}

	@Override
	public void run() 
	{
		for(int i = 0; i < MAX; i++) 
		{
				lock.lock();
					addItem(i);
				lock.unlock();
		}		
		latch.countDown();
	}

	
	/**
	 * Theoritically we should not have this seperate methd and all lines should be inside the above synchronized block.
	 * The reason behind this is because this method is not synchronized and hence can be called from other non-synchronized method too and 
	 * that's where the problem occurs. But here, since it is only called from synchronized block, the problem not occured.
	 * Also i have done this to show the clearness. but in real scenario, don't do it like this and always put inside synchronized block above directly.
	 */
	private void addItem(int i) {
		System.out.println("Producer aquired the lock.Going to add number in a queue now.");
		int radomNumber = (int) (Math.random()*100);
		mq.add(radomNumber);
		System.out.println(radomNumber + " is added to the queue. Now going to release the lock so that consumer can consume it.");
		if(i == MAX - 1) {
			System.out.println("By the way, No more queue will be added. Final items in a queue with size "+  mq.getQueue().size() + " is : " + mq.getQueue());
			MyQueue.ProducerFinishedTask = true;
		}
	}

}
