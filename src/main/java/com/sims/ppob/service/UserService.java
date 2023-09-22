package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.*;
import org.springframework.validation.BindingResult;

public interface UserService {

    void registration(UserRegisterRequest request, BindingResult bindingResult);

    UserLoginResponse login(UserLoginRequest request, BindingResult bindingResult);

    UserResponse profile(Users user);

    UserResponse update(Users user, UserUpdateRequest request, BindingResult bindingResult);
}
