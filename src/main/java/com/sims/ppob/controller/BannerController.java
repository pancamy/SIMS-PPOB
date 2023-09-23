package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.WebResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface BannerController {

    @GetMapping(
            path = "/banner",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<List<BannerResponse>>> getALl();
}
