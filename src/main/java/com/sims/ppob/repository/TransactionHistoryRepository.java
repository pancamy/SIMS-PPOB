package com.sims.ppob.repository;

import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.TransactionHistories;
import com.sims.ppob.model.PagingRequest;

import java.util.List;

public interface TransactionHistoryRepository {

    void save(TransactionHistories transactionHistory);

    List<TransactionHistories> getAll(PagingRequest pagingRequest);
}
