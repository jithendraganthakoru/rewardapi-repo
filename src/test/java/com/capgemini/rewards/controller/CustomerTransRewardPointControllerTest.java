package com.capgemini.rewards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.capgemini.rewards.model.RewardResponse;
import com.capgemini.rewards.model.Transaction;
import com.capgemini.rewards.service.CustomerTransRewardPointService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerTransRewardPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerTransRewardPointService customerTransRewardPointService;

    @Test
    public void getCustomerRewardPointDetailTest() throws Exception {
        List<Transaction> list = List.of(
            new Transaction("1001", 120, LocalDate.of(2025, 6, 6)),
            new Transaction("1002", 100, LocalDate.of(2025, 6, 7)),
            new Transaction("1003", 110, LocalDate.of(2025, 7, 6))
        );
        
        MvcResult result = mockMvc.perform(post("/getCustomerRewardPointDetails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(list)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        List<RewardResponse> transList = mapper.readValue(response, new TypeReference<>() {});

    }
}

