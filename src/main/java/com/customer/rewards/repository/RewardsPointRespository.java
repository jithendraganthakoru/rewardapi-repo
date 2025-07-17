package com.customer.rewards.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.customer.rewards.model.Transaction;

@Repository
public class RewardsPointRespository {

	private final Map<String, List<Transaction>> data = new HashMap<>();

	/**
	 *   maintains the custom dataset for multiple customers 
	 */
	public RewardsPointRespository() {

		data.put("1001", List.of(new Transaction("1001", 120, LocalDate.of(2025, 5, 10)),
				new Transaction("1001", 110, LocalDate.of(2025, 6, 10))));

		data.put("1002", List.of(new Transaction("1002", 150, LocalDate.of(2025, 5, 10)),
				new Transaction("1002", 100, LocalDate.of(2025, 6, 15))));

		data.put("1003", List.of(new Transaction("1003", 180, LocalDate.of(2025, 5, 10))));
		
		data.put("1004", List.of(new Transaction("1004", 160, LocalDate.of(2025, 7, 10)),
				new Transaction("1004", 170, LocalDate.of(2025, 6, 15))));
		
		data.put("1005", List.of(new Transaction("1005", 180, LocalDate.of(2025, 5, 05)),
				new Transaction("1005", 150, LocalDate.of(2025, 6, 10)),
				new Transaction("1005", 158, LocalDate.of(2025, 7, 15))));
	}

	/**
	 * @param customerId
	 * @return based on customerId it will returns the customer transaction data
	 */
	public List<Transaction> findByCustomerId(String customerId) {
		return data.getOrDefault(customerId, List.of());
	}

}