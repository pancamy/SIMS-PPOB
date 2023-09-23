package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;

import java.util.List;

public interface BannerService {

    List<BannerResponse> getAll();
}
