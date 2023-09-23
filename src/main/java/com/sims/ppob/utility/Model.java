package com.sims.ppob.utility;

import com.sims.ppob.constant.UrlConstant;
import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BannerResponse;
import com.sims.ppob.model.ServiceResponse;
import com.sims.ppob.model.UserLoginResponse;
import com.sims.ppob.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class Model {

    public UserResponse toUserResponse(Users users) {
        return UserResponse.builder()
                .email(users.getEmail())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .profileImage(UrlConstant.BASE_URL + users.getProfile())
                .build();
    }

    public UserLoginResponse toUserLoginResponse(String token) {
        return UserLoginResponse.builder()
                .token(token)
                .build();
    }

    public BannerResponse toBannerResponse(Banners banner) {
        return BannerResponse.builder()
                .bannerName(banner.getBannerName())
                .bannerImage(banner.getBannerImage())
                .description(banner.getDescription())
                .build();
    }

    public ServiceResponse toServiceResponse(Services service) {
        return ServiceResponse.builder()
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .serviceIcon(service.getServiceIcon())
                .serviceTariff(service.getServiceTariff())
                .build();
    }
}
