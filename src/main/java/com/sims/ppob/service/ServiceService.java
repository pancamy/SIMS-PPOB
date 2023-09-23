package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.BannerSaveRequest;
import com.sims.ppob.model.ServiceResponse;
import com.sims.ppob.model.ServiceSaveRequest;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ServiceService {

    List<ServiceResponse> getAll(Users user);

    ServiceResponse save(Users user, ServiceSaveRequest request, BindingResult bindingResult);
}
