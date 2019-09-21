package com.yoogesh.multithreading;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	private List<Product> products;
	private int first;
	private int last;
	private double increment;
	
	public Task(List<Product> products, int first, int last, double increment) {
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}


	
	/**
	 * This method will update the price of the products
	 * If the numbers are less than 10, it manually changes the prices
	 * If the numbers are greater than 10 then it will divide total numbers in a half half and uses two task to process those 
	 * hence using the parallelism
	 */

	@Override
	protected void compute() {
      if(last-first < 10) {
    	  updatePrices();
      }else {
    	  int middle = (last + first)/2;
    	  System.out.printf("Task: Pending tasks: %s\n", getQueuedTaskCount());
    	  Task t1= new Task(products, first, middle+1, increment);
    	  Task t2= new Task(products, middle+1, last, increment);
    	  invokeAll(t1, t2);
      }
	}



	private void updatePrices() {
		for( int i= first; i<last; i++) {
			Product product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}

}
