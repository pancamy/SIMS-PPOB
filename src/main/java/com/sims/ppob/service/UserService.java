package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserLoginRequest;
import com.sims.ppob.model.UserLoginResponse;
import com.sims.ppob.model.UserRegisterRequest;
import com.sims.ppob.model.UserResponse;
import org.springframework.validation.BindingResult;

public interface UserService {

    void registration(UserRegisterRequest request, BindingResult bindingResult);

    UserLoginResponse login(UserLoginRequest request, BindingResult bindingResult);

    UserResponse getProfile(Users user);
}
