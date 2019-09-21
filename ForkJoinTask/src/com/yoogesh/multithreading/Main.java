package com.yoogesh.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		
		List<Product> products = generateProducts(100);
		
		ForkJoinTask task = new Task(products, 0, products.size(), 0.20);
		
		ForkJoinPool pool  = new ForkJoinPool();
		pool.execute(task);
		
		//Write information about the pool
		do {
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
			System.out.printf("Main: Steal count: %d\n", pool.getStealCount());
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		while(!task.isDone());
		
		pool.isShutdown();
		
		//check if the task completed normally
		if(task.isCompletedNormally()) {
			System.out.printf("Main: The process has completed normally and price of all is set to 12.\n");
		}
		
		System.out.printf("Main: End of the program.\n");
	}

	
	
	private static List<Product> generateProducts(int size) 
	{
		List<Product> products = new ArrayList<>();
		for (int i=0; i <size; i++) {
			Product product = new Product();
			product.setName("Product " + i);
			product.setPrice(10);
			products.add(product);
		}
		return products;
	}
}

class Product {
	
	private String name;
	private double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}

