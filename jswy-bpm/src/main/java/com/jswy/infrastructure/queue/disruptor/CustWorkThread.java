package com.jswy.infrastructure.queue.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator
 */
public class CustWorkThread extends Thread {
	private AtomicInteger atomicInteger;

	public CustWorkThread(AtomicInteger atomicInteger, Runnable runnable) {
		super(runnable);
		this.atomicInteger = atomicInteger;
	}

	@Override
	public void run() {
		System.out.println(this.getName() + " test" + atomicInteger.getAndDecrement());
		super.run();
	}
}
