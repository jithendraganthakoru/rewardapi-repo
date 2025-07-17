package com.customer.rewards.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

	private String customerId;
	
    private Map<String, Integer> monthlyPoints;
	
    private int totalPoints;
}