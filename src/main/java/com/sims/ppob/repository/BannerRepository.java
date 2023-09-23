package com.sims.ppob.repository;

import com.sims.ppob.entity.Banners;

import java.util.List;

public interface BannerRepository {

    List<Banners> getAll();

    void save(Banners banner);
}
