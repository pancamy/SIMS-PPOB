package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.*;
import com.sims.ppob.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BannerControllerImpl implements BannerController{

    private final BannerService bannerService;

    @Autowired
    public BannerControllerImpl(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @Override
    public ResponseEntity<WebResponse<List<BannerResponse>>> getALl() {
        List<BannerResponse> bannerResponses = bannerService.getAll();

        WebResponse<List<BannerResponse>> webResponse = WebResponse.<List<BannerResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(bannerResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<BannerResponse>> save(Users user, BannerSaveRequest request, BindingResult bindingResult) {
        BannerResponse bannerResponse = bannerService.save(user, request, bindingResult);

        WebResponse<BannerResponse> webResponse = WebResponse.<BannerResponse>builder()
                .status(0)
                .message("Sukses")
                .data(bannerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
