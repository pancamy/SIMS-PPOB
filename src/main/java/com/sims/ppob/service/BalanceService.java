package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.model.BalanceTopUpAmountRequest;
import org.springframework.validation.BindingResult;

public interface BalanceService {

    BalanceResponse getByUser(Users user);

    BalanceResponse topUpAmount(Users user, BalanceTopUpAmountRequest request, BindingResult bindingResult);
}
