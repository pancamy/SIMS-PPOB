package com.sims.ppob.repository;

import com.sims.ppob.entity.Balances;

public interface BalanceRepository {

    Balances getByUserId(String userId);

    void save(Balances balance);
}
