package com.customer.rewards.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.customer.rewards.model.RewardResponse;
import com.customer.rewards.service.RewardPointService;

/**
 * 
 * RewardPointController class acts as Rest controller and accepts the http request from the client
 * 
 */
@RestController
public class RewardPointController {
	
	private final RewardPointService customerTransRewardPointService;
	
	Logger logger = LoggerFactory.getLogger(RewardPointController.class);
	
	/**
	 * @param customerTransRewardPointService  
	 * 
	 * spring container will create CustomerTransRewardPointService object by applying constructor injection
	 */
	
	public RewardPointController(RewardPointService customerTransRewardPointService) {
		
		this.customerTransRewardPointService = customerTransRewardPointService;
	}
	
	/**
	 * @param customerId
	 * @return response contains customerId, monthly earned reward points and total reward points
	 * @throws Exception
	 */
	@GetMapping("/getCustomerRewardPointDetail/{customerId}")
	public List<RewardResponse> getCustomerRewardPointDetail(@PathVariable String customerId) throws Exception {
		
		logger.info("customerId:::::"+customerId);
		
		return customerTransRewardPointService.calculateRewards(customerId);
	}
	
	
	@GetMapping("/getAllCustomerRewardPointDetails")
	public List<RewardResponse>  getAllCustomerRewardPointDetails(){
		
		return customerTransRewardPointService.calculateAllCustomerReward();
	}

}