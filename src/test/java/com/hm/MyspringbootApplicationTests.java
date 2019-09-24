package com.hm;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hm.config.InitService;
import com.hm.mapper.UserInfoMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyspringbootApplicationTests {
	@Autowired
	private UserInfoMapper userInfoMapper;

	private static final Integer NUM = 10000;
	private CountDownLatch count = new CountDownLatch(1);
	@Autowired
	private InitService initService;

//	@Test
//	public void contextLoads() {
//
//		for (int i = 0; i < NUM; i++) {
//			new Thread(new AddUserinfo()).start();
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("start...................");
//		// count.countDown();
//
//	}
//
//	class AddUserinfo implements Runnable {
//
//		@Override
//		public void run() {
//			UserInfo info = new UserInfo();
//			info.setName("mark");
//			System.out.println("user create ......");
//			// count.await();
//			System.out.println("user go ......");
//			userInfoMapper.save(info);
//
//		}
//
//	}
//
//	@Test
//	public void getOne() {
//		UserInfo selectOne = userInfoMapper.selectOne(1);
//		System.out.println(selectOne);
//	}

	@Test
	public void product() throws InterruptedException {
		initService.generateMultiThread();
		Thread.sleep(1000000);
	}

}
