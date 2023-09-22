package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.UserSaveRequest;
import org.springframework.validation.BindingResult;

public interface UserService {

    UserResponse save(Users users, UserSaveRequest request, BindingResult bindingResult);
}
