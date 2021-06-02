package com.FireFighter_Support.restservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestServiceApplicationTests {

	KafkaConsumer consumer = new KafkaConsumer();

	@Test
	void contextLoads() {
	}

	@Test
	void verify_get_messages() {
		assert(consumer.get_messages() != null);
	}

	@Test
	void verify_Topic() {
		assert(consumer.getTopic().equals("ESP33_SensorData"));
	}

	@Test
	void verify_database_name() {
		assert(consumer.getDatabaseName().equals("esp33_firefighters"));
	}

}
