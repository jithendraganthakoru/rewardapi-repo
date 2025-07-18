package com.customer.rewards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.customer.rewards.model.RewardResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * This Class Responsible to perform integration test to check entire flow of an
 * application
 * 
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RewardPointControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void getCustomerRewardPointDetailTest() throws Exception {

		MvcResult result = mockMvc
				.perform(get("/getCustomerRewardPointDetail/1001").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();

		List<RewardResponse> transList = mapper.readValue(response, new TypeReference<>() {
		});

		RewardResponse rewardResponse = transList.get(0);

		assertEquals("1001", rewardResponse.getCustomerId());

		assertEquals(160, rewardResponse.getTotalPoints());

		Map<String, Integer> map = rewardResponse.getMonthlyPoints();

		assertTrue(map.containsKey("June"));

		assertTrue(map.containsKey("May"));

		assertEquals(90, map.get("May"));

		assertEquals(70, map.get("June"));
	}

	@Test
	public void rewardPointDetailTest_customerIdAbsent() throws Exception {

		MvcResult result = mockMvc
				.perform(get("/getCustomerRewardPointDetail/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError()).andReturn();

		int statusCode = result.getResponse().getStatus();

		assertEquals(500, statusCode);

	}

	@Test
	public void getCustomerRewardPoint_invalidCustomerId() throws JsonProcessingException, Exception {

		MvcResult result = mockMvc
				.perform(get("/getCustomerRewardPointDetail/101").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andReturn();

		assertEquals("CustomerId 101 Not Found", result.getResponse().getContentAsString());
	}

	@Test
	public void getCustomerRewardPoins() throws JsonProcessingException, Exception {

		MvcResult result = mockMvc
				.perform(get("/getAllCustomerRewardPointDetails").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();

		List<RewardResponse> list = mapper.readValue(response, new TypeReference<List<RewardResponse>>() {
			
		});
		
		assertEquals(5,list.size());
	}
}