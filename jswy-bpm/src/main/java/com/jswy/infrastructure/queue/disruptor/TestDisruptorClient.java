package com.jswy.infrastructure.queue.disruptor;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jswy.infrastructure.util.RestfulHttpClient;

public class TestDisruptorClient {
	private static Logger logger = LoggerFactory.getLogger(TestDisruptorClient.class);
	private static String url = "http://localhost:9099/queue/data";

	public static void main(String[] args) throws IOException {
//		batchQueueAsync();
		long startTime1 = System.currentTimeMillis();
		testThreadPool(null);
		long startTime3 = System.currentTimeMillis();
		logger.info("命令窗口:结束 - 完成 当前时间[{}] 耗时[{}]", new Date(), startTime3 - startTime1);
	}

	/**
	 * 模拟批量:异步队列请求
	 * 
	 * @throws IOException
	 */
	private static void batchQueueAsync() throws IOException {
		long startTime1 = System.currentTimeMillis();
		logger.info("命令窗口:开始 - 接受消息!");
		RestfulHttpClient.HttpResponse response;
		for (int i = 0; i < 2; i++) {
			response = RestfulHttpClient.request("POST", url, "Thankpad P520 " + i);
			// logger.info("命令窗口:进行中 - 状态码:" + response.getCode()); // 响应状态码
			// logger.info("命令窗口:进行中 - 服务URL:" + response.getRequestUrl());// 最终发起请求的地址
			if (response.getCode() == 200) {// 请求成功
				// logger.info("命令窗口:进行中 - 响应内容:" + response.getContent()); // 响应内容
			}
		}
		long startTime3 = System.currentTimeMillis();
		logger.info("命令窗口:结束 - 完成 当前时间[{}] 耗时[{}]", new Date(), startTime3 - startTime1);
	}

	/**
	 * 启动多线程:测试队列生产/消费(Thankpad Intel(R) Xeon(R) W-10855M CPU @ 2.80GHz  59997 millis)
	 * 
	 * @param args
	 */
	public static void testThreadPool(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool(new CustThreadFactory(false));
		for (int i = 0; i < 1; i++) {
			executorService.execute(new TestRunnable("Thankpad P520 " + String.valueOf(i)));
		}
		executorService.shutdown();
	}

	static class TestRunnable implements Runnable {
		private String msg;

		public TestRunnable(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			try {
				RestfulHttpClient.HttpResponse response = RestfulHttpClient.request("POST", url, msg);
//				System.out.println("msg:" + msg + ",Code:" + response.getCode() + ",URL: " + response.getRequestUrl());
				// logger.info("命令窗口:进行中 - 状态码:" + response.getCode()); // 响应状态码
				// logger.info("命令窗口:进行中 - 服务URL:" + response.getRequestUrl());// 最终发起请求的地址
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
