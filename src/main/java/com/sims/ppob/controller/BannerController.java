package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BannerController {

    @GetMapping(
            path = "/banner",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<List<BannerResponse>>> getALl();

    @PostMapping(
            path = "/banner",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<BannerResponse>> save(Users user, @Valid @RequestBody BannerSaveRequest request,
                                                     BindingResult bindingResult);
}
