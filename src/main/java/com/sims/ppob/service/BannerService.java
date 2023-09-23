package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.BannerSaveRequest;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface BannerService {

    List<BannerResponse> getAll();

    BannerResponse save(Users user, BannerSaveRequest request, BindingResult bindingResult);
}
