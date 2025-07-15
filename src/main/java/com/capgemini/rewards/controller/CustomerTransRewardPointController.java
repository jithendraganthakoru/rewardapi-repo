package com.capgemini.rewards.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.rewards.model.RewardResponse;
import com.capgemini.rewards.model.Transaction;
import com.capgemini.rewards.service.CustomerTransRewardPointService;

/**
 * 
 * CustomerTransRewardPointController class acts as Restcontroller and accepts the http request from the client
 * 
 */
@RestController
public class CustomerTransRewardPointController {

	
	private final CustomerTransRewardPointService customerTransRewardPointService;
	
	Logger logger = LoggerFactory.getLogger(CustomerTransRewardPointController.class);
	
	/**
	 * @param customerTransRewardPointService  
	 * 
	 * spring container will create CustomerTransRewardPointService object by applying constructor injection
	 */
	public CustomerTransRewardPointController(CustomerTransRewardPointService customerTransRewardPointService) {

		this.customerTransRewardPointService = customerTransRewardPointService;
	}
	
	/**
	 * @param tranactions
	 * @return response contains list of customer data and it contains customerId and monthly earned reward points and total reward points
	 * @throws Exception 
	 */
	@PostMapping("/getCustomerRewardPointDetails")
	public List<RewardResponse> getCustomerRewardPointDetail(@RequestBody List<Transaction> tranactions) throws Exception {
		
		logger.info("list of customer transactions:::::"+tranactions);

		return customerTransRewardPointService.calculateRewards(tranactions);
	}

}