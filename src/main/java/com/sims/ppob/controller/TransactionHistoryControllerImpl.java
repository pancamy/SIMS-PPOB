package com.sims.ppob.controller;

import com.sims.ppob.entity.TransactionHistories;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.*;
import com.sims.ppob.service.TransactionHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class TransactionHistoryControllerImpl implements TransactionHistoryController{

    private final TransactionHistoryService transactionHistoryService;

    @Autowired
    public TransactionHistoryControllerImpl(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @Override
    public ResponseEntity<WebResponse<PagingResponse<List<TransactionHistoryResponse>>>> getALl(Users user, Integer offset, Integer limit) {
        PagingRequest pagingRequest = new PagingRequest();
        pagingRequest.setOffset(offset);
        pagingRequest.setLimit(limit);

        PagingResponse<List<TransactionHistoryResponse>> bannerResponses = transactionHistoryService.getAll(user, pagingRequest);

        WebResponse<PagingResponse<List<TransactionHistoryResponse>>> webResponse = WebResponse.<PagingResponse<List<TransactionHistoryResponse>>>builder()
                .status(0)
                .message("Get History Berhasil")
                .data(bannerResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(webResponse);
    }
}
