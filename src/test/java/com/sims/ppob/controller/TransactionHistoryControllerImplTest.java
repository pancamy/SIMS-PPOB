package com.sims.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sims.ppob.model.PagingRequest;
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
class TransactionHistoryControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UseMe useMe;

    @Autowired
    private RandomGenerator randomGenerator;

    @Test
    public void getAllSuccess() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+userLoginResponse.getToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.offset").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.limit").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records").isArray());
    }

    @Test
    public void getAllSuccessWithParams() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        PagingRequest pagingRequest = new PagingRequest();
        pagingRequest.setLimit(10);
        pagingRequest.setOffset(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("offset", pagingRequest.getOffset().toString())
                        .param("limit", pagingRequest.getLimit().toString())
                        .header("Authorization", "Bearer "+userLoginResponse.getToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.offset").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.limit").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records").isArray());
    }

    @Test
    public void getAllSuccessWithParamOffset() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        PagingRequest pagingRequest = new PagingRequest();
        pagingRequest.setOffset(0);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("offset", pagingRequest.getOffset().toString())
                        .header("Authorization", "Bearer "+userLoginResponse.getToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.offset").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.limit").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records").isArray());
    }

    @Test
    public void getAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(108))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }
}