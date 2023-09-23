package com.sims.ppob.repository;

import com.sims.ppob.entity.Balances;
import com.sims.ppob.entity.Users;

public interface BalanceRepository {

    Balances getByUserId(String userId);

    void save(Balances balance);

    Balances update(Balances balance);
}
