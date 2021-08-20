package com.example.automappingexercise.services;

import com.example.automappingexercise.models.dtos.GameAddDto;

import java.math.BigDecimal;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void deleteGame(long parseLong);

    void editGame(Long gameId, BigDecimal price, Double size);
}
