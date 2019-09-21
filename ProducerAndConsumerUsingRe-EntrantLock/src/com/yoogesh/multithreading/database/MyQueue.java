package com.yoogesh.multithreading.database;

import java.util.PriorityQueue;

public class MyQueue {
	
	PriorityQueue<Integer> queue = new PriorityQueue<>(100);
	public static boolean stop = false;
	public static boolean ProducerFinishedTask = false;
	
	public void add(int input) {
		queue.offer(input);
	}
	
	public int remove() {
		int removedItem = queue.remove();
		if(ProducerFinishedTask && queue.size() == 0) {
			stop = true;
		}
		return removedItem;
	}
	
	public PriorityQueue<Integer> getQueue() {
		return queue;
	}
}
