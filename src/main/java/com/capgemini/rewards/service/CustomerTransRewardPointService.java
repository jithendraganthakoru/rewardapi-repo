package com.capgemini.rewards.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capgemini.rewards.model.RewardResponse;
import com.capgemini.rewards.model.Transaction;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * CustomerTransRewardPointService class is responsible for maintaining the business logic 
 * 
 */
@Service
@Slf4j
public class CustomerTransRewardPointService {
	
	Logger logger = LoggerFactory.getLogger(CustomerTransRewardPointService.class);
	
	/**
	 * @param tranactions
	 * @return  below method is responsible for accepting list of customer transactions & extra the amount 
	 * 
	 * To calculate the reward points monthly and total reward points of last threee months & forms a response, sent back to 
	 * 
	 * controller
	 * 
	 */
	public List<RewardResponse> calculateRewards(List<Transaction> tranactions) throws Exception{
		
	    List<RewardResponse> rewardList = new ArrayList<>();

	    Map<String, List<Transaction>> customerTxnMap = tranactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId, LinkedHashMap::new, Collectors.toList()));
	    
	    
	    logger.info("groupBy trasnactions based on customerId:::::"+customerTxnMap);

	    for (Map.Entry<String, List<Transaction>> entry : customerTxnMap.entrySet()) {
	        
	    	String customerId = entry.getKey();
	        
	    	List<Transaction> eachCustomerTxns = entry.getValue();

	        Map<String, Integer> monthlyPoints = new HashMap<>();
	        
	        int totalPoints = 0;

	        for (Transaction txn : eachCustomerTxns) {
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
	    }

	    return rewardList;
	}

	 /**
	 * @param amount
	 * 
	 * Calculates reward points based on the product purchase amount.
	 * 
	 * For every dollar spent over $100: the customer earns 2 points per dollar.
	 * 
	 * For every dollar spent between $50 and $100: the customer earns 1 point per dollar.
	 * 
	 * No points are awarded for the first $50.
	 * 
	 */
	public int calculatePoints(double amount) throws IllegalArgumentException {
		 
	        int points = 0;
	        
	        if(amount==0.0) {
	        	
	        	throw new IllegalArgumentException("Amount cannot be zero");
	        }
	        
	        if (amount > 100) {
	            points += (int)((amount - 100) * 2);
	            points += 50;
	        } else if (amount > 50) {
	            points += (int)(amount - 50);
	        }

	        return points;
	    }   

}