package com.example.esp33_frontEndApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Esp33FrontEndAppApplicationTests {

	AlertConsumer consumer = new AlertConsumer();

	@Test
	void contextLoads() {
	}

	@Test
	void verify_Topic() {
		assert(consumer.getTopic().equals("ESP33_Alerts"));
	}

}
