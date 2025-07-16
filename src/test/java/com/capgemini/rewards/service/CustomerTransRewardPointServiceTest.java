package com.capgemini.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.rewards.model.RewardResponse;
import com.capgemini.rewards.model.Transaction;

@SpringBootTest
public class CustomerTransRewardPointServiceTest {

	@Autowired
	private CustomerTransRewardPointService customerTransRewardPointService;
	
	@Test
	public void calculateRewardsTest() throws Exception {

		List<Transaction> list = new ArrayList<Transaction>();
		list.add(new Transaction("1001", 120, LocalDate.of(2025, 06, 06)));
		list.add(new Transaction("1002", 100, LocalDate.of(2025, 06, 07)));
		list.add(new Transaction("1003", 110, LocalDate.of(2025, 07, 06)));

		List<RewardResponse> responseList = customerTransRewardPointService.calculateRewards(list);

		System.out.println("responseList::::"+responseList);
		RewardResponse response = responseList.get(0);

		assertEquals("1001", response.getCustomerId());
		assertEquals(90, response.getMonthlyPoints().entrySet().iterator().next().getValue());
	}

	@Test
	public void testCalculateRewardPoints_EmptyList() throws Exception {

	    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
	        customerTransRewardPointService.calculateRewards(List.of());
	    });
	    assertEquals("Customer Transactions should not empty or null", exception.getMessage());
		
	}

	@Test
	public void testCalculateRewardPoints_LessThan50() {
		int points = customerTransRewardPointService.calculatePoints(40);
		assertEquals(0, points);
	}

	@Test
	void testCalculateRewardPoints_Between50And100() {
		int points = customerTransRewardPointService.calculatePoints(75);
		assertEquals(25, points);
	}

	@Test
	void testCalculateRewardPoints_Above100() {
		int points = customerTransRewardPointService.calculatePoints(120);
		assertEquals(90, points);
	}

}