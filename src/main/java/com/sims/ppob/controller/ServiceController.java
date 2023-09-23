package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.ServiceResponse;
import com.sims.ppob.model.WebResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ServiceController {

    @GetMapping(
            path = "/services",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<List<ServiceResponse>>> getALl(Users user);
}
