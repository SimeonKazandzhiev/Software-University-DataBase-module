package com.example.automappingexercise;


import com.example.automappingexercise.models.dtos.GameAddDto;
import com.example.automappingexercise.models.dtos.UserLoginDto;
import com.example.automappingexercise.models.dtos.UserRegisterDto;
import com.example.automappingexercise.services.GameService;
import com.example.automappingexercise.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run(String... args) throws Exception {


        while (true) {
            System.out.println("Enter command or Exit to quit the program");
            String[] input = bufferedReader.readLine().split("\\|");

            switch (input[0]) {
                case "Exit" -> {
                    System.out.println("Bye Bye");
                    return;
                }

                case "RegisterUser" -> userService
                        .registerUser(new UserRegisterDto(input[1], input[2], input[3], input[4]));

                case "LoginUser" -> userService.loginUser(new UserLoginDto(input[1], input[2]));

                case "Logout" -> userService.logout();

                case "AddGame" -> gameService.addGame(new GameAddDto(input[1],new BigDecimal(input[2]),Double.parseDouble(input[3]),input[4],input[5],
                        input[6], LocalDate.parse(input[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

                case "DeleteGame" -> gameService.deleteGame(Long.parseLong(input[1]));


                case "EditGame" -> gameService.editGame(Long.parseLong(input[1]),
                        new BigDecimal(input[2]),Double.parseDouble(input[3]));
            }

        }

    }
}
