package com.capgemini.rewards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.capgemini.rewards.model.RewardResponse;
import com.capgemini.rewards.model.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerTransRewardPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    
   static List<Transaction> customerListWithSingleTnx = null;
    
   static List<Transaction> customerListWithMultipleTnx = null;
    
    
    @BeforeAll
    public static void init() {
    	
    	//each customer having single transaction
    	customerListWithSingleTnx = List.of(
                new Transaction("1001", 120, LocalDate.of(2025, 6, 6)),
                new Transaction("1002", 100, LocalDate.of(2025, 6, 7)),
                new Transaction("1003", 110, LocalDate.of(2025, 7, 6))
            );
    	
    	//each customer having multiple transactions
    	customerListWithMultipleTnx = List.of(
    		      new Transaction("1001", 120, LocalDate.of(2025, 6, 6)),
                  new Transaction("1001", 100, LocalDate.of(2025, 7, 7)),
                  new Transaction("1001", 110, LocalDate.of(2025, 5, 10)),    //90+50+20+50
                  new Transaction("1002", 120, LocalDate.of(2025, 5, 9)),
                  new Transaction("1002", 100, LocalDate.of(2025, 6, 10)),    //90+50
                  new Transaction("1003", 160, LocalDate.of(2025, 7, 6)),     //120+50+100+50
                  new Transaction("1003", 150, LocalDate.of(2025, 6, 15))
    			);
    }
    

    @Test
    public void getCustomerRewardPointDetailTest() throws Exception {
        
        MvcResult result = mockMvc.perform(post("/getCustomerRewardPointDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerListWithSingleTnx)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        
        List<RewardResponse> transList = mapper.readValue(response, new TypeReference<>() {});
        
        RewardResponse  rewardResponse = transList.get(0);
        
        assertEquals("1001", rewardResponse.getCustomerId());
        
        assertEquals(90,rewardResponse.getMonthlyPoints().entrySet().iterator().next().getValue());
        
        assertEquals("June",rewardResponse.getMonthlyPoints().entrySet().iterator().next().getKey());
        
        assertEquals(90, rewardResponse.getTotalPoints());
        
        rewardResponse = transList.get(1);
        
        assertEquals("1002", rewardResponse.getCustomerId());
        
        assertEquals(50,rewardResponse.getMonthlyPoints().entrySet().iterator().next().getValue());
        
        assertEquals("June",rewardResponse.getMonthlyPoints().entrySet().iterator().next().getKey());
        
    }
    
    @Test
    public void getCustomerRewardPointDetail() throws Exception {
        
        MvcResult result = mockMvc.perform(post("/getCustomerRewardPointDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerListWithMultipleTnx)))
                .andExpect(status().isOk())
                .andReturn();
        
        RewardResponse  rewardResponse = null;
        
        String response = result.getResponse().getContentAsString();
        
        List<RewardResponse> transList = mapper.readValue(response, new TypeReference<>() {});
        
        rewardResponse = transList.get(0);
        
        assertEquals("1001", rewardResponse.getCustomerId());
                        
        assertEquals(210, rewardResponse.getTotalPoints());
        
        assertTrue(rewardResponse.getMonthlyPoints().containsKey("June"));

        assertTrue(rewardResponse.getMonthlyPoints().containsKey("July"));

        assertTrue(rewardResponse.getMonthlyPoints().containsKey("May"));

        rewardResponse = transList.get(1);
        
        assertEquals("1002", rewardResponse.getCustomerId());
        
        assertEquals(140, rewardResponse.getTotalPoints());
                
        rewardResponse = transList.get(2);
        
        assertEquals("1003", rewardResponse.getCustomerId());
        
        assertEquals(320, rewardResponse.getTotalPoints());
        
    }
    
    @Test
    public void emptyCustomerTnxList() throws JsonProcessingException, Exception {
    	
    	List<Transaction> list = List.of();
    	
    	 MvcResult result = mockMvc.perform(post("/getCustomerRewardPointDetails")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(mapper.writeValueAsString(list)))
                 .andExpect(status().isOk())
                 .andReturn();
    	
    	List<RewardResponse> response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
    	 
    	assertTrue(response.isEmpty()); 	 
    }
    
    
    @Test
    public void invalidInputAssertException() throws Exception {
    	
    	
    	String s = "[\r\n"
    			+ " {\r\n"
    			+ "   \"customerId\":\"1004\",\r\n"
    			+ "   \"amount\": 135,\r\n"
    			+ "   \"date\" : \"06-07-2025\"\r\n"
    			+ " },\r\n"
    			+ "\r\n"
    			+ "]";      // contains extra delimeter ','
  
     MvcResult result = mockMvc.perform(post("/getCustomerRewardPointDetails")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(s)).andReturn();
              
      assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException);

    }
    
    
    
    
}