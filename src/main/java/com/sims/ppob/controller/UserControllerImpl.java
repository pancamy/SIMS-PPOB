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
    public ResponseEntity<WebResponse<UserResponse>> registration(UserRegisterRequest request, BindingResult bindingResult) {
        userService.registration(request, bindingResult);

        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .status(0)
                .message("Registrasi berhasil silahkan login")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<UserLoginResponse>> login(UserLoginRequest request, BindingResult bindingResult) {
        UserLoginResponse userLoginResponse = userService.login(request, bindingResult);

        WebResponse<UserLoginResponse> webResponse = WebResponse.<UserLoginResponse>builder()
                .status(0)
                .message("Login Sukses")
                .data(userLoginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<UserResponse>> profile(Users user) {
        UserResponse userResponse = userService.profile(user);

        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .status(0)
                .message("Sukses")
                .data(userResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<UserResponse>> update(Users user, UserUpdateRequest request, BindingResult bindingResult) {
        UserResponse userResponse = userService.update(user, request, bindingResult);

        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .status(0)
                .message("Update Pofile berhasil")
                .data(userResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
