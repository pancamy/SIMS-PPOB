package com.sims.ppob.utility;

import com.sims.ppob.constant.UrlConstant;
import com.sims.ppob.entity.Users;
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
}
