package com.sims.ppob.service;

import com.sims.ppob.model.UserLoginRequest;
import com.sims.ppob.model.UserLoginResponse;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.UserRegisterRequest;
import org.springframework.validation.BindingResult;

public interface UserService {

    UserResponse registration(UserRegisterRequest request, BindingResult bindingResult);

    UserLoginResponse login(UserLoginRequest request, BindingResult bindingResult);
}
