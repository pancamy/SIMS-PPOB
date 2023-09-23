package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.TransactionPaymentRequest;
import com.sims.ppob.model.TransactionResponse;
import com.sims.ppob.model.WebResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransactionController {

    @PostMapping(
            path = "/transaction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<TransactionResponse>> payment(Users user,
                                                             @Valid @RequestBody TransactionPaymentRequest request,
                                                             BindingResult bindingResult);
}
