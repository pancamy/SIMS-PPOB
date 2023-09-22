package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.UserSaveRequest;
import com.sims.ppob.model.WebResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {

    ResponseEntity<WebResponse<UserResponse>> save(Users users,
                                                   @Valid @RequestBody UserSaveRequest request,
                                                   BindingResult binding);
}
