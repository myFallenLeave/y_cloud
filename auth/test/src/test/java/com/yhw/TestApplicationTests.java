package com.yhw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestApplicationTests {

	@Test
	public void contextLoads() {
//		ArrayBlockingQueue
		/*CountDownLatch countDownLatch = new CountDownLatch(5);
		countDownLatch.await();*/
	}

	public static void main(String[] args){
		String groupId = null;
		Optional<String> optional = Optional.ofNullable(groupId);
//		Optional<String> optional = Optional.of(groupId);
		System.out.println(optional.isPresent());
		System.out.println(optional.orElse("哈哈"));

//		AtomicInteger
	}

}
