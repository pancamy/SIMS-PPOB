package com.sims.ppob.repository;

import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Users;

import java.util.List;

public interface ServiceRepository {

    List<Services> getAll();
}
