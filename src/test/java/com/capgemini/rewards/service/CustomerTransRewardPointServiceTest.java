package com.capgemini.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.rewards.model.RewardResponse;
import com.capgemini.rewards.model.Transaction;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerTransRewardPointServiceTest {

	
	CustomerTransRewardPointService customerTransRewardPointService = new CustomerTransRewardPointService();

	@Test
	public void calculateRewardsTest() {

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
	public void testCalculateRewards_EmptyList() {
		List<RewardResponse> responses = customerTransRewardPointService.calculateRewards(List.of());
		assertTrue(responses.isEmpty());
	}

	@Test
	public void testCalculatePoints_LessThan50() {
		int points = customerTransRewardPointService.calculatePoints(40);
		assertEquals(0, points);
	}

	@Test
	void testCalculatePoints_Between50And100() {
		int points = customerTransRewardPointService.calculatePoints(75);
		assertEquals(25, points);
	}

	@Test
	void testCalculatePoints_Above100() {
		int points = customerTransRewardPointService.calculatePoints(120);
		assertEquals(90, points);
	}

}