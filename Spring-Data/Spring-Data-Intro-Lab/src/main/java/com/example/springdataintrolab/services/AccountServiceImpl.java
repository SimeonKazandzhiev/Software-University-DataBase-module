package com.example.springdataintrolab.services;


import com.example.springdataintrolab.models.Account;
import com.example.springdataintrolab.repositories.AccountRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findAccountById(id);
         if(account.getBalance().compareTo(amount) < 0){
             throw new IllegalArgumentException("Not enough money");
         }
         account.setBalance(account.getBalance().subtract(amount));
         this.accountRepository.save(account);
    }

    @Override
    public void depositMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findAccountById(id);
        account.setBalance(account.getBalance().add(amount));
        this.accountRepository.save(account);
    }
}
