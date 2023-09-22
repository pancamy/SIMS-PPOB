package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.UserSaveRequest;
import com.sims.ppob.model.WebResponse;
import com.sims.ppob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController{

    @Autowired
    private UserService userService;

    @Override
    @PostMapping(
            path = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<UserResponse>> save(Users users, UserSaveRequest request, BindingResult binding) {
        UserResponse userResponse = userService.save(users, request, binding);

        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .status(0)
                .message("Registrasi berhasil silahkan login")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
