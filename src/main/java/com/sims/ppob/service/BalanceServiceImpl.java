package com.sims.ppob.service;

import com.sims.ppob.entity.Balances;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.model.BalanceTopUpAmountRequest;
import com.sims.ppob.repository.BalanceRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    private final Model model;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository, Model model) {
        this.balanceRepository = balanceRepository;
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

        balanceParent.setBalance(balanceParent.getBalance() + request.getTopUpAmount());

        Balances balance = balanceRepository.update(balanceParent);

        return model.toBalanceResponse(balance);
    }
}
