package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.ServiceResponse;

import java.util.List;

public interface ServiceService {

    List<ServiceResponse> getAll(Users user);
}
