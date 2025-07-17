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
				.perform(get("/getCustomerRewardPointDetails/1001").contentType(MediaType.APPLICATION_JSON))
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
				.perform(get("/getCustomerRewardPointDetails/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError()).andReturn();

		int statusCode = result.getResponse().getStatus();
		
		assertEquals(500, statusCode);

	}

	@Test
	public void getCustomerRewardPoint_invalidCustomerId() throws JsonProcessingException, Exception {

		MvcResult result = mockMvc.perform(
				get("/getCustomerRewardPointDetails/101").contentType(MediaType.APPLICATION_JSON).content("1002"))
				.andExpect(status().is5xxServerError()).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("CustomerId Not Found"));
	}

}