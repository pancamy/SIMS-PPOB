package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.*;
import com.sims.ppob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController{

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<WebResponse<UserResponse>> registration(UserRegisterRequest request, BindingResult binding) {
        userService.registration(request, binding);

        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .status(0)
                .message("Registrasi berhasil silahkan login")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<UserLoginResponse>> login(UserLoginRequest request, BindingResult binding) {
        UserLoginResponse userLoginResponse = userService.login(request, binding);

        WebResponse<UserLoginResponse> webResponse = WebResponse.<UserLoginResponse>builder()
                .status(0)
                .message("Login Sukses")
                .data(userLoginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<UserLoginResponse>> profile(Users users) {
        WebResponse<UserLoginResponse> webResponse = WebResponse.<UserLoginResponse>builder()
                .status(0)
                .message("Login Sukses")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
