package com.example.springdataintrolab.services;

import com.example.springdataintrolab.models.Account;
import com.example.springdataintrolab.models.User;
import com.example.springdataintrolab.repositories.AccountRepository;
import com.example.springdataintrolab.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerUser(String username, int age, BigDecimal initialAmount) {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);

        this.userRepository.save(user);

        Account account = new Account();
        account.setBalance(initialAmount);

        account.setUser(user);

        this.accountRepository.save(account);
    }
}
