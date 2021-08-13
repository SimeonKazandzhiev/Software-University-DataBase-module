package com.example.springdataintrolab;

import com.example.springdataintrolab.services.AccountService;
import com.example.springdataintrolab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private UserService userService;
    private AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) {

        this.userService.registerUser("Pesho",25,new BigDecimal(1000));

    }
}