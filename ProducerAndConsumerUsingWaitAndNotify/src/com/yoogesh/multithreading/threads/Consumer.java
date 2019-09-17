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
