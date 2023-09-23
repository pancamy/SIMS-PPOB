package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;

public interface BalanceService {

    BalanceResponse getBalance(Users user);
}
