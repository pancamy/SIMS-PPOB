package com.sims.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sims.ppob.model.ServiceSaveRequest;
import com.sims.ppob.model.TransactionPaymentRequest;
import com.sims.ppob.model.UserLoginResponse;
import com.sims.ppob.utility.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@SpringBootTest
class TransactionControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UseMe useMe;

    @Autowired
    private RandomGenerator randomGenerator;

    @Test
    public void paymentSuccess() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        TransactionPaymentRequest request = new TransactionPaymentRequest();
        request.setServiceCode("PULSA");

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+userLoginResponse.getToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.service_code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.service_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.transaction_type").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.total_amount").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.created_on").isString());
    }

    @Test
    public void paymentBadRequest() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        TransactionPaymentRequest request = new TransactionPaymentRequest();
        request.setServiceCode("");

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+userLoginResponse.getToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(102))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void paymentUnauthorized() throws Exception {
        TransactionPaymentRequest request = new TransactionPaymentRequest();
        request.setServiceCode("");

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(108))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }
}