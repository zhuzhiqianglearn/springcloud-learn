package com.cloudlearn.turbin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TurbinApplicationTests {

	@Test
	public void contextLoads() {
		String a="张三";
		System.out.println(a.substring(2));
	}

	public static void main(String[] args) {
		String a="张三";
		System.out.println(a.substring(2));;
	}
}
