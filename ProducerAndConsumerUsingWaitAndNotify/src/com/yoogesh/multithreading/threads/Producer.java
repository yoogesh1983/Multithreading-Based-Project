package com.yoogesh.multithreading.threads;

import com.yoogesh.multithreading.database.MyQueue;

public class Producer extends Thread {
	
	private static final int MAX = 100;
	private MyQueue mq;
	
	public Producer(MyQueue mq) {
		this.mq = mq;
	}

	@Override
	public void run() {
		
		//Assume there is 1000 lines of codes here
		
		for(int i = 0; i < MAX; i++) {
			
			synchronized (mq) {
				addItem(i);
			}
		}
		
		//Assume there is 10000 lines of codes here
	}

	private void addItem(int i) {
		System.out.println("Producer aquired the lock.Going to add number in a queue now.");
		int radomNumber = (int) (Math.random()*100);
		mq.add(radomNumber);
		mq.notify();
		System.out.println(radomNumber + " is added to the queue. Now going to release the lock.");
		if(i == MAX - 1) {
			System.out.println("By the way, No more queue will be added. Final items in a queue with size "+  mq.getQueue().size() + " is : " + mq.getQueue());
			MyQueue.ProducerFinishedTask = true;
		}
	}

}
