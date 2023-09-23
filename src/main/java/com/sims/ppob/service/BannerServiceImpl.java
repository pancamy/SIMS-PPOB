package com.sims.ppob.service;

import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.BannerSaveRequest;
import com.sims.ppob.repository.BannerRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService{

    private final LocalDateTime newDate = LocalDateTime.now();

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

    @Override
    @Transactional
    public BannerResponse save(Users user, BannerSaveRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Banners banner = new Banners();
        banner.setId(UUID.randomUUID().toString());
        banner.setBannerName(request.getBannerName());
        banner.setDescription(request.getDescription());
        banner.setBannerImage("/dummy.jpeg");
        banner.setCreatedAt(newDate);
        banner.setUpdatedAt(newDate);

        bannerRepository.save(banner);

        return model.toBannerResponse(banner);
    }
}
