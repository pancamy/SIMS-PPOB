package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.TransactionPaymentRequest;
import com.sims.ppob.model.TransactionResponse;
import org.springframework.validation.BindingResult;

public interface TransactionService {

    TransactionResponse payment(Users user, TransactionPaymentRequest request, BindingResult bindingResult);
}
