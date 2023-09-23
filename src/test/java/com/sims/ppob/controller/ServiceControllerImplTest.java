package com.sims.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sims.ppob.model.BannerSaveRequest;
import com.sims.ppob.model.ServiceSaveRequest;
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
class ServiceControllerImplTest {

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

        mockMvc.perform(MockMvcRequestBuilders.get("/services")
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
    public void getAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(108))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void saveSuccess() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        ServiceSaveRequest request = new ServiceSaveRequest();
        request.setServiceCode(randomGenerator.randomString(5));
        request.setServiceName(randomGenerator.randomString(5));
        request.setServiceTariff(10000L);

        mockMvc.perform(MockMvcRequestBuilders.post("/services")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.service_icon").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.service_tariff").isNumber());
    }

    @Test
    public void saveBadRequest() throws Exception {
        UserLoginResponse userLoginResponse = useMe.login();

        ServiceSaveRequest request = new ServiceSaveRequest();
        request.setServiceCode("");
        request.setServiceName("");
        request.setServiceTariff(0L);

        mockMvc.perform(MockMvcRequestBuilders.post("/services")
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

        mockMvc.perform(MockMvcRequestBuilders.post("/services")
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