package com.example.esp33_frontEndApp;

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

@SpringBootTest
@AutoConfigureMockMvc
class Esp33FrontEndAppApplicationTests {

	@Autowired
	AlertConsumer consumer = new AlertConsumer();

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertNotNull(consumer);
	}

	@Test
	void verify_TopicA() {
		assert(consumer.getTopicA().equals("ESP33_Alerts"));
	}

	@Test
	void verify_TopicR() {
		assert(consumer.getTopicR().equals("ESP33_SensorData"));
	}

	@Test
    public void requestRealtime() throws Exception {
        System.out.println("Request realtime");
        this.mockMvc.perform(get("/realtime")).andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void requestReset() throws Exception {
        System.out.println("Request reset");
        this.mockMvc.perform(get("/reset")).andDo(print()).andExpect(status().isOk());
    }

	@Test
    public void testAlert_messages() throws Exception {
		assert(consumer.getAlert_messages() != null);
	}

}
