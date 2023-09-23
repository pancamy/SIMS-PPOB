package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.ServiceResponse;
import com.sims.ppob.model.WebResponse;
import com.sims.ppob.service.BannerService;
import com.sims.ppob.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceControllerImpl implements ServiceController{

    private final ServiceService serviceService;

    @Autowired
    public ServiceControllerImpl(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Override
    public ResponseEntity<WebResponse<List<ServiceResponse>>> getALl(Users user) {
        List<ServiceResponse> serviceResponses = serviceService.getAll(user);

        WebResponse<List<ServiceResponse>> webResponse = WebResponse.<List<ServiceResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(serviceResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
