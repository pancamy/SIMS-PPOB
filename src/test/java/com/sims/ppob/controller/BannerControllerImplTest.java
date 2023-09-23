package com.sims.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sims.ppob.model.BannerSaveRequest;
import com.sims.ppob.model.PagingRequest;
import com.sims.ppob.model.UserLoginRequest;
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
class BannerControllerImplTest {

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

        mockMvc.perform(MockMvcRequestBuilders.get("/banner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+userLoginResponse.getToken()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    public void getAllSuccessWithParams() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        PagingRequest pagingRequest = new PagingRequest();
        pagingRequest.setOffset(0);
        pagingRequest.setLimit(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/banner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+userLoginResponse.getToken())
                        .queryParam("offset", pagingRequest.getOffset().toString())
                        .queryParam("limit", pagingRequest.getLimit().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    public void saveSuccess() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        BannerSaveRequest request = new BannerSaveRequest();
        request.setBannerName(randomGenerator.randomString(5));
        request.setDescription(randomGenerator.randomString(5));

        mockMvc.perform(MockMvcRequestBuilders.post("/banner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+userLoginResponse.getToken())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.banner_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.banner_image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description").isString());
    }

    @Test
    public void saveBadRequest() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        BannerSaveRequest request = new BannerSaveRequest();
        request.setBannerName("");
        request.setDescription("");

        mockMvc.perform(MockMvcRequestBuilders.post("/banner")
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
    public void saveUnauthorized() throws Exception {
        BannerSaveRequest request = new BannerSaveRequest();
        request.setBannerName(randomGenerator.randomString(5));
        request.setDescription(randomGenerator.randomString(5));

        mockMvc.perform(MockMvcRequestBuilders.post("/banner")
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