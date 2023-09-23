package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.model.BalanceTopUpAmountRequest;
import com.sims.ppob.model.WebResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BalanceController {

    @GetMapping(
            path = "/balance",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<BalanceResponse>> getByUser(Users user);

    @PostMapping(
            path = "/topup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<BalanceResponse>> topUpAmount(Users user, @Valid @RequestBody BalanceTopUpAmountRequest request,
                                                             BindingResult bindingResult);
}
