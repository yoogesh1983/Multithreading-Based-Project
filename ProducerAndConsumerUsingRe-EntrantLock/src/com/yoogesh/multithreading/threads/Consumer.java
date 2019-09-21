package com.yoogesh.multithreading.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import com.yoogesh.multithreading.database.MyQueue;

public class Consumer extends Thread {

	private MyQueue mq;
	private CountDownLatch latch;
	private ReentrantLock lock;

	public Consumer(MyQueue mq, ReentrantLock lock, CountDownLatch latch) {
		this.mq = mq;
		this.latch = latch;
		this.lock = lock;
	}

	@Override
	public void run() 
	{
		while (!MyQueue.stop) 
		{
			lock.lock();
				removeItem();
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
	private void removeItem() {
		System.out.println("Consumer aquired the lock.Going to remove the number from a queue now.");

		if (mq.getQueue().size() == 0) {
			System.out.println(
					"Queue is empty. So Comsumer is waiting for any number added in a queue. Going to release the lock now.");
		}
		else {
			System.out.println("Current Items in a Queue: " + mq.getQueue());
			System.out.println("Going to remove item in a queue: " + mq.getQueue().peek());
			mq.remove();
			System.out.println("Queue after removing the item: " + mq.getQueue());
			System.out.println("Item is removed. Going to release the luck now so that producer can use it.");
		}
	}
}
