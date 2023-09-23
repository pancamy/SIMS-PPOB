package com.sims.ppob.service;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.PagingResponse;
import com.sims.ppob.model.PagingRequest;
import com.sims.ppob.model.TransactionHistoryResponse;

import java.util.List;

public interface TransactionHistoryService {

    PagingResponse<TransactionHistoryResponse> getAll(Users user, PagingRequest pagingRequest);
}
