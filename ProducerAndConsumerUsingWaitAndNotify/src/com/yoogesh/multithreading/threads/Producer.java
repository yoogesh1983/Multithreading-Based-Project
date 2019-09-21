package com.yoogesh.multithreading.threads;

import java.util.concurrent.CountDownLatch;

import com.yoogesh.multithreading.database.MyQueue;

public class Producer extends Thread {
	
	private static final int MAX = 50;
	private MyQueue mq;
	private CountDownLatch latch;
	
	public Producer(MyQueue mq, CountDownLatch latch) {
		this.mq = mq;
		this.latch = latch;
	}

	@Override
	public void run() {
		
		//Assume there is 1000 lines of codes here
		
		for(int i = 0; i < MAX; i++) {
			
			synchronized (mq) {
				System.out.println("Producer aquired the lock.Going to add number in a queue now.");
				int radomNumber = (int) (Math.random()*100);
				mq.add(radomNumber);
				System.out.println(radomNumber + " is added to the queue. Now releasing the lock and going in a waiting state to give a chance to consumer.");
				if(i == MAX - 1) {
					System.out.println("By the way, No more queue will be added. Final items in a queue with size "+  mq.getQueue().size() + " is : " + mq.getQueue());
					MyQueue.ProducerFinishedTask = true;
				}
				mq.notify();
			}
		}
		latch.countDown();
		
		//Assume there is 10000 lines of codes here
	}

}
