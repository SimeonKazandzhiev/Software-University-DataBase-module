package com.example.automappingexercise.services;

import com.example.automappingexercise.models.dtos.UserLoginDto;
import com.example.automappingexercise.models.dtos.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

}
