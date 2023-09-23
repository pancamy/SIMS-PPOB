package com.sims.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sims.ppob.model.UserLoginRequest;
import com.sims.ppob.model.UserLoginResponse;
import com.sims.ppob.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Component
public class UseMe {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public UserLoginResponse login() throws Exception {
        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("panca9090@gmail.com");
        request.setPassword("12345678");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.token").isString())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        var webResponse = objectMapper.readValue(responseJson, WebResponse.class);
        String dataJson = objectMapper.writeValueAsString(webResponse.getData());

        return objectMapper.readValue(dataJson, UserLoginResponse.class);
    }
}
