package com.sims.ppob.repository;

import com.sims.ppob.entity.Transactions;

public interface TransactionRepository {

    void save(Transactions transaction);
}
