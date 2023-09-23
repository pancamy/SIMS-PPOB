package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void registration(UserRegisterRequest request, BindingResult bindingResult);

    UserLoginResponse login(UserLoginRequest request, BindingResult bindingResult);

    UserResponse profile(Users user);

    UserResponse profileUpdate(Users user, UserProfileUpdateRequest request, BindingResult bindingResult);

    UserResponse profileImageUpdate(Users user, MultipartFile request);
}
