package com.customer.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.customer.rewards.model.RewardResponse;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.RewardsPointRespository;

/**
 * This class responsible to perform junit testing with mockito for component based testing
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RewardPointServiceTest {

	@Spy
	RewardsPointRespository repositroy;

	@InjectMocks
	private RewardPointService customerTransRewardPointService;

	@Test
	void testCalculateRewards_Success() throws Exception {

		String customerId = "1001";
		Transaction txn = new Transaction(customerId, 120, LocalDate.of(2025, 6, 6));

		when(repositroy.findByCustomerId(customerId)).thenReturn(List.of(txn));

		List<RewardResponse> rewards = customerTransRewardPointService.calculateRewards("1001");

		assertNotNull(rewards);
		assertEquals(1, rewards.size());

		RewardResponse response = rewards.get(0);
		assertEquals(customerId, response.getCustomerId());
		assertEquals(90, response.getTotalPoints());
		assertTrue(response.getMonthlyPoints().containsKey("June"));
		assertEquals(90, response.getMonthlyPoints().get("June"));
	}

	@Test
	void testCalculateRewards_MultipleTxnWithSameCustomer() throws Exception {

		String customerId = "1001";
		List<Transaction> txn = List.of(new Transaction(customerId, 120, LocalDate.of(2025, 6, 6)),
				new Transaction(customerId, 110, LocalDate.of(2025, 7, 6)));

		when(repositroy.findByCustomerId(customerId)).thenReturn(txn);

		List<RewardResponse> rewards = customerTransRewardPointService.calculateRewards(customerId);

		assertNotNull(rewards);
		RewardResponse response = rewards.get(0);
		assertEquals(customerId, response.getCustomerId());
		assertEquals(160, response.getTotalPoints());
		assertTrue(response.getMonthlyPoints().containsKey("July"));
		assertEquals(70, response.getMonthlyPoints().get("July"));
	}

	@Test
	void testCalculateRewards_MultipleTxnWithInvalidAmount() throws Exception {

		String customerId = "1001";
		List<Transaction> txn = List.of(new Transaction(customerId, -120, LocalDate.of(2025, 6, 6)),
				new Transaction(customerId, -110, LocalDate.of(2025, 7, 6)));

		when(repositroy.findByCustomerId(customerId)).thenReturn(txn);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			customerTransRewardPointService.calculateRewards(customerId);
		});

		assertEquals("Amount must be greater than zero or not null", exception.getMessage());

	}

	@Test
	public void testCalculateRewardPoints_EmptyList() throws Exception {

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			customerTransRewardPointService.calculateRewards("1008");
		});
		assertEquals("CustomerId Not Found", exception.getMessage());

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