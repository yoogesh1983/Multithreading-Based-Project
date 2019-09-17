package com.yoogesh.multithreading.threads;

import com.yoogesh.multithreading.database.MyQueue;

public class Consumer extends Thread {

	private MyQueue mq;

	public Consumer(MyQueue mq) {
		this.mq = mq;
	}

	@Override
	public void run() {

		// Assume there is 1000 lines of codes here
		
		while (!MyQueue.stop) {
			synchronized (mq) {
				removeItem();
			}
		}

		// Assume there is 10000 lines of codes here
	}

	private void removeItem() {
		System.out.println("Consumer aquired the lock.Going to remove the number from a queue now.");

		if (mq.getQueue().size() == 0) {
			System.out.println(
					"Queue is empty. So Comsumer is waiting for any number added in a queue. Going to release the lock now.");
			try {
				mq.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Current Items in a Queue: " + mq.getQueue());
			System.out.println("Going to remove item in a queue: " + mq.getQueue().peek());
			mq.remove();
			System.out.println("Queue after removing the item: " + mq.getQueue());
		}
	}
}
