package com.FireFighter_Support.restservice;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
/*
@SpringBootTest
@AutoConfigureMockMvc
class RestServiceApplicationTests {

	@Autowired
	KafkaConsumer consumer = new KafkaConsumer();

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertNotNull(consumer);
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

	@Test
    public void requestIndex() throws Exception {
        System.out.println("Request index");
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

}
*/