package com.sims.ppob.repository;

import com.sims.ppob.entity.TransactionHistories;

public interface TransactionHistoryRepository {

    void save(TransactionHistories transactionHistory);
}
