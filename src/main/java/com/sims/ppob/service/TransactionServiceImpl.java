package com.sims.ppob.service;

import com.sims.ppob.entity.*;
import com.sims.ppob.enumeration.TransactionTypes;
import com.sims.ppob.model.TransactionPaymentRequest;
import com.sims.ppob.model.TransactionResponse;
import com.sims.ppob.repository.*;
import com.sims.ppob.utility.Model;
import com.sims.ppob.utility.Numbering;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final LocalDateTime newDate = LocalDateTime.now();

    private final TransactionRepository transactionRepository;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final UserRepository userRepository;

    private final BalanceRepository balanceRepository;

    private final ServiceRepository serviceRepository;

    private final Model model;

    private final Numbering numbering;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionHistoryRepository transactionHistoryRepository, UserRepository userRepository, BalanceRepository balanceRepository, ServiceRepository serviceRepository, Model model, Numbering numbering) {
        this.transactionRepository = transactionRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.serviceRepository = serviceRepository;
        this.model = model;
        this.numbering = numbering;
    }

    @Override
    @Transactional
    public TransactionResponse payment(Users user, TransactionPaymentRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Services service = serviceRepository.getByStatusCode(request.getServiceCode());
        if (service.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service tidak ditemukan");
        }

        Balances balance = balanceRepository.getByUserId(user.getId());
        if (balance.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance tidak ditemukan");
        }

        if (balance.getBalance() < service.getServiceTariff()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance tidak cukup untuk pembayaran " + service.getServiceName());
        }

        String invoiceNumber = numbering.InvoiceNumber();

        // transaction
        Transactions transaction = new Transactions();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setInvoiceNumber(invoiceNumber);
        transaction.setTransactionType(TransactionTypes.PAYMENT.toString());
        transaction.setCreatedAt(newDate);
        transaction.setUpdatedAt(newDate);
        transaction.setUser(user);
        transaction.setServices(service);
        transaction.setTotalAmount(balance.getBalance());
        transactionRepository.save(transaction);

        // histories
        TransactionHistories transactionHistory = new TransactionHistories();
        transactionHistory.setId(UUID.randomUUID().toString());
        transactionHistory.setInvoiceNumber(invoiceNumber);
        transactionHistory.setTransactionType(TransactionTypes.TOPUP.toString());
        transactionHistory.setDescription(service.getServiceName());
        transactionHistory.setTotalAmount(balance.getBalance());
        transactionHistory.setCreatedAt(newDate);
        transactionHistory.setUpdatedAt(newDate);
        transactionHistoryRepository.save(transactionHistory);

        // balance reduction
        balance.setBalance(balance.getBalance() - service.getServiceTariff());
        balanceRepository.update(balance);

        return model.toTransactionResponse(transaction);
    }
}
