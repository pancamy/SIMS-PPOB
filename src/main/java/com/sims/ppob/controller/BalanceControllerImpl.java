package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.WebResponse;
import com.sims.ppob.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceControllerImpl implements BalanceController{

    private final BalanceService balanceService;

    @Autowired
    public BalanceControllerImpl(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public ResponseEntity<WebResponse<BalanceResponse>> getBalance(Users user) {
        BalanceResponse balanceResponse = balanceService.getBalance(user);

        WebResponse<BalanceResponse> webResponse = WebResponse.<BalanceResponse>builder()
                .status(0)
                .message("Get Balance Berhasil")
                .data(balanceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
