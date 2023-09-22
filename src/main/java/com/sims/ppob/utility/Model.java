package com.sims.ppob.utility;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class Model {

    public UserResponse toUserResponse(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .email(users.getEmail())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .build();
    }
}
