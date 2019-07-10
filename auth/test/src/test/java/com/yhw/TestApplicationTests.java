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
		/*AtomicInteger atomicInteger = new AtomicInteger();
		System.out.println(atomicInteger.get());
		System.out.println(0x61c88647);
		atomicInteger.getAndAdd(0x61c88647);
		System.out.println(atomicInteger.get());
		atomicInteger.getAndAdd(0x61c88647);
		System.out.println(atomicInteger.get());*/
		ThreadLocal<String> test = new ThreadLocal<>();
		test.set("aaa");
		test.set("bbb");

		System.out.println(test.get());
		test.remove();
		System.out.println(test.get());
		test.remove();
		System.out.println(test.get());
	}

}
