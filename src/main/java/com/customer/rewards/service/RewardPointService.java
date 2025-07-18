package com.customer.rewards.service;

import java.util.List;

import com.customer.rewards.model.RewardResponse;

public interface RewardPointService {
	
	List<RewardResponse> calculateRewards(String customerId);
	
	List<RewardResponse> calculateAllCustomerReward();	

}
