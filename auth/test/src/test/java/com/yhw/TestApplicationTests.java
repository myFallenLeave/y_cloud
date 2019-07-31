package com.yhw;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 158;
		Integer f = 158;
		Long g = 3L;
		System.out.println(c == d);
		System.out.println(e == f);//-128-127
		System.out.println(c == (a+b));
		System.out.println(c.equals(a+b));
		System.out.println(g == (a+b));
		System.out.println(g.equals(a+b));

		Map<String,String> abc = new HashMap<>();
//		CopyOnWriteArrayList
	}

}
