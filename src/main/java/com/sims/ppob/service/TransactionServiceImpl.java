package com.sims.ppob.service;

import com.sims.ppob.entity.Balances;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Transactions;
import com.sims.ppob.entity.Users;
import com.sims.ppob.enumeration.TransactionTypes;
import com.sims.ppob.model.TransactionPaymentRequest;
import com.sims.ppob.model.TransactionResponse;
import com.sims.ppob.repository.BalanceRepository;
import com.sims.ppob.repository.ServiceRepository;
import com.sims.ppob.repository.TransactionRepository;
import com.sims.ppob.repository.UserRepository;
import com.sims.ppob.utility.Model;
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

    private final UserRepository userRepository;

    private final BalanceRepository balanceRepository;

    private final ServiceRepository serviceRepository;

    private final Model model;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, BalanceRepository balanceRepository, ServiceRepository serviceRepository, Model model) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.serviceRepository = serviceRepository;
        this.model = model;
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

        Transactions transaction = new Transactions();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setInvoiceNumber("TEST");
        transaction.setTransactionType(TransactionTypes.PAYMENT.toString());
        transaction.setCreatedAt(newDate);
        transaction.setUpdatedAt(newDate);
        transaction.setUser(user);
        transaction.setServices(service);
        transaction.setBalance(balance);

        transactionRepository.save(transaction);

        return model.toTransactionResponse(transaction);
    }
}