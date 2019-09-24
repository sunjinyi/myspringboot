package com.hm.config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService {
	private static final int ThreadNum = 10000;
	private static AtomicInteger mobile = new AtomicInteger(1);

	@Autowired
	private CommonMqService mqService;

	public void generateMultiThread() {
		System.out.println("开始初始化线程数----->");
		try {
			CountDownLatch latch = new CountDownLatch(1);
			for (int i = 0; i < ThreadNum; i++) {
				new Thread(new UserThread(latch)).start();
			}
			latch.countDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private class UserThread implements Runnable {

		public UserThread(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		private CountDownLatch latch;

		@Override
		public void run() {
			try {
				latch.await();
				int mobiles = mobile.getAndIncrement();
				mqService.sendMessage(String.valueOf(mobiles));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
