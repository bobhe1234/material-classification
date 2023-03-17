package com.jswy.infrastructure.queue.disruptor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator
 */
public class CustThreadFactory implements ThreadFactory {

	private AtomicInteger atomicInteger = new AtomicInteger();

	private boolean isDaemon;

	public CustThreadFactory(boolean isDaemon) {
		this.isDaemon = isDaemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		atomicInteger.incrementAndGet();
		Thread thread = new CustWorkThread(atomicInteger, r);
		thread.setDaemon(isDaemon);
		return thread;
	}
}
