package com.sims.ppob.service;

import com.sims.ppob.entity.Banners;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.repository.BannerRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService{

    private final BannerRepository bannerRepository;

    private final Model model;

    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository, Model model) {
        this.bannerRepository = bannerRepository;
        this.model = model;
    }

    @Override
    @Transactional
    public List<BannerResponse> getAll() {
        List<Banners> banners = bannerRepository.getAll();

        return banners.stream()
                .map(model::toBannerResponse)
                .collect(Collectors.toList());
    }
}
