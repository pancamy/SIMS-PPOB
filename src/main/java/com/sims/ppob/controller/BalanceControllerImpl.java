package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.model.BalanceTopUpAmountRequest;
import com.sims.ppob.model.WebResponse;
import com.sims.ppob.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BalanceControllerImpl implements BalanceController{

    private final BalanceService balanceService;

    @Autowired
    public BalanceControllerImpl(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public ResponseEntity<WebResponse<BalanceResponse>> getByUser(Users user) {
        BalanceResponse balanceResponse = balanceService.getByUser(user);

        WebResponse<BalanceResponse> webResponse = WebResponse.<BalanceResponse>builder()
                .status(0)
                .message("Get Balance Berhasil")
                .data(balanceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }

    @Override
    public ResponseEntity<WebResponse<BalanceResponse>> topUpAmount(Users user, BalanceTopUpAmountRequest request, BindingResult bindingResult) {
        BalanceResponse balanceResponse = balanceService.topUpAmount(user, request, bindingResult);

        WebResponse<BalanceResponse> webResponse = WebResponse.<BalanceResponse>builder()
                .status(0)
                .message("Top Up Balance berhasil")
                .data(balanceResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
