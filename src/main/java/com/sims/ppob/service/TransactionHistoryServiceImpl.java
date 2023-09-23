package com.sims.ppob.service;

import com.sims.ppob.entity.TransactionHistories;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.PagingRequest;
import com.sims.ppob.model.PagingResponse;
import com.sims.ppob.model.TransactionHistoryResponse;
import com.sims.ppob.repository.TransactionHistoryRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final Model model;

    @Autowired
    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository, Model model) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.model = model;
    }

    @Override
    @Transactional
    public PagingResponse<List<TransactionHistoryResponse>> getAll(Users user, PagingRequest pagingRequest) {
        List<TransactionHistories> banners = transactionHistoryRepository.getAll(pagingRequest);

        List<TransactionHistoryResponse> transactionHistoryResponses = banners.stream()
                .map(model::toTransactionHistoryResponse)
                .toList();

        PagingResponse<List<TransactionHistoryResponse>> pagingResponse = new PagingResponse<>();
        pagingResponse.setOffset(pagingRequest.getOffset());
        pagingResponse.setLimit(banners.size());
        pagingResponse.setRecords(transactionHistoryResponses);

        return pagingResponse;
    }
}
