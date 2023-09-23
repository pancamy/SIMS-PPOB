package com.sims.ppob.controller;

import com.sims.ppob.entity.Users;
import com.sims.ppob.model.PagingResponse;
import com.sims.ppob.model.TransactionHistoryResponse;
import com.sims.ppob.model.WebResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface TransactionHistoryController {

    @GetMapping(
            path = "/transaction/history",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<WebResponse<PagingResponse<TransactionHistoryResponse>>> getALl(Users user,
                                                       @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                       @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit);
}
