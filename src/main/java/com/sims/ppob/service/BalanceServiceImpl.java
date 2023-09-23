package com.sims.ppob.service;

import com.sims.ppob.entity.Balances;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.BalanceResponse;
import com.sims.ppob.repository.BalanceRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
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
    public BalanceResponse getBalance(Users user) {
        Balances balance = balanceRepository.getByUserId(user.getId());
        if (balance.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance tidak ditemukan");
        }

        return model.toBalanceResponse(balance);
    }
}
