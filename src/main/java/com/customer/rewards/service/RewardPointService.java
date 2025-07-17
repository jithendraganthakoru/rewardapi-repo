package com.customer.rewards.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.customer.rewards.model.RewardResponse;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.RewardsPointRespository;

/**
 * 
 * CustomerTransRewardPointService class is responsible for maintaining the
 * business logic
 * 
 */
@Service
public class RewardPointService {

	Logger logger = LoggerFactory.getLogger(RewardPointService.class);

	public final RewardsPointRespository rewardsPointRespository;

	public RewardPointService(RewardsPointRespository rewardsPointRespository) {

		this.rewardsPointRespository = rewardsPointRespository;

	}

	/**
	 * @param customerId
	 * @return Below method is responsible to calculate the reward points monthly
	 *         and total reward points for the specific customer
	 *     
	 * @throws Exception
	 */
	public List<RewardResponse> calculateRewards(String customerId) throws Exception {

		List<Transaction> tranactions = rewardsPointRespository.findByCustomerId(customerId);

		if (tranactions.size() == 0) {

			throw new RuntimeException("CustomerId Not Found");
		}

		List<RewardResponse> rewardList = new ArrayList<RewardResponse>();

		Map<String, Integer> monthlyPoints = new HashMap<>();

		int totalPoints = 0;

		for (Transaction txn : tranactions) {
			int points = calculatePoints(txn.getAmount());
			String month = txn.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			monthlyPoints.put(month, points);
			totalPoints += points;
		}

		RewardResponse response = new RewardResponse();
		response.setCustomerId(customerId);
		response.setMonthlyPoints(monthlyPoints);
		response.setTotalPoints(totalPoints);
		rewardList.add(response);

		return rewardList;
	}

	/**
	 * @param amount
	 * 
	 *     Calculates reward points based on the product purchase amount.
	 * 
	 *     For every dollar spent over $100: the customer earns 2 points
	 *     per dollar.
	 * 
	 *     For every dollar spent between $50 and $100: the customer earns
	 *     1 point per dollar.
	 * 
	 *     No points are awarded for the first $50.
	 * 
	 */
	public int calculatePoints(Integer amount) throws IllegalArgumentException {

		int points = 0;

		if (amount == null || amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero or not null");
		}

		if (amount > 100) {
			points += (int) ((amount - 100) * 2);
			points += 50;
		} else if (amount > 50) {
			points += (int) (amount - 50);
		}

		return points;
	}
}