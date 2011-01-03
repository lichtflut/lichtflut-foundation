/*
 * Copyright (C) 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.infra.threading;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.logging.Log;

/**
 * Asynchronous executor of orders.
 * 
 * Created: 28.01.2009
 *
 * @author Oliver Tigges
 */
public class OrderExecutor implements Runnable {
	
	private final Queue<Order> orders = new ArrayBlockingQueue<Order>(20, false);
	private boolean running;
	
	//-----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public OrderExecutor() {}
	
	//-----------------------------------------------------
	
	/**
	 * add an order that will be executed in another Thread.
	 * @param order The order to be executed later.
	 */
	public void addOrder(final Order order){
		orders.add(order);
	}
	
	// -----------------------------------------------------
	
	/**
	 * Starts the executor.
	 * May only be called once.
	 */
	public void startExecutor(){
		if (running){
			throw new IllegalStateException(this.getClass().getName() + " already running!");
		}
		new Thread(this).start();
	}
	
	/**
	 * implementation of {@link Runnable}.
	 */
	public void run(){
		while (true){
			Order exec = orders.poll();
			if (exec != null){
				Log.debug(this, "found order in Queue: " + exec);
				execInNewThread(exec);
				Thread.yield();
			} else {
				Infra.wait(100);
			}
		}
	}
	
	//-----------------------------------------------------
	
	/**
	 * TODO: ThreadPooling: WorkderThreads
	 */
	private void execInNewThread(final Order order){
		Thread thread = new Thread(new Runnable(){
			public void run() {
				Log.debug(OrderExecutor.class, "executing " + order);
				order.execute();
			}
		});
		thread.start();
	}
	
}
