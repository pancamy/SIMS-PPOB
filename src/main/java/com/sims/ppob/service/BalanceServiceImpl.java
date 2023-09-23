package com.sims.ppob.service;

import com.sims.ppob.entity.Balances;
import com.sims.ppob.entity.TransactionHistories;
import com.sims.ppob.entity.Users;
import com.sims.ppob.enumeration.TransactionTypes;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.model.BalanceTopUpAmountRequest;
import com.sims.ppob.repository.BalanceRepository;
import com.sims.ppob.repository.TransactionHistoryRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private final LocalDateTime newDate = LocalDateTime.now();

    private final BalanceRepository balanceRepository;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final Model model;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository, TransactionHistoryRepository transactionHistoryRepository, Model model) {
        this.balanceRepository = balanceRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.model = model;
    }

    @Override
    @Transactional
    public BalanceResponse getByUser(Users user) {
        Balances balance = balanceRepository.getByUserId(user.getId());
        if (balance.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance tidak ditemukan");
        }

        return model.toBalanceResponse(balance);
    }

    @Override
    @Transactional
    public BalanceResponse topUpAmount(Users user, BalanceTopUpAmountRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Balances balanceParent = balanceRepository.getByUserId(user.getId());
        if (balanceParent.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance tidak ditemukan");
        }

        Long totalAmount = balanceParent.getBalance() + request.getTopUpAmount();

        balanceParent.setBalance(totalAmount);
        Balances balance = balanceRepository.update(balanceParent);

        TransactionHistories transactionHistory = new TransactionHistories();
        transactionHistory.setId(UUID.randomUUID().toString());
        transactionHistory.setInvoiceNumber("TEST");
        transactionHistory.setTransactionType(TransactionTypes.TOPUP.toString());
        transactionHistory.setDescription("Top Up balance");
        transactionHistory.setTotalAmount(totalAmount);
        transactionHistory.setCreatedAt(newDate);
        transactionHistory.setUpdatedAt(newDate);
        transactionHistoryRepository.save(transactionHistory);

        return model.toBalanceResponse(balance);
    }
}
