package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.TransactionPaymentRequest;
import com.sims.ppob.model.TransactionResponse;
import com.sims.ppob.model.UserResponse;
import com.sims.ppob.model.WebResponse;
import com.sims.ppob.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionControllerImpl implements TransactionController{

    private final TransactionService transactionService;

    @Autowired
    public TransactionControllerImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<WebResponse<TransactionResponse>> payment(Users user, TransactionPaymentRequest request, BindingResult bindingResult) {
        TransactionResponse transactionResponse = transactionService.payment(user, request, bindingResult);

        WebResponse<TransactionResponse> webResponse = WebResponse.<TransactionResponse>builder()
                .status(0)
                .message("Sukses")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
