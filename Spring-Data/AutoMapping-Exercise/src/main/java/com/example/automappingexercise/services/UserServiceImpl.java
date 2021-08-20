package com.example.automappingexercise.services;

import com.example.automappingexercise.models.dtos.UserLoginDto;
import com.example.automappingexercise.models.dtos.UserRegisterDto;
import com.example.automappingexercise.models.entities.User;
import com.example.automappingexercise.repositories.UserRepository;
import com.example.automappingexercise.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;


    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Passwords don't match!");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations = validationUtil.violations(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }
        //After all the validations I map the dto(input) to User class and save it to the DB(with Repository)
        User user = this.mapper.map(userRegisterDto, User.class);
        System.out.println("User with full name " + userRegisterDto.getFullName() + " was successfully registered.");
        this.userRepository.save(user);

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations =
                this.validationUtil.violations(userLoginDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user =
                this.userRepository
                        .findByEmailAndPassword(userLoginDto.getEmail(),  userLoginDto.getPassword())
                        .orElse(null);

        if(user == null){
            System.out.println("Incorrect username or password");
            return;
        }
        loggedInUser = user;

    }

    @Override
    public void logout() {
        if(loggedInUser == null) {
            System.out.println("Cannot log out , no user logged in!");
        }
        loggedInUser = null;

    }
}
